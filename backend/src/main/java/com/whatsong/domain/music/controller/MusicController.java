package com.whatsong.domain.music.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsong.domain.music.dto.reponseDto.CreateMusicSetResponseDto;
import com.whatsong.domain.music.dto.requestDto.CreateMusicSetRequestDto;
import com.whatsong.domain.music.service.MusicService;
import com.whatsong.global.common.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

	private final MusicService musicService;

	@Operation(
		summary = "퀴즈 생성하기",
		description = """
        ### 퀴즈를 생성합니다.
    """)
	@PostMapping("/create")
	private ResponseEntity<BaseResponse<CreateMusicSetResponseDto>> create(@RequestHeader("accessToken") String token,
		@RequestBody CreateMusicSetRequestDto request) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(musicService.createSet(token, request)));
	}


}
