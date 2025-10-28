package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.InGameErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InGameException extends RuntimeException{
	private final InGameErrorCode info;
}
