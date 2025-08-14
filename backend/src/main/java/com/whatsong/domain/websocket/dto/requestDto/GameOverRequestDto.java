package com.whatsong.domain.websocket.dto.requestDto;

import java.time.LocalDateTime;
import java.util.List;

import com.whatsong.domain.websocket.dto.gameMessageDto.GameResultItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "게임종료후 로그를 찍기위해 Kafka로 보낼 Dto")
@Getter
@Builder
public class GameOverRequestDto {
	@Schema(description = "unique한 게임방 생성로그 id")
	private int createGameRoomLogId;
	@Schema(description = "방이름")
	private String title;
	@Schema(description = "연도")
	private String years;
	@Schema(description = "참여한 전체유저의 닉네임과 점수")
	private List<GameResultItem> members;
	@Schema(description = "종료시각")
	private LocalDateTime endedAt;
	@Schema(description = "전체플레이타임")
	private int playTime;
}
