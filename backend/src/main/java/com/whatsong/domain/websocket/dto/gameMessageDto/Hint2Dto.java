package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Hint2Dto {

	private MessageDtoType messageType;
	private String hint2;

	@Builder
	public Hint2Dto(String hint2) {
		this.messageType = MessageDtoType.HINT2;
		this.hint2 = hint2;
	}
}