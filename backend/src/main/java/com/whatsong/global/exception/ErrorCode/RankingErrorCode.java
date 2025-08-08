package com.whatsong.global.exception.ErrorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum RankingErrorCode {
	EMPTY_RANK(HttpStatus.NO_CONTENT, 1300, "랭킹이 비어있습니다."),
	NOT_LOGIN(HttpStatus.BAD_REQUEST, 1301, "로그인이 필요한 서비스입니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	RankingErrorCode(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
