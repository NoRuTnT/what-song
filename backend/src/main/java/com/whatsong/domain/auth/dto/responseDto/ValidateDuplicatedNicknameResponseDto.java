package com.whatsong.domain.auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateDuplicatedNicknameResponseDto {
	private boolean isValid;
}