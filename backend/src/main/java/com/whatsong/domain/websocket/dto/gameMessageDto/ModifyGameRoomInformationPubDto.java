package com.whatsong.domain.websocket.dto.gameMessageDto;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyGameRoomInformationPubDto {
	private MessageDtoType messageType;

	private String title;

	private String year;

	private int quizAmount;

	private int maxUserNumber;
}
