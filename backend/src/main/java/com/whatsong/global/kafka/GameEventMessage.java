package com.whatsong.global.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEventMessage {

	// GAME_START, GAME_OVER, GAME_RESULT
	private String eventType;
	private Object payload;
}