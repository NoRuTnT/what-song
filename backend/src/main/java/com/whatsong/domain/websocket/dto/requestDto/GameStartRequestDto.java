package com.whatsong.domain.websocket.dto.requestDto;

import lombok.Getter;

@Getter
public class GameStartRequestDto {
	private int multiModeCreateGameRoomLogId;
	private int gameRoomNumber;
}