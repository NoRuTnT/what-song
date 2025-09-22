package com.whatsong.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsong.domain.auth.dto.responseDto.ValidateDuplicatedLoginIdResponseDto;
import com.whatsong.domain.member.Service.MemberInfoService;
import com.whatsong.domain.member.Service.MemberInfoServiceImpl;
import com.whatsong.domain.member.dto.responseDto.MemberInfoResponseDto;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.global.common.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberInfoService memberInfoService;

	@Operation(
		summary = "내 정보 불러오기",
		description = """
        ### AT를 이용해서 내 게임정보를 불러옵니다.
    """)
	@GetMapping("/myinfo")
	private ResponseEntity<BaseResponse<MemberInfoResponseDto>> myInfo(@RequestHeader("accessToken") String token) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(memberInfoService.getMemberInfo(token)));
	}
}
