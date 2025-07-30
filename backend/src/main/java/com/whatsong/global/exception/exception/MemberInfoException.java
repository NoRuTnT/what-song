package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.MemberInfoErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoException extends RuntimeException {
	private final MemberInfoErrorCode code;

}
