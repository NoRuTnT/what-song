package com.whatsong.domain.member.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.whatsong.domain.member.dto.responseDto.MemberInfoResponseDto;
import com.whatsong.domain.member.model.MemberInfo;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.global.exception.ErrorCode.MemberInfoErrorCode;
import com.whatsong.global.exception.exception.MemberInfoException;
import com.whatsong.global.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

	private final MemberInfoRepository memberInfoRepository;
	private final JwtValidator jwtValidator;

	@Override
	public MemberInfoResponseDto getMemberInfo(String token) {
		UUID memberId = jwtValidator.getData(token);

		MemberInfo memberInfo = memberInfoRepository.findByIdAndDeletedFalse(memberId)
			.orElseThrow(() -> new MemberInfoException(MemberInfoErrorCode.NOT_FOUND_MEMBER_INFO));

		return MemberInfoResponseDto.builder()
			.nickname(memberInfo.getNickname())
			.exp(memberInfo.getExp())
			.level(memberInfo.getLevel())
			.wins(memberInfo.getWins())
			.build();

	}
}
