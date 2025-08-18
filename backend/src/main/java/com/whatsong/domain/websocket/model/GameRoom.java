package com.whatsong.domain.websocket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.whatsong.domain.websocket.data.GameRoomType;
import com.whatsong.domain.websocket.data.GameValue;
import com.whatsong.domain.websocket.data.MessageDtoType;
import com.whatsong.domain.websocket.data.MessageType;
import com.whatsong.domain.websocket.data.PlayType;
import com.whatsong.domain.websocket.dto.GetUserInfoItemDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.EnterGameRoomDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.ExitGameRoomDto;
import com.whatsong.domain.websocket.dto.gameMessageDto.GameRoomMemberInfo;
import com.whatsong.domain.websocket.dto.requestDto.ModifyGameRoomInformationRequestDto;
import com.whatsong.domain.websocket.dto.responseDto.CheckPasswordResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.EnterGameRoomResponseDto;
import com.whatsong.global.exception.ErrorCode.MultiModeErrorCode;
import com.whatsong.global.exception.exception.MultiModeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameRoom {
	private static final int LEAST_MEMBER_SIZE = 1;
	private static final int ROOM_DIVIDE_NUMBER = 1000;
	private static final int MULTI_MODE_EXP_WEIGHT = 10;
	private static final String SPACE = " ";

	//todo Room
	private int roomNo;

	private int channelNo;

	private String title;

	private String password;

	private boolean isPrivate;

	private UUID roomManagerUUID;

	private int maxUserNumber;

	private String roomManagerNickname;

	private Map<UUID, UserInfoItem> userInfoItems;

	private int totalUsers;

	private GameRoomType gameRoomType;

	private int createGameRoomLogId;

	//todo Game class분리
	private int numberOfProblems;

	private String year;

	private PlayType playType;

	private int time;

	private int skipVote;

	private int round;

	private List<Problem> problems;


	public void setProblems(
		List<Problem> problems) {
		this.problems = problems;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public void setSkipVote(int skipVote) {
		this.skipVote = skipVote;
	}

	public void changeGameRoomType(GameRoomType type) {
		this.gameRoomType = type;
	}

	public void changePlayType(PlayType type) {
		this.playType = type;
	}

	public void timeDown() {
		this.time--;
	}

	public void roundUp() {
		this.round++;
	}

	public ExitGameRoomDto exitUser(UUID uuid, String nickname, int gameRoomNumber) {
		int lobbyChannelNumber = gameRoomNumber / ROOM_DIVIDE_NUMBER;
		int gameRoomIndex = gameRoomNumber % ROOM_DIVIDE_NUMBER;
		List<GameRoomMemberInfo> gameRoomMemberInfos = new ArrayList<>();

		// 방에 아무도 안 남을 경우
		if (totalUsers == LEAST_MEMBER_SIZE) {
			GameValue.deleteGameRoom(lobbyChannelNumber, gameRoomIndex, gameRoomNumber);

			this.totalUsers--;
			this.userInfoItems.remove(uuid);

			for(UserInfoItem userInfoItem : this.userInfoItems.values()) {
				GameRoomMemberInfo gameRoomMemberInfo =
					GameRoomMemberInfo.create(userInfoItem.getNickname(),userInfoItem.getScore());
				gameRoomMemberInfos.add(gameRoomMemberInfo);
			}


			return ExitGameRoomDto.builder()
				.messageType(MessageDtoType.EXITUSER)
				.userInfoItems(gameRoomMemberInfos)
				.gameRoomManagerNickname(this.roomManagerNickname)
				.exitedUserNickname(nickname)
				.build();
		}


		// 방장 위임
		if (uuid.equals(this.roomManagerUUID)) {
			for(UUID userUUID : this.userInfoItems.keySet()) {
				if (!userUUID.equals(this.roomManagerUUID)) {
					this.roomManagerNickname = userInfoItems.get(userUUID).getNickname();
					this.roomManagerUUID = userUUID;
					break;
				}
			}
		}

		this.totalUsers--;
		userInfoItems.remove(uuid);

		for(UserInfoItem userInfoItem : this.userInfoItems.values()) {
			GameRoomMemberInfo gameRoomMemberInfo =
				GameRoomMemberInfo.create(userInfoItem.getNickname(),userInfoItem.getScore());
			gameRoomMemberInfos.add(gameRoomMemberInfo);
		}


		return ExitGameRoomDto.builder()
			.messageType(MessageDtoType.EXITUSER)
			.userInfoItems(gameRoomMemberInfos)
			.gameRoomManagerNickname(this.roomManagerNickname)
			.exitedUserNickname(nickname)
			.build();
	}

	public CheckPasswordResponseDto checkPassword(String password) {
		if (!this.isPrivate) {
			return new CheckPasswordResponseDto(Boolean.TRUE);
		}

		if (this.password.equals(password)) {
			return new CheckPasswordResponseDto(Boolean.TRUE);
		}

		return new CheckPasswordResponseDto(Boolean.FALSE);
	}

	public EnterGameRoomResponseDto enterUser(UUID uuid, UserInfoItem userInfoItem) {
		if (!gameRoomType.equals(GameRoomType.WAITING)) {
			throw new MultiModeException(MultiModeErrorCode.ALREADY_STARTED_ROOM);
		}

		if (totalUsers == this.maxUserNumber) {
			throw new MultiModeException(MultiModeErrorCode.FULL_ROOM_USER);
		}

		userInfoItems.put(uuid, userInfoItem);
		totalUsers++;

		return EnterGameRoomResponseDto.builder()
			.userInfoItems(userInfoItems.values().stream().toList())
			.gameRoomManagerNickname(this.roomManagerNickname)
			.enteredUserNickname(userInfoItems.get(uuid).getNickname())
			.build();
	}

	public EnterGameRoomDto getGameRoomInformation(UUID uuid) {
		return EnterGameRoomDto.builder()
			.messageType(MessageDtoType.ENTERUSER)
			.userInfoItems(userInfoItems.values().stream().toList())
			.gameRoomManagerNickname(this.roomManagerNickname)
			.enteredUserNickname(userInfoItems.get(uuid).getNickname())
			.build();
	}

	public void initializeGameStart() {
		this.gameRoomType = GameRoomType.PLAYING;
		this.playType = PlayType.ROUNDSTART;
		this.time = 5;
		this.round = 1;
	}

	public void initializeGameEnd() {
		this.gameRoomType = GameRoomType.WAITING;
		this.skipVote = 0;

		for (UserInfoItem userInfo : this.userInfoItems.values()) {
			userInfo.initializeUserInfo();
		}
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getNicknames() {
		StringBuilder nicknames = new StringBuilder();
		for(UserInfoItem userInfoItem : this.userInfoItems.values()) {
			nicknames.append(userInfoItem.getNickname()).append(SPACE);
		}

		return nicknames.toString();
	}

	public GetUserInfoItemDto getUserInfoItemDto() {
		StringBuilder nicknames = new StringBuilder();
		StringBuilder exps = new StringBuilder();
		for(UserInfoItem userInfoItem : this.userInfoItems.values()) {
			nicknames.append(userInfoItem.getNickname()).append(SPACE);
			exps.append(userInfoItem.getScore() * MULTI_MODE_EXP_WEIGHT).append(SPACE);
		}

		return GetUserInfoItemDto.builder()
			.nicknames(nicknames.toString())
			.exps(exps.toString())
			.build();
	}


	public void gameRoomUserScoreReset() {
		for (Map.Entry<UUID, UserInfoItem> entry : this.userInfoItems.entrySet()) {
			entry.getValue().initializeUserInfo();
			this.userInfoItems.put(entry.getKey(), entry.getValue());
		}
	}

	public boolean isNotRoomManager(UUID roomManagerUUID) {
		if (roomManagerUUID.equals(this.roomManagerUUID)) {
			return false;
		}
		return true;
	}

	public void modifyInformation(ModifyGameRoomInformationRequestDto modifyGameRoomInformationRequestDto) {
		this.title = modifyGameRoomInformationRequestDto.getTitle();
		this.year = modifyGameRoomInformationRequestDto.getYear();
		this.numberOfProblems = modifyGameRoomInformationRequestDto.getQuizAmount();
		this.maxUserNumber = modifyGameRoomInformationRequestDto.getMaxUserNumber();
	}
}