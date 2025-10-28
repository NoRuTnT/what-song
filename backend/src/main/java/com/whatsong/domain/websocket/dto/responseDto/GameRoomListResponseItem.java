package com.whatsong.domain.websocket.dto.responseDto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomListResponseItem {
	private int gameRoomNo;
	private String roomTitle;
	private String roomManager;
	private int maxUserNumber;
	private int currentMembers;
	private int currentRound;
	private int quizAmount;
	private Boolean isPrivate;
	private Boolean isPlay;
}
