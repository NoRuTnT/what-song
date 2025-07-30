package com.whatsong.domain.member.service;

import com.whatsong.domain.member.dto.requestDto.JoinRequestDto;
import com.whatsong.domain.member.dto.requestDto.LoginRequestDto;
import com.whatsong.domain.member.dto.requestDto.ReissueTokenRequestDto;
import com.whatsong.domain.member.dto.responseDto.JoinResponseDto;
import com.whatsong.domain.member.dto.responseDto.LoginResponseDto;
import com.whatsong.domain.member.dto.responseDto.LogoutResponseDto;
import com.whatsong.domain.member.dto.responseDto.ReissueTokenResponseDto;
import com.whatsong.domain.member.dto.responseDto.ValidateDuplicatedLoginIdResponseDto;
import com.whatsong.domain.member.dto.responseDto.ValidateDuplicatedNicknameResponseDto;

public interface MemberService {

	JoinResponseDto signUp(JoinRequestDto joinRequestDto);

	LoginResponseDto login(LoginRequestDto loginRequestDto);

	LogoutResponseDto logout(String token);

	ValidateDuplicatedLoginIdResponseDto validateDuplicatedLoginId(String loginId);

	ValidateDuplicatedNicknameResponseDto validateDuplicatedNickname(String nickname);

	ReissueTokenResponseDto reissueToken(ReissueTokenRequestDto reissueTokenRequestDto);
}
