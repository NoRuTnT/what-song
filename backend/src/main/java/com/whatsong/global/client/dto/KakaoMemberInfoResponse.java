package com.whatsong.global.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoMemberInfoResponse {

	private Long id;
	private KakaoAccount kakaoAccount;
	private KakaoUserProperties properties;

	public String getPlatformUserId() {
		return id != null ? id.toString() : null;
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@Data
	public static class KakaoAccount {
		private Profile profile;
		private String name;
		private String email;

		@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
		@Data
		public static class Profile {
			private String nickname;
			private String thumbnailImageUrl;
			private String profileImageUrl;
		}
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@Data
	public static class KakaoUserProperties {
		private String nickname;
	}
}
