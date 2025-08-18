package com.whatsong.domain.websocket.service.subService;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whatsong.domain.member.model.MemberInfo;
import com.whatsong.domain.websocket.data.GameValue;
import com.whatsong.domain.websocket.dto.gameMessageDto.EnterGameRoomDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.ExitGameRoomDto;
import com.whatsong.domain.websocket.dto.responseDto.CheckPasswordResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.EnterGameRoomResponseDto;
import com.whatsong.domain.websocket.model.GameRoom;
import com.whatsong.domain.websocket.model.UserInfoItem;

@Service
public class CommonService {

	private static final int MULTI_SCORE_WEIGHT = 10;

	public ExitGameRoomDto exitGameRoom(UUID uuid,String nickname, GameRoom gameRoom, int roomNumber) {
		// Channel 현재 게임룸에서 로비로 이동
		GameValue.exitGameRoom(uuid, gameRoom.getChannelNo(), roomNumber);

		// 게임 룸에서 totalUser--, userInfoItems에서 내 uuid로 지우기
		return gameRoom.exitUser(uuid, nickname, roomNumber);
	}

	public CheckPasswordResponseDto checkPassword(GameRoom gameRoom, String password) {
		return gameRoom.checkPassword(password);
	}

	public EnterGameRoomResponseDto enterGameRoom(UUID uuid, String nickname, GameRoom gameRoom, int roomNumber) {
		GameValue.enterGameRoom(uuid, gameRoom.getChannelNo(), roomNumber);

		UserInfoItem userInfoItem = UserInfoItem.of(nickname);

		// 게임 룸에서 totalUser++, userInfoItems에 내 uuid 추가
		return gameRoom.enterUser(uuid, userInfoItem);
	}

	public EnterGameRoomDto enterGameRoomForPublish(UUID uuid, GameRoom gameRoom) {

		return gameRoom.getGameRoomInformation(uuid);
	}

	@Transactional
	public void updateExp(MemberInfo memberInfo, Double score) {
		memberInfo.gainExp(score * MULTI_SCORE_WEIGHT);
	}

}
