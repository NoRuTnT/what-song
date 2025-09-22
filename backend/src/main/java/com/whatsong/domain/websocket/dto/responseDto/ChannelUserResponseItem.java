package com.whatsong.domain.websocket.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChannelUserResponseItem {
	private long userLevel;
	private String nickname;
	private Boolean isGaming;
}
