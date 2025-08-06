package com.whatsong.domain.websocket.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckPasswordResponseDto {
	private Boolean isCorrectPassword;
}
