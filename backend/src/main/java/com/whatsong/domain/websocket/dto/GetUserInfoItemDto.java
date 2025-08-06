package com.whatsong.domain.websocket.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserInfoItemDto {
	private String nicknames;
	private String exps;
}