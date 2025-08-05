package com.whatsong.domain.auth.dto.requestDto;

import com.whatsong.domain.auth.data.LoginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "소셜로그인 요청 DTO")
@Getter
public class SocialLoginRequestDto {

	@Schema(description = "소셜로그인 플랫폼")
	private LoginType loginType;

	@Schema(description = "발급받은 id토큰")
	private String idToken;
}
