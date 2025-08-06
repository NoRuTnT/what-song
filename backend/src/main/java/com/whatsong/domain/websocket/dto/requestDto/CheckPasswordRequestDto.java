package com.whatsong.domain.websocket.dto.requestDto;

import lombok.Getter;

@Getter
public class CheckPasswordRequestDto {
	private int gameRoomNo;
	private String password;
}
