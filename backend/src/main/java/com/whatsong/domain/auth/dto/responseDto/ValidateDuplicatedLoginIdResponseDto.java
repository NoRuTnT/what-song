package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "loginID 중복검사 응답 DTO")
@Getter
@AllArgsConstructor
public class ValidateDuplicatedLoginIdResponseDto {

	@Schema(description = "존재유무 boolean값")
	private boolean isValid;
}