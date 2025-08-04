package com.whatsong.domain.auth.service;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.whatsong.global.client.dto.JsonWebKey;
import com.whatsong.global.client.dto.KakaoIdTokenPayload;
import com.whatsong.global.exception.ErrorCode.AuthErrorCode;
import com.whatsong.global.exception.exception.AuthException;
import com.whatsong.global.jwt.JwtProvider;
import com.whatsong.global.jwt.JwtValidator;
import com.whatsong.global.properties.OauthProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OIDCHelper {

	private final JwtProvider jwtProvider;
	private final OauthProperties oauthProperties;


	public KakaoIdTokenPayload parseKakaoIdToken(String idToken, List<JsonWebKey> oidcPublicKeys) {
		String kid = getKid(idToken);
		JsonWebKey webKey = oidcPublicKeys.stream()
			.filter(k -> kid.equals(k.getKid()))
			.findFirst()
			.orElseThrow(() -> {
				log.warn("kid({})에 해당하는 카카오 공개키를 찾을 수 없습니다.", kid);
				return new AuthException(AuthErrorCode.ILLEGAL_KID);
			});

		Jws<Claims> jws = parseIdToken(
			idToken,
			getRsaPublicKey(webKey.getN(), webKey.getE()),
			oauthProperties.getKakao().getBaseUrl(),
			oauthProperties.getKakao().getClientId()
		);

		return toKakaoIdTokenPayload(jws);
	}

	private Jws<Claims> parseIdToken(String idToken, PublicKey rsaPublicKey, String issuer, String audience) {
		JwtParser parser = Jwts.parserBuilder()
			.requireIssuer(issuer)
			.requireAudience(audience)
			.setSigningKey(rsaPublicKey)
			.build();

		return JwtValidator.parseKakaoIdToken(parser, idToken);
	}

	private String getKid(String idToken) {
		Map<String, Object> header = JwtValidator.getUnsecuredHeader(idToken);
		if (header.get("kid") == null) {
			throw new IllegalArgumentException("kid가 존재하지 않습니다. 올바르지 않은 idToken입니다.");
		}
		return header.get("kid").toString();
	}

	private PublicKey getRsaPublicKey(String n, String e) {
		byte[] decodedN = Base64.getUrlDecoder().decode(n);
		byte[] decodedE = Base64.getUrlDecoder().decode(e);

		BigInteger modulus = new BigInteger(1, decodedN);
		BigInteger exponent = new BigInteger(1, decodedE);

		try {
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (Exception ex) {
			throw new RuntimeException("RSA 공개키 생성 실패", ex);
		}
	}

	private KakaoIdTokenPayload toKakaoIdTokenPayload(Jws<Claims> jws) {
		Claims claims = jws.getBody();
		return new KakaoIdTokenPayload(
			claims.getIssuer(),
			String.join(",", claims.getAudience()),
			claims.getSubject(),
			claims.get("iat", Number.class).longValue(),
			claims.get("exp", Number.class).longValue(),
			claims.get("auth_time", Number.class).longValue(),
			(String) claims.get("nonce"),
			(String) claims.get("nickname"),
			(String) claims.get("picture"),
			(String) claims.get("email")
		);
	}

}
