package com.whatsong.domain.member.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponseDto {

	private String logoutResult;
}