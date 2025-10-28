package com.whatsong.domain.music.dto.requestDto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "musicSet 내부 문제 생성 요청 DTO")
@Getter
@Builder
public class CreateMusicRequestDto {

	@Schema(description = "음원재생 유튜브링크")
	private String url;

	@Schema(description = "첫번째힌트")
	private String hint1;

	@Schema(description = "두번째힌트")
	private String hint2;

	@Schema(description = "유저에게 표기될 정답")
	private String answer;

	@Schema(description = "유사정답처리목록")
	private List<String> answers;

	@Schema(description = "재생시간")
	private Integer lengthSec;

	@Schema(description = "시작시간 기본값=0")
	private Integer startSec;
}
