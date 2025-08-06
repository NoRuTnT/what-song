package com.whatsong.domain.music.dto.queryDto;

import lombok.Getter;

@Getter
public class FindAnswerDto {

	private String title;

	public FindAnswerDto(String title) {
		this.title = title;
	}
}
