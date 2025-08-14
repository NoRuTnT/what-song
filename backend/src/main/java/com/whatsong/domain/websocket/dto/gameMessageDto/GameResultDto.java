package com.whatsong.domain.websocket.dto.gameMessageDto;

import java.util.List;

import com.whatsong.domain.websocket.data.MessageDtoType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게임이 종료되고 사용자들에게 결과창을 보내기위한 DTO")
@Getter
@NoArgsConstructor
public class GameResultDto {

	@Schema(description = "메세지타입")
	private MessageDtoType messageType;
	@Schema(description = "유저들의 닉네임, 스코어 리스트")
	private List<GameResultItem> userResults;

	@Builder
	public GameResultDto(List<GameResultItem> userResults) {

		this.messageType = MessageDtoType.GAMERESULT;
		this.userResults = userResults;
	}
}
