package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.MultiModeErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MultiModeException extends RuntimeException{
	private final MultiModeErrorCode info;
}
