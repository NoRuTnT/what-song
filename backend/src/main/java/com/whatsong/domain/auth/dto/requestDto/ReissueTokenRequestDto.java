package com.whatsong.domain.auth.dto.requestDto;

import lombok.Getter;

@Getter
public class ReissueTokenRequestDto {
	private String refreshToken;
}