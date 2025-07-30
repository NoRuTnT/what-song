package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.MemberErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
	private final MemberErrorCode code;

}
