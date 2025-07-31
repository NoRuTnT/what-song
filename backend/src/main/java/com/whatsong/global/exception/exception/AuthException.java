package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.AuthErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {
	private final AuthErrorCode code;

}
