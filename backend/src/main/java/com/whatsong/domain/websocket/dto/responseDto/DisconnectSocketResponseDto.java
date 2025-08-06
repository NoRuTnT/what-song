package com.whatsong.domain.websocket.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DisconnectSocketResponseDto {
	private int channelNo;
}
