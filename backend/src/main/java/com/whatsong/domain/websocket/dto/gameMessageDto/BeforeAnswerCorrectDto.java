package com.whatsong.domain.websocket.dto.gameMessageDto;

import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BeforeAnswerCorrectDto {

	private MessageDtoType messageType;
	private String winner;
	private String answer;
	private String hint1;
	private String hint2;
	private Integer skipVote;
	private List<GameRoomMemberInfo> memberInfos;

	public static BeforeAnswerCorrectDto create(MessageDtoType messageType, String winner,
		String answer, Integer skipVote,List<GameRoomMemberInfo> memberInfos ){
		String hint1 = "";
		String hint2 = "";
		return new BeforeAnswerCorrectDto(messageType, winner, answer, hint1,
			hint2, skipVote, memberInfos);
	}

}
