package com.whatsong.domain.websocket.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExitGameRoomResponse {
	private int destinationChannelNo;
}
