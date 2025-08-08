package com.whatsong.global.exception.exception;

import com.whatsong.global.exception.ErrorCode.RankingErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingException extends RuntimeException {
	private final RankingErrorCode code;
}
