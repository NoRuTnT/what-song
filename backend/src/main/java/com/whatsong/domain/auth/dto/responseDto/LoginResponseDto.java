package com.whatsong.domain.auth.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
	private String nickname;

	private String accessToken;

	private String refreshToken;
}