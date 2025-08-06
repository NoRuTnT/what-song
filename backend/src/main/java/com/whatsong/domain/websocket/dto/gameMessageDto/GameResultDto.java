package com.whatsong.domain.websocket.dto.gameMessageDto;

import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameResultDto {

	private MessageDtoType messageType;
	private List<GameResultItem> userResults;

	@Builder
	public GameResultDto(List<GameResultItem> userResults) {

		this.messageType = MessageDtoType.GAMERESULT;
		this.userResults = userResults;
	}
}
