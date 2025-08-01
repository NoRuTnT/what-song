package com.whatsong.domain.member.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueTokenResponseDto {
	private String accessToken;
	private String refreshToken;

	@Builder
	public ReissueTokenResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}