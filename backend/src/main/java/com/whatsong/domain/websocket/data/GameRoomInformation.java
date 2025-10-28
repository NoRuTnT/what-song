package com.whatsong.domain.websocket.data;

import lombok.Getter;

@Getter
public enum GameRoomInformation {
	MINIMUM_TITLE_LENGTH(1),
	MAXIMUM_TITLE_LENGTH(18),
	MINIMUM_MAX_USER_NUMBER(1),
	MAXIMUM_MAX_USER_NUMBER(10);

	private int value;

	GameRoomInformation(int value) {
		this.value = value;
	}
}
