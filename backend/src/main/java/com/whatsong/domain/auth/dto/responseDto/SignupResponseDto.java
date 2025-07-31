package com.whatsong.domain.auth.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {
	private String nickname;

	public static SignupResponseDto of(String nickname) {
		return SignupResponseDto.builder()
			.nickname(nickname)
			.build();
	}
}