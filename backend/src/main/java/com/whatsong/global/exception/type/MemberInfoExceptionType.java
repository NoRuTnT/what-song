package com.whatsong.global.exception.type;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum MemberInfoExceptionType {
	NOT_FOUND_MEMBER_INFO(HttpStatus.NOT_FOUND, 1100, "사용자 정보룰 찾을 수 없습니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	MemberInfoExceptionType(HttpStatus status, Integer code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
