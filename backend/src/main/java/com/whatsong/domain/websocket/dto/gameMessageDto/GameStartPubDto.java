package com.whatsong.domain.websocket.dto.gameMessageDto;

import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStartPubDto {
	private MessageDtoType messageType;
	private String message;
	private List<GameRoomMemberInfo> memberInfos;

	@Builder
	public GameStartPubDto(List<GameRoomMemberInfo> memberInfos) {
		this.messageType = MessageDtoType.GAMESTART;
		this.memberInfos = memberInfos;
		this.message = "게임이 시작됩니다.";
	}
}