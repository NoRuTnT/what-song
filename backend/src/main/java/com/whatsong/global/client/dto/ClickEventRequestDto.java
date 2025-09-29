package com.whatsong.global.client.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEventRequestDto {
	private UUID userId;
	private String element;
	private long timestamp;
}