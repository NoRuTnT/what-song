package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그아웃 성공 응답 DTO")
@Getter
@Builder
public class LogoutResponseDto {
	@Schema(description = "로그아웃결과, default = SUCCESS")
	private String logoutResult;
}