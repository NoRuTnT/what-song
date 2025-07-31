package com.whatsong.domain.auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateDuplicatedLoginIdResponseDto {
	private boolean isValid;
}