package com.whatsong.domain.member.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateDuplicatedLoginIdResponseDto {
	private boolean isValid;
}