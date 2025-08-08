package com.whatsong.domain.ranking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsong.domain.ranking.dto.responseDto.FullRankResponseDto;
import com.whatsong.domain.ranking.dto.responseDto.MyRankResponseDto;
import com.whatsong.domain.ranking.service.RankingService;
import com.whatsong.global.redis.RedisService;
import com.whatsong.global.common.response.BaseResponse;
import com.whatsong.global.redis.RedisScheduler;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

	private final RankingService rankingService;
	private final RedisScheduler redisScheduler;
	private final RedisService redisService;

	@GetMapping("/myranking")
	private ResponseEntity<BaseResponse<MyRankResponseDto>> getMyRanking(@PathParam("nickname") String nickname) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(rankingService.RankNumToDto(nickname)));
	}

	@GetMapping("/fullranking")
	private ResponseEntity<BaseResponse<FullRankResponseDto>> getFullRanking(@PathParam("nickname") String nickname) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(rankingService.getFullRank(nickname, 100)));
	}

	@PostMapping("/testadd")
	private BaseResponse<?> testAdd(@PathParam("nickname") String nickname, @PathParam("exp") double exp) {
		try {
			rankingService.testAdd(nickname, exp);
			return BaseResponse.builder().code(HttpStatus.OK.value()).build();
		} catch (Exception e) {
			return BaseResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
		}
	}

	@DeleteMapping("/testclear")
	private BaseResponse<?> testClear() {
		try {
			rankingService.testClear();
			return BaseResponse.builder().code(HttpStatus.OK.value()).build();
		} catch (Exception e) {
			return BaseResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
		}
	}

	@PostMapping("/into-redis")
	private void insertRanking(){
		redisScheduler.insertRankingToRedis();
	}

	@DeleteMapping("/init-redis")
	private void deleteRanking(){
		redisService.deleteKeyInRedis("ranking");
	}

}