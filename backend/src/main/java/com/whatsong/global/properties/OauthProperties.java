package com.whatsong.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {
	private KakaoOauthSecret kakao;

	@Getter
	@Setter
	public static class KakaoOauthSecret {
		private String baseUrl;
		private String clientId;
		private String clientSecret;
		private String redirectUrl;
		private String appId;
		private String adminKey;

		public String getAdminKeyWithPrefix() {
			return "KakaoAK " + adminKey;
		}
	}

}
