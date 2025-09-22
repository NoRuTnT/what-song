package com.whatsong.domain.member.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "memberInfo 게임데이터 호출 응답 DTO")
@Getter
@Builder
public class MemberInfoResponseDto {

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "경험치")
	private Double exp;

	@Schema(description = "레벨")
	private Long level;

	@Schema(description = "승리횟수")
	private Long wins;
}
