package com.whatsong.domain.websocket.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameStartRequestDto {
	private int createGameRoomLogId;
	private int gameRoomNumber;
}