package com.whatsong.domain.websocket.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameStartResponseDto {
	private int multiModeCreateGameRoomLogId;
}
