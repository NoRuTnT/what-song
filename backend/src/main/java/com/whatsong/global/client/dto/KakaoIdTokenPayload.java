package com.whatsong.global.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoIdTokenPayload {
	private String iss;
	private String aud;
	private String sub;
	private long iat;
	private long exp;
	private long authTime;
	private String nonce;
	private String nickname;
	private String picture;
	private String email;

	public String getPlatformMemberId() {
		return sub;
	}
}
