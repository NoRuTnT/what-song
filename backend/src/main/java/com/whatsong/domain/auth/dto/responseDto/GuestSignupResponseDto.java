package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게스트 회원가입성공 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestSignupResponseDto {

	@Schema(description = "랜덤생성된 게스트memberId 이자 nickname")
	private String nickname;

	public static GuestSignupResponseDto of(String nickname) {
		return GuestSignupResponseDto.builder()
			.nickname(nickname)
			.build();
	}
}
