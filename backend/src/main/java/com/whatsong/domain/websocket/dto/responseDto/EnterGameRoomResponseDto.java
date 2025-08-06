package com.whatsong.domain.websocket.dto.responseDto;

import java.util.List;

import com.whatsong.domain.websocket.model.UserInfoItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterGameRoomResponseDto {
	private List<UserInfoItem> userInfoItems;
	private String gameRoomManagerNickname;
	private String enteredUserNickname;
}
