package com.whatsong.domain.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import com.whatsong.domain.websocket.model.ChatMessage;
import com.whatsong.domain.websocket.service.GameService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WebSocketController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	private final GameService gameService;

	@MessageMapping("/chat-message/{channelNo}")
	public void sendLobbyChat(
		@DestinationVariable String channelNo,
		@Payload ChatMessage chatMessage,
		@Header("accessToken") String accessToken) {

		logger.info("Request Chat Message. channelNo : {}, chatMessage : {}", channelNo, chatMessage);

		if (chatMessage == null) {
			throw new IllegalArgumentException();
		}

		gameService.sendLobbyMessage(Integer.parseInt(channelNo), chatMessage, accessToken);
	}

	@MessageMapping("/chat-message/{channelNo}/{gameRoomNo}")
	public void sendGameRoomChat(
		@DestinationVariable String channelNo,
		@DestinationVariable String gameRoomNo,
		@Payload ChatMessage chatMessage,
		@Header("accessToken") String accessToken) {

		logger.info("Request Chat Message. channelNo : {}, chatMessage : {}", channelNo, chatMessage);

		if (chatMessage == null) {
			throw new IllegalArgumentException();
		}

		gameService.sendGameRoomMessage(Integer.parseInt(channelNo), Integer.parseInt(gameRoomNo), chatMessage, accessToken);
	}
}
