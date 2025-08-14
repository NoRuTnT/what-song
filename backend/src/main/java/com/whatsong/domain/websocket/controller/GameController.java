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

import io.swagger.v3.oas.annotations.Operation;
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
	@Operation(
		summary = "모든채널 현재인원수 조회",
		description = """
        ### 모든채널 현재인원수 조회
        
        - 채널에 접속한 인원수를 list로 받습니다.
    """)
	@GetMapping("/channel")
	@ResponseBody
	public ResponseEntity<BaseResponse<AllChannelSizeResponseDto>> getAllChannelSize(
		@RequestHeader("accessToken") String accessToken) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.getAllChannelSizeList(accessToken)));
	}

	/**
	 * @param accessToken
	 * @param channelNo
	 * @see ChannelUserResponseDto
	 * @return
	 */
	@Operation(
		summary = "채널의 접속한 모든유저 조회",
		description = """
        ### 채널의 접속한 모든유저 조회
        
        - 채널에 접속해있는 유저정보를 list로 받습니다.
    """)
	@GetMapping("/{channelNo}")
	@ResponseBody
	public ResponseEntity<BaseResponse<ChannelUserResponseDto>> getChannelUsers(
		@RequestHeader("accessToken") String accessToken, @PathVariable int channelNo) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.getUserList(accessToken, channelNo)));
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
			.body(BaseResponse.success(gameService.disconnectUser(accessToken, channelNo)));
	}

	/**
	 * @param accessToken
	 * @param channelNo
	 * @see GameRoomListResponseDto
	 * @return
	 */
	@Operation(
		summary = "채널의 생성된 모든 방 조회",
		description = """
        ### 채널의 생성된 모든 게임룸 조회
        
        - 채널에 생성된 모든 방을 list로 받습니다.
    """)
	@GetMapping("/main/{channelNo}")
	@ResponseBody
	public ResponseEntity<BaseResponse<GameRoomListResponseDto>> getGameRoomList(
		@RequestHeader("accessToken") String accessToken, @PathVariable int channelNo) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.getGameRoomList(accessToken, channelNo)));
	}

	/**
	 * @param accessToken
	 * @param createGameRoomRequestDto
	 * @see CreateGameRoomResponseDto
	 * @return
	 */
	@Operation(
		summary = "방 생성",
		description = """
        ### 게임룸생성
    """)
	@PostMapping("/main/create")
	@ResponseBody
	public ResponseEntity<BaseResponse<CreateGameRoomResponseDto>> createGameRoom(
		@RequestHeader("accessToken") String accessToken,
		@RequestBody CreateGameRoomRequestDto createGameRoomRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.makeGameRoom(accessToken, createGameRoomRequestDto)));
	}

	/**
	 * 비밀번호 체크
	 *
	 * @param checkPasswordRequestDto
	 * @return
	 */
	@Operation(
		summary = "비공개방 비밀번호 체크",
		description = """
        ### 입력한 비밀번호가 올바른지 체크
        
        - 입장시 비밀번호를 체크합니다
        
        - 공개방 항상 TRUE, 비공개방 체크후 TRUE or FALSE
    """)
	@PostMapping("/main/password")
	private ResponseEntity<BaseResponse<CheckPasswordResponseDto>> checkPassword(
		@RequestBody CheckPasswordRequestDto checkPasswordRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.checkPassword(checkPasswordRequestDto)));
	}

	/**
	 * 게임방 입장
	 *
	 * @param accessToken
	 * @param gameRoomNo
	 * @return ResponseEntity<BaseResponse<EnterGameRoomResponseDto>>
	 */
	@Operation(
		summary = "방 입장",
		description = """
        ### gameRoomNo에 맞는 게임룸에 입장합니다.
    """)
	@GetMapping("/main/enter/{gameRoomNo}")
	@ResponseBody
	private ResponseEntity<BaseResponse<EnterGameRoomResponseDto>> enterGameRoom(
		@RequestHeader("accessToken") String accessToken,
		@PathVariable("gameRoomNo") int gameRoomNo) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.enterGameRoom(accessToken, gameRoomNo)));
	}

	/**
	 * 게임방 퇴장
	 *
	 * @param accessToken
	 * @param exitGameRoomRequestDto
	 * @see ExitGameRoomResponse
	 * @return
	 */
	@Operation(
		summary = "방 퇴장",
		description = """
        ### 게임룸에서 나갑니다.
    """)
	@PatchMapping("/main/exit")
	@ResponseBody
	private ResponseEntity<BaseResponse<ExitGameRoomResponse>> exitGameRoom(
		@RequestHeader("accessToken") String accessToken, @RequestBody ExitGameRoomRequestDto exitGameRoomRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.exitGameRoom(accessToken, exitGameRoomRequestDto)));
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
			.body(BaseResponse.success(gameService.saveGameStartLog(gameStartRequestDto)));
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
			.body(BaseResponse.success(gameService.saveGameOverLog(gameOverRequestDto)));
	}

	/**
	 * 게임 방 정보 변경
	 *
	 * @param accessToken
	 * @param modifyGameRoomInformationRequestDto
	 * @return
	 */
	@Operation(
		summary = "방 설정 변경",
		description = """
        ### 방설정을 변경합니다.
    """)
	@PatchMapping("/main/modify")
	private ResponseEntity<BaseResponse<ModifyGameRoomInformationResponseDto>> modifyGameRoomInformation(
		@RequestHeader("accessToken") String accessToken,
		@RequestBody ModifyGameRoomInformationRequestDto modifyGameRoomInformationRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(gameService.modifyGameRoomInformation(accessToken, modifyGameRoomInformationRequestDto)));
	}
}