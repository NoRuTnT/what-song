package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.type.MemberExceptionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
	private final MemberExceptionType type;

}
