package com.whatsong.domain.auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestSignupResponseDto {
	private String nickname;

	public static GuestSignupResponseDto of(String nickname) {
		return GuestSignupResponseDto.builder()
			.nickname(nickname)
			.build();
	}
}
