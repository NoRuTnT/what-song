package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicEndDto {

	private MessageDtoType messageType;
	private Boolean musicPlay;

	@Builder
	public MusicEndDto() {

		this.messageType = MessageDtoType.MUSICEND;
		this.musicPlay = false;
	}
}