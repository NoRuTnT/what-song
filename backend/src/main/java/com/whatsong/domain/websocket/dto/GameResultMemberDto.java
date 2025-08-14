package com.whatsong.domain.websocket.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Kafka로 보낼 GameResultMemberDto를 구성 DTO")
@Getter
@Builder
public class GameResultMemberDto {
	@Schema(description = "사용자 uuid")
	private UUID uuid;
	@Schema(description = "사용자 점수")
	private double score;
}
