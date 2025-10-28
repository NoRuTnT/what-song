package com.whatsong.domain.music.dto.requestDto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "musicSet 생성 요청 DTO")
@Getter
@Builder
public class CreateMusicSetRequestDto {

	@Schema(description = "제목")
	private String title;

	@Schema(description = "설명")
	private String description;

	@Schema(description = "문제수")
	private Integer quizAmount;

	@Schema(description = "추가될 music들")
	private List<CreateMusicRequestDto> musics;
}
