package com.whatsong.domain.member.service;

import com.whatsong.domain.member.dto.responseDto.MemberInfoResponseDto;

public interface MemberInfoService {

	MemberInfoResponseDto getMemberInfo(String token);
}
