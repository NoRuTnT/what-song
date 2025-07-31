package com.whatsong.domain.auth.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponseDto {

	private String logoutResult;
}