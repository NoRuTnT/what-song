package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입성공 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {

	@Schema(description = "닉네임")
	private String nickname;

	public static SignupResponseDto of(String nickname) {
		return SignupResponseDto.builder()
			.nickname(nickname)
			.build();
	}
}