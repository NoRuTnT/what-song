package com.whatsong.global.redis;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.whatsong.domain.member.model.MemberInfo;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.domain.websocket.service.GameService;
import com.whatsong.global.redis.RedisService.RedisKey;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisScheduler {
	private final RedisService redisService;
	private final MemberInfoRepository memberInfoRepository;
	private final GameService gameService;


	// 매일 새벽 4시 0분 0초마다 실행됨
	@Scheduled(cron = "0 0 4 * * *")
	private void deleteRanking(){

		redisService.deleteKeyInRedis("ranking");
	}

	// 매일 새벽 4시 0분 1초마다 실행됨
	@Scheduled(cron = "1 0 4 * * *")
	private void insertRanking(){

		insertRankingToRedis();
	}

	//1초마다 모든 멀티방 -1
	@Scheduled(cron = "*/1 * * * * *")
	private void multiModeCountDown(){
		gameService.pubMessage();
	}

	public void insertRankingToRedis() {
		int num = 100;
		Pageable pageRequest = PageRequest.of(0, num, Sort.by(Sort.Order.desc("exp")));
		List<MemberInfo> rankingList = memberInfoRepository.findByDeleted(false, pageRequest);

		for(MemberInfo memberInfo : rankingList) {
			redisService.insertDatatoRedisSortedSet(RedisKey.RANKING.getKey(), memberInfo.getNickname(), memberInfo.getExp());
		}
	}

}
