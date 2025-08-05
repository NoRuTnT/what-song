package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그인성공 응답 DTO")
@Getter
@Builder
public class LoginResponseDto {

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "발급받은 AT")
	private String accessToken;

	@Schema(description = "발급받은 RT")
	private String refreshToken;
}