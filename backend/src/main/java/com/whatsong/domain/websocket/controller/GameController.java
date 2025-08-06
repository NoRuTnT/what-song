package com.whatsong.domain.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.whatsong.domain.websocket.dto.requestDto.CheckPasswordRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.CreateGameRoomRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.ExitGameRoomRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameOverRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.GameStartRequestDto;
import com.whatsong.domain.websocket.dto.requestDto.ModifyGameRoomInformationRequestDto;
import com.whatsong.domain.websocket.dto.responseDto.AllChannelSizeResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.ChannelUserResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.CheckPasswordResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.CreateGameRoomResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.DisconnectSocketResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.EnterGameRoomResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.ExitGameRoomResponse;
import com.whatsong.domain.websocket.dto.responseDto.GameOverResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.GameRoomListResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.GameStartResponseDto;
import com.whatsong.domain.websocket.dto.responseDto.ModifyGameRoomInformationResponseDto;
import com.whatsong.domain.websocket.model.ChatMessage;
import com.whatsong.domain.websocket.service.GameService;
import com.whatsong.global.common.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	private final GameService gameService;

	/**
	 * @param accessToken
	 * @see AllChannelSizeResponseDto
	 * @return
	 */
	@GetMapping("/channel")
	@ResponseBody
	public ResponseEntity<BaseResponse<AllChannelSizeResponseDto>> getAllChannelSize(
		@RequestHeader("accessToken") String accessToken) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<AllChannelSizeResponseDto>builder()
				.data(gameService.getAllChannelSizeList(accessToken))
				.build());
	}

	/**
	 * @param accessToken
	 * @param channelNo
	 * @see ChannelUserResponseDto
	 * @return
	 */
	@GetMapping("/{channelNo}")
	@ResponseBody
	public ResponseEntity<BaseResponse<ChannelUserResponseDto>> getChannelUsers(
		@RequestHeader("accessToken") String accessToken, @PathVariable int channelNo) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<ChannelUserResponseDto>builder()
				.data(gameService.getUserList(accessToken, channelNo))
				.build());
	}

	/**
	 * @param accessToken
	 * @param channelNo
	 * @return
	 */
	@PostMapping("/{channelNo}")
	@ResponseBody
	public ResponseEntity<BaseResponse<DisconnectSocketResponseDto>> disconnectSocket(
		@RequestHeader("accessToken") String accessToken, @PathVariable int channelNo) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<DisconnectSocketResponseDto>builder()
				.data(gameService.disconnectUser(accessToken, channelNo))
				.build());
	}

	/**
	 * @param accessToken
	 * @param channelNo
	 * @see GameRoomListResponseDto
	 * @return
	 */
	@GetMapping("/main/{channelNo}")
	@ResponseBody
	public ResponseEntity<BaseResponse<GameRoomListResponseDto>> getGameRoomList(
		@RequestHeader("accessToken") String accessToken, @PathVariable int channelNo) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<GameRoomListResponseDto>builder()
				.data(gameService.getGameRoomList(accessToken, channelNo))
				.build());
	}

	/**
	 * @param accessToken
	 * @param createGameRoomRequestDto
	 * @see CreateGameRoomResponseDto
	 * @return
	 */
	@PostMapping("/main/create")
	@ResponseBody
	public ResponseEntity<BaseResponse<CreateGameRoomResponseDto>> createGameRoom(
		@RequestHeader("accessToken") String accessToken,
		@RequestBody CreateGameRoomRequestDto createGameRoomRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<CreateGameRoomResponseDto>builder()
				.data(gameService.makeGameRoom(accessToken, createGameRoomRequestDto))
				.build());
	}

	/**
	 * 비밀번호 체크
	 *
	 * @param checkPasswordRequestDto
	 * @return
	 */
	@PostMapping("/main/password")
	private ResponseEntity<BaseResponse<CheckPasswordResponseDto>> checkPassword(
		@RequestBody CheckPasswordRequestDto checkPasswordRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<CheckPasswordResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(gameService.checkPassword(checkPasswordRequestDto))
				.build());
	}

	/**
	 * 게임방 입장
	 *
	 * @param accessToken
	 * @param gameRoomNo
	 * @return ResponseEntity<BaseResponse<EnterGameRoomResponseDto>>
	 */
	@GetMapping("/main/enter/{gameRoomNo}")
	@ResponseBody
	private ResponseEntity<BaseResponse<EnterGameRoomResponseDto>> enterGameRoom(
		@RequestHeader("accessToken") String accessToken,
		@PathVariable("gameRoomNo") int gameRoomNo) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<EnterGameRoomResponseDto>builder()
				.data(gameService.enterGameRoom(accessToken, gameRoomNo))
				.build());
	}

	/**
	 * 게임방 퇴장
	 *
	 * @param accessToken
	 * @param exitGameRoomRequestDto
	 * @see ExitGameRoomResponse
	 * @return
	 */
	@PatchMapping("/main/exit")
	@ResponseBody
	private ResponseEntity<BaseResponse<ExitGameRoomResponse>> exitGameRoom(
		@RequestHeader("accessToken") String accessToken, @RequestBody ExitGameRoomRequestDto exitGameRoomRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<ExitGameRoomResponse>builder()
				.data(gameService.exitGameRoom(accessToken, exitGameRoomRequestDto))
				.build());
	}

	@MessageMapping("/chat-message/{channelNo}")
	public void sendChatMessage(@DestinationVariable("channelNo") String channelNo, @Payload ChatMessage chatMessage,
		@Header("accessToken") String accessToken) {
		logger.info("Request Chat Message. channelNo : {}, chatMessage : {}", channelNo, chatMessage);

		if (chatMessage == null) {
			throw new IllegalArgumentException();
		}

		gameService.sendMessage(Integer.parseInt(channelNo), chatMessage, accessToken);
	}

	/**
	 * 게임 시작 로그 생성
	 *
	 * @param gameStartRequestDto
	 * @return
	 */
	@PostMapping("/start")
	private ResponseEntity<BaseResponse<GameStartResponseDto>> gameStart(
		@RequestBody GameStartRequestDto gameStartRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<GameStartResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(gameService.saveMultiModeGameStartLog(gameStartRequestDto))
				.build());
	}

	/**
	 * 게임 종료 로그 생성
	 *
	 * @param gameOverRequestDto
	 * @return
	 */
	@PostMapping("/over")
	private ResponseEntity<BaseResponse<GameOverResponseDto>> gameOver(
		@RequestBody GameOverRequestDto gameOverRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<GameOverResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(gameService.saveMultiModeGameOverLog(gameOverRequestDto))
				.build());
	}

	/**
	 * 게임 방 정보 변경
	 *
	 * @param accessToken
	 * @param modifyGameRoomInformationRequestDto
	 * @return
	 */
	@PatchMapping("/main/modify")
	private ResponseEntity<BaseResponse<ModifyGameRoomInformationResponseDto>> modifyGameRoomInformation(
		@RequestHeader("accessToken") String accessToken,
		@RequestBody ModifyGameRoomInformationRequestDto modifyGameRoomInformationRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.<ModifyGameRoomInformationResponseDto>builder()
				.code(HttpStatus.OK.value())
				.data(gameService.modifyGameRoomInformation(accessToken, modifyGameRoomInformationRequestDto))
				.build());
	}
}