package com.whatsong.domain.websocket.dto.gameMessageDto;


import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExitGameRoomDto {
	private MessageDtoType messageType;
	private List<GameRoomMemberInfo> userInfoItems;
	private String gameRoomManagerNickname;
	private String exitedUserNickname;
}
