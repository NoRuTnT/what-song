package com.whatsong.domain.auth.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "토큰 재발급성공 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenResponseDto {

	@Schema(description = "발급받은 AT")
	private String accessToken;

	@Schema(description = "발급받은 RT")
	private String refreshToken;

}