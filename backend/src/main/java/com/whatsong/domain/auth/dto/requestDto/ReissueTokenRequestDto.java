package com.whatsong.domain.auth.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "토큰재발급 요청 DTO")
@Getter
public class ReissueTokenRequestDto {

	@Schema(description = "기존 리프레쉬토큰")
	private String refreshToken;
}