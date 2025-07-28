package com.whatsong.global;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisService {
	private final RedisTemplate<String, String> redisTemplate;

	public enum RedisKey {
		RANKING("expranking");

		private final String key;

		RedisKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}

	/**
	 * 리프레쉬토큰 저장
	 *
	 * @param memberId
	 * @param refreshToken
	 */
	public void saveRefreshToken(UUID memberId, String refreshToken) {
		String key = memberId.toString();
		redisTemplate.opsForValue().set(key, refreshToken, 1, TimeUnit.DAYS);
	}

	public String getRefreshToken(String memberId) {
		return redisTemplate.opsForValue().get(memberId);
	}

	/**
	 * 해당 키의 value들을 모두 삭제
	 * @param key
	 */
	public void deleteKeyInRedis(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * SortedSet에 데이터를 삽입하는 method
	 * @param member
	 * @param score
	 */
	public void insertDatatoRedisSortedSet(String key, String member, double score) {
		ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
		zSetOps.add(key, member, score);
	}

	public Double getScorefromSortedSet(String key, String member) {
		ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
		return zSetOps.score(key, member);
	}

	public Long countInSortedSet(String key, Double min, Double max) {
		ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
		return zSetOps.count(key, min, max);
	}

}
