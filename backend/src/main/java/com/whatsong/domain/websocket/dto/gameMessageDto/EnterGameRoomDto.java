package com.whatsong.domain.websocket.dto.gameMessageDto;

import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;
import com.whatsong.domain.websocket.model.UserInfoItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterGameRoomDto {
	private MessageDtoType messageType;
	private List<UserInfoItem> userInfoItems;
	private String gameRoomManagerNickname;
	private String enteredUserNickname;
}
