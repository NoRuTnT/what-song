package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "닉네임 중복검사 응답 DTO")
@Getter
@AllArgsConstructor
public class ValidateDuplicatedNicknameResponseDto {

	@Schema(description = "닉네임 존재유무 boolean값")
	private boolean isValid;
}