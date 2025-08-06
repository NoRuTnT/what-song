package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingerHintDto {

	private MessageDtoType messageType;
	private String singerHint;

	@Builder
	public SingerHintDto(String singerHint) {
		this.messageType = MessageDtoType.SINGERHINT;
		this.singerHint = singerHint;
	}
}