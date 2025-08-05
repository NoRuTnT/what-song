package com.whatsong.domain.auth.data;

import java.util.Arrays;

import com.whatsong.global.exception.exception.AuthException;
import com.whatsong.global.exception.ErrorCode.AuthErrorCode;

import lombok.Getter;

@Getter
public enum LoginType {
	KAKAO("KAKAO"),
	LOCAL("LOCAL"),
	GUEST("GUEST"),
	TEST("TEST");

	private final String type;

	LoginType(String type) {
		this.type = type;
	}

	public static LoginType ofType(String type) {
		return Arrays.stream(LoginType.values())
			.filter(value -> value.getType().equals(type))
			.findAny()
			.orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_LOGIN_TYPE));
	}

}
