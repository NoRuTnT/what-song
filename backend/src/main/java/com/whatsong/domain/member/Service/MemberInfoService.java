package com.whatsong.domain.member.Service;

import com.whatsong.domain.member.dto.responseDto.MemberInfoResponseDto;
import com.whatsong.domain.member.model.MemberInfo;

public interface MemberInfoService {

	MemberInfoResponseDto getMemberInfo(String token);
}
