package com.whatsong.domain.member.data;

import java.util.Arrays;

import com.whatsong.global.exception.exception.MemberException;
import com.whatsong.global.exception.ErrorCode.MemberErrorCode;

import lombok.Getter;

@Getter
public enum LoginType {
	SOCIAL("SOCIAL"),
	SIMPLE("SIMPLE");

	private final String type;

	LoginType(String type) {
		this.type = type;
	}

	public static LoginType ofType(String type) {
		return Arrays.stream(LoginType.values())
			.filter(value -> value.getType().equals(type))
			.findAny()
			.orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_LOGIN_TYPE));
	}

}
