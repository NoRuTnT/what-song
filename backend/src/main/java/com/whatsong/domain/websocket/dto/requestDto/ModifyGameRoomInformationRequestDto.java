package com.whatsong.domain.websocket.dto.requestDto;

import lombok.Getter;

@Getter
public class ModifyGameRoomInformationRequestDto {
	private int createGameRoomLogId;

	private int gameRoomNo;

	private String title;

	private int musicSetId;

	private int quizAmount;

	private int maxUserNumber;
}
