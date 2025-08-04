package com.whatsong.domain.auth.dto.requestDto;

import com.whatsong.domain.auth.data.LoginType;

import lombok.Getter;

@Getter
public class SocialLoginRequestDto {
	private LoginType loginType;

	private String idToken;
}
