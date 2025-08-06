package com.whatsong.domain.websocket.dto.requestDto;

import lombok.Getter;

@Getter
public class ModifyGameRoomInformationRequestDto {
	private int multiModeCreateGameRoomLogId;

	private int gameRoomNo;

	private String title;

	private String year;

	private int quizAmount;

	private int maxUserNumber;
}
