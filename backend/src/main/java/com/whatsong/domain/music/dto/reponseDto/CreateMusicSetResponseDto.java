package com.whatsong.domain.music.dto.reponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMusicSetResponseDto {
	private Integer musicSetId;
	private Integer quizAmount;
}
