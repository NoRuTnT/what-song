package com.whatsong.global.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.whatsong.global.client.dto.KakaoIdTokenInfoRequest;
import com.whatsong.global.client.dto.KakaoIdTokenPayload;
import com.whatsong.global.client.dto.OIDCPublicKeysResponse;
import com.whatsong.global.openfeign.KakaoKauthConfig;

@FeignClient(
	name = "KakaoOIDCClient",
	url = "https://kauth.kakao.com",
	configuration = KakaoKauthConfig.class
)
@Component
public interface KakaoOIDCClient {

	@Cacheable(
		cacheNames = "oidc-public-key",
		key = "'KAKAO'",
		cacheManager = "oidcCacheManager"
	)
	@GetMapping(path = "/.well-known/jwks.json")
	OIDCPublicKeysResponse getOIDCPublicKey();

	/**
	 * 토큰의 유효성 검증이 불가능하므로 디버깅 용도로만 사용해야 한다.
	 */
	@PostMapping(
		path = "/oauth/tokeninfo",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	)
	KakaoIdTokenPayload getIdTokenInfo(KakaoIdTokenInfoRequest idToken);
}