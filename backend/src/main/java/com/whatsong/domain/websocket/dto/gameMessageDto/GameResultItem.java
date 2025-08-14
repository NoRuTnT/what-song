package com.whatsong.domain.websocket.dto.gameMessageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게임종료후 결과창, 로그남기기를 위한 dto")
@Getter
@NoArgsConstructor
@Embeddable
public class GameResultItem {

	@Schema(description = "유저닉네임")
	private String nickname;
	@Schema(description = "획득한점수")
	private Double score;

	@Builder
	public GameResultItem(String nickname, Double score) {
		this.nickname = nickname;
		this.score = score;
	}
}
