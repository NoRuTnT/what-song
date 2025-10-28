package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Hint1Dto {

	private MessageDtoType messageType;
	private String hint1;

	@Builder
	public Hint1Dto(String hint1) {
		this.messageType = MessageDtoType.HINT1;
		this.hint1 = hint1;
	}
}