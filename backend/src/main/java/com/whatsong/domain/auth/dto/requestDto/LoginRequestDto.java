package com.whatsong.domain.auth.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인 요청 DTO")
@Getter
public class LoginRequestDto {

	@Schema(description = "로그인 id")
	private String loginId;

	@Schema(description = "비밀번호")
	private String password;
}