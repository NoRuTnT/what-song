package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MusicPlayDto {

	private MessageDtoType messageType;
	private Boolean musicPlay;

	@Builder
	public MusicPlayDto() {

		this.messageType = MessageDtoType.MUSICPLAY;
		this.musicPlay = true;
	}
}
