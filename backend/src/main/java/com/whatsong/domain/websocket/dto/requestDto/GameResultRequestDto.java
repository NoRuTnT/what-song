package com.whatsong.domain.websocket.dto.requestDto;


import java.util.List;


import com.whatsong.domain.websocket.dto.GameResultMemberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "GameResult를 Kafka로 보낼  Dto")
@Getter
@Builder
public class GameResultRequestDto {
	@Schema(description = "uuid와 score를 List로 가짐")
	private List<GameResultMemberDto> members;

}
