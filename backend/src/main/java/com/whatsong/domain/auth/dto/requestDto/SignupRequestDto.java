package com.whatsong.domain.auth.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "일반 회원가입요청 DTO")
@Getter
public class SignupRequestDto {

	@Schema(description = "로그인id")
	private String loginId;

	@Schema(description = "비밀번호")
	private String password;

	@Schema(description = "닉네임")
	private String nickname;
}