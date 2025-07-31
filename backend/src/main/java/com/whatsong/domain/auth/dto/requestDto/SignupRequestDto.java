package com.whatsong.domain.auth.dto.requestDto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
	private String loginId;

	private String password;

	private String nickname;
}