package com.whatsong.domain.member.dto.requestDto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String loginId;

	private String password;
}