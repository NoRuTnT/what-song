package com.whatsong.global.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsong.domain.websocket.dto.gameMessageDto.GameResultDto;
import com.whatsong.domain.websocket.dto.requestDto.GameOverRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameResultRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameStartRequestDto;
import com.whatsong.domain.websocket.service.GameService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameEventConsumer {

	private final GameService gameService;

	@KafkaListener(topics = "game-event-topic", groupId = "game-log-consumer", containerFactory = "kafkaListenerContainerFactory")
	public void consume(GameEventMessage message) {
		switch (message.getEventType()) {
			case "GAME_START" -> {
				GameStartRequestDto dto = convert(message.getPayload(), GameStartRequestDto.class);
				gameService.saveGameStartLog(dto);
			}
			case "GAME_OVER" -> {
				GameOverRequestDto dto = convert(message.getPayload(), GameOverRequestDto.class);
				gameService.saveGameOverLog(dto);
			}
			case "GAME_RESULT" -> {
				GameResultRequestDto dto = convert(message.getPayload(), GameResultRequestDto.class);
				gameService.updateGameResult(dto);
			}
		}
	}

	//payload를 지정한 타입 clazz의 객체로 변환하는 메소드
	private <T> T convert(Object payload, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(payload, clazz);
	}
}
