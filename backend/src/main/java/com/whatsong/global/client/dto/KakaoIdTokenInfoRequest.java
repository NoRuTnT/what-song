package com.whatsong.global.client.dto;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoIdTokenInfoRequest {

	@FormProperty("id_token")
	private String idToken;

}