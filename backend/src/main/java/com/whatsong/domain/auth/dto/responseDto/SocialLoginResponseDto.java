package com.whatsong.domain.auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginResponseDto {
	private String nickname;

	private String accessToken;

	private String refreshToken;

}
