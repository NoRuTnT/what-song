package com.whatsong.global.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.cloud.openfeign.FeignClient;

import com.whatsong.global.client.dto.KakaoUnlinkMemberRequest;
import com.whatsong.global.client.dto.KakaoUnlinkMemberResponse;
import com.whatsong.global.client.dto.KakaoMemberInfoResponse;
import com.whatsong.global.openfeign.KakaoKapiConfig;

@FeignClient(
	name = "KakaoKapiClient",
	url = "https://kapi.kakao.com",
	configuration = KakaoKapiConfig.class
)
@Component
public interface KakaoKapiClient {

	@GetMapping(
		path = "/v2/user/me",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	)
	KakaoMemberInfoResponse getMemberInfo(@RequestHeader("Authorization") String accessToken);

	@PostMapping(
		path = "/v1/user/unlink",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	)
	KakaoUnlinkMemberResponse unlinkMember(@RequestHeader("Authorization") String accessToken);

	@PostMapping(
		path = "/v1/user/unlink",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	)
	KakaoUnlinkMemberResponse unlinkMemberByAdminKey(
		@RequestHeader("Authorization") String appAdminKeyWithPrefix,
		@RequestBody KakaoUnlinkMemberRequest unlinkUser
	);
}
