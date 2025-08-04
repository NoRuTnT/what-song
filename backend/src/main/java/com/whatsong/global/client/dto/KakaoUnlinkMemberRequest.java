package com.whatsong.global.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import feign.form.FormProperty;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUnlinkMemberRequest {
	@FormProperty("target_id")
	private Long targetId;

	@FormProperty("target_id_type")
	private String targetIdType = "user_id";
}
