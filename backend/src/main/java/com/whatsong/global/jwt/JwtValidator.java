package com.whatsong.global.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsong.global.RedisService;
import com.whatsong.global.exception.ErrorCode.KakaoServerErrorCode;
import com.whatsong.global.exception.exception.AuthException;
import com.whatsong.global.exception.ErrorCode.AuthErrorCode;
import com.whatsong.global.exception.exception.KakaoServerException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtValidator {
	private final RedisService redisService;

	@Value("${jwt.secret-key}")
	private String SECRET_KEY;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public void validateToken(String type, String token) {
		try {
			Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			Date now = new Date();
			if (claims.getExpiration() != null && claims.getExpiration().before(now)) {
				throw new AuthException(type.equals("access") ? AuthErrorCode.INVALID_ACCESS_TOKEN : AuthErrorCode.INVALID_REFRESH_TOKEN);
			}
		} catch (MalformedJwtException | ExpiredJwtException | SignatureException e) {
			throw new AuthException(type.equals("access") ? AuthErrorCode.INVALID_ACCESS_TOKEN : AuthErrorCode.INVALID_REFRESH_TOKEN);
		}
	}

	public String validateRefreshToken(String refreshToken) {
		validateToken("refresh", refreshToken);

		String memberId = getData(refreshToken).toString();

		String token = redisService.getRefreshToken(memberId);

		if (!refreshToken.equals(token)) {
			throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
		}

		return memberId;
	}

	public UUID getData(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
			.build()
			.parseClaimsJws(token)
			.getBody();

		String data = claims.get("data", String.class);;
		return UUID.fromString(data);
	}

	public static Jws<Claims> parseKakaoIdToken(JwtParser parser, String idToken) {
		try {
			return parser.parseClaimsJws(idToken);
		} catch (JwtException e) {
			throw new KakaoServerException(KakaoServerErrorCode.INVALID_OIDC_TOKEN);
		}
	}

	public static Map<String, Object> getUnsecuredHeader(String token) {
		List<String> jwtChunk = getJwtChunk(token);
		try {
			byte[] decodedHeader = Base64.getUrlDecoder().decode(jwtChunk.get(0));
			return objectMapper.readValue(decodedHeader, new TypeReference<>() {});
		} catch (Exception e) {
			throw new RuntimeException("유효하지않은 JWT Header 입니다", e);
		}
	}

	public static Map<String, Object> getUnsecuredPayload(String token) {
		List<String> jwtChunk = getJwtChunk(token);
		try {
			byte[] decodedPayload = Base64.getUrlDecoder().decode(jwtChunk.get(1));
			return objectMapper.readValue(decodedPayload, new TypeReference<>() {});
		} catch (Exception e) {
			throw new RuntimeException("유효하지않은 JWT Payload 입니다", e);
		}
	}

	private static List<String> getJwtChunk(String token) {
		String[] parts = token.split("\\.");
		if (parts.length < 3) {
			throw new RuntimeException("JWT 형식이 잘못되었습니다.");
		}
		return List.of(parts);
	}
}
