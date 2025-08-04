package com.whatsong.domain.auth.service;

import com.whatsong.domain.auth.dto.requestDto.SignupRequestDto;
import com.whatsong.domain.auth.dto.requestDto.LoginRequestDto;
import com.whatsong.domain.auth.dto.requestDto.ReissueTokenRequestDto;
import com.whatsong.domain.auth.dto.requestDto.SocialLoginRequestDto;
import com.whatsong.domain.auth.dto.responseDto.SignupResponseDto;
import com.whatsong.domain.auth.dto.responseDto.LoginResponseDto;
import com.whatsong.domain.auth.dto.responseDto.LogoutResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ReissueTokenResponseDto;
import com.whatsong.domain.auth.dto.responseDto.SocialLoginResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ValidateDuplicatedLoginIdResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ValidateDuplicatedNicknameResponseDto;

public interface AuthService {

	SignupResponseDto signUp(SignupRequestDto signupRequestDto);

	LoginResponseDto login(LoginRequestDto loginRequestDto);

	SocialLoginResponseDto socialLogin(SocialLoginRequestDto socialLoginRequestDto, String deviceId);

	LogoutResponseDto logout(String token);

	ValidateDuplicatedLoginIdResponseDto validateDuplicatedLoginId(String loginId);

	ValidateDuplicatedNicknameResponseDto validateDuplicatedNickname(String nickname);

	ReissueTokenResponseDto reissueToken(ReissueTokenRequestDto reissueTokenRequestDto);
}
