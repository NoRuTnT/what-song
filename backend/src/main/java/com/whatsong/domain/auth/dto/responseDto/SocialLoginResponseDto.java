package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Schema(description = "소셜로그인 성공 응답 DTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginResponseDto {

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "발급받은 AT")
	private String accessToken;

	@Schema(description = "발급받은 RT")
	private String refreshToken;

}
