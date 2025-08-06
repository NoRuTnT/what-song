package com.whatsong.domain.websocket.dto.gameMessageDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameResultItem {

	private String nickname;
	private Double score;

	@Builder
	public GameResultItem(String nickname, Double score) {
		this.nickname = nickname;
		this.score = score;
	}
}
