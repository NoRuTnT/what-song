package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.type.MemberInfoExceptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoException extends RuntimeException {
	private final MemberInfoExceptionType type;

}
