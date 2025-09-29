package com.whatsong.global.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import com.whatsong.domain.websocket.dto.requestDto.GameOverRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameStartRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameResultRequestDto;
import com.whatsong.global.client.dto.ClickEventRequestDto;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GameEventProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;


	public void sendGameStartEvent(GameStartRequestDto requestDto) {
		kafkaTemplate.send("game-event-topic",
			GameEventMessage.builder()
				.eventType("GAME_START")
				.payload(requestDto)
				.build());
	}

	public void sendGameOverEvent(GameOverRequestDto requestDto) {
		kafkaTemplate.send("game-event-topic",
			GameEventMessage.builder()
				.eventType("GAME_OVER")
				.payload(requestDto)
				.build());
	}

	public void sendGameResultEvent(GameResultRequestDto resultDto) {
		kafkaTemplate.send("game-event-topic",
			GameEventMessage.builder()
				.eventType("GAME_RESULT")
				.payload(resultDto)
				.build());
	}

	public void sendClickEvent(ClickEventRequestDto clickDto) {
		kafkaTemplate.send(
			"click-event-topic",
			GameEventMessage.builder()
				.eventType("CLICK_EVENT")
				.payload(clickDto)
				.build()
		);
	}

}
