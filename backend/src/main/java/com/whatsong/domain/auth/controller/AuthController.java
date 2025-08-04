package com.whatsong.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.whatsong.domain.auth.service.AuthService;
import com.whatsong.global.common.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 회원가입
	 *
	 * @param signupRequestDto
	 * @see SignupResponseDto
	 * @return ResponseEntity<BaseResponse < JoinResponseDto>>
	 */
	@PostMapping("/signup")
	private ResponseEntity<BaseResponse<SignupResponseDto>> signUp(@RequestBody SignupRequestDto signupRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.signUp(signupRequestDto)));
	}

	/**
	 * 로그아웃
	 *
	 * @param token
	 * @return
	 */
	@DeleteMapping("/logout")
	private ResponseEntity<BaseResponse<LogoutResponseDto>> logout(@RequestHeader("accessToken") String token) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.logout(token)));
	}

	/**
	 * 로그인
	 *
	 * @param loginRequestDto
	 * @see LoginResponseDto
	 * @return ResponseEntity<BaseResponse < LoginResponseDto>>
	 */
	@PostMapping("/login")
	private ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.login(loginRequestDto)));
	}


	@PostMapping("/social-login")
	private ResponseEntity<BaseResponse<SocialLoginResponseDto>> socialLogin(@RequestHeader("DEVICE_ID") String deviceId, @RequestBody SocialLoginRequestDto socialLoginRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.socialLogin(socialLoginRequestDto,deviceId)));
	}



	/**
	 * 로그인 아이디 중복 검사
	 *
	 * @param loginId
	 * @see ValidateDuplicatedLoginIdResponseDto
	 * @return ResponseEntity<BaseResponse < ValidateDuplicatedLoginIdResponseDto>>
	 */
	@GetMapping("/validate-login-id/{login-id}")
	private ResponseEntity<BaseResponse<ValidateDuplicatedLoginIdResponseDto>> validateDuplicatedLoginId(
		@PathVariable("login-id") String loginId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.validateDuplicatedLoginId(loginId)));
	}

	/**
	 * 닉네임 중복 검사
	 *
	 * @param nickname
	 * @see ValidateDuplicatedNicknameResponseDto
	 * @return ResponseEntity<BaseResponse < ValidateDuplicatedNicknameResponseDto>>
	 */
	@GetMapping("/validate-nickname/{nickname}")
	private ResponseEntity<BaseResponse<ValidateDuplicatedNicknameResponseDto>> validateDuplicatedNickname(
		@PathVariable("nickname") String nickname) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.validateDuplicatedNickname(nickname)));
	}

	/**
	 * 토큰 재발급
	 *
	 * @param reissueTokenRequestDto
	 * @see ReissueTokenResponseDto
	 * @return ResponseEntity<BaseResponse < ReissueTokenResponseDto>>
	 */
	@PostMapping("/token")
	private ResponseEntity<BaseResponse<ReissueTokenResponseDto>> reissueToken(
		@RequestBody ReissueTokenRequestDto reissueTokenRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.reissueToken(reissueTokenRequestDto)));
	}
}
