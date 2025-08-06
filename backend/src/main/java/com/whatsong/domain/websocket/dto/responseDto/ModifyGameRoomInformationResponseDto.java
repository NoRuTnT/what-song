package com.whatsong.domain.websocket.dto.responseDto;

import lombok.Getter;

@Getter
public class ModifyGameRoomInformationResponseDto {
	private Boolean isSuccess;

	public ModifyGameRoomInformationResponseDto() {
		this.isSuccess = Boolean.TRUE;
	}
}