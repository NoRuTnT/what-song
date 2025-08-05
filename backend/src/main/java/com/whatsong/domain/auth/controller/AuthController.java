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
import com.whatsong.domain.auth.dto.responseDto.GuestSignupResponseDto;
import com.whatsong.domain.auth.dto.responseDto.SignupResponseDto;
import com.whatsong.domain.auth.dto.responseDto.LoginResponseDto;
import com.whatsong.domain.auth.dto.responseDto.LogoutResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ReissueTokenResponseDto;
import com.whatsong.domain.auth.dto.responseDto.SocialLoginResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ValidateDuplicatedLoginIdResponseDto;
import com.whatsong.domain.auth.dto.responseDto.ValidateDuplicatedNicknameResponseDto;
import com.whatsong.domain.auth.service.AuthService;
import com.whatsong.global.annotation.DisableSwaggerAuthButton;
import com.whatsong.global.common.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;


	@Operation(
		summary = "회원가입",
		description = """
        ### 회원가입을 진행합니다. 
    """,
		responses = {
			@ApiResponse(responseCode = "200", description = "회원가입 성공"),
		}
	)
	@PostMapping("/signup")
	private ResponseEntity<BaseResponse<SignupResponseDto>> signUp(@RequestBody SignupRequestDto signupRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.signUp(signupRequestDto)));
	}

	@Operation(
		summary = "게스트계정생성",
		description = """
        ### 임시용 게스트계정을 생성합니다.
        
        - 게스트계정 memberId와 nickname은 guest + 5자리의 숫자로 랜덤생성됩니다. 
    """,
		responses = {
			@ApiResponse(responseCode = "200", description = "guest계정생성성공"),
		}
	)
	@PostMapping("/guest")
	private ResponseEntity<BaseResponse<GuestSignupResponseDto>> guestSignUp() {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.guestSignUp()));
	}

	@DisableSwaggerAuthButton
	@Operation(
		summary = "일반로그인 and guest로그인",
		description = """
        ### 이미 회원인 경우 로그인을, 그렇지 않다면 회원가입을 진행합니다.
        
        - 회원가입을 진행한 유저는 닉네임이 카카오톡 닉네임으로 설정됩니다.
    """,
		responses = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "403", description = "유효하지 않은 토큰")
		}
	)
	@PostMapping("/login")
	private ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.login(loginRequestDto)));
	}


	@DisableSwaggerAuthButton
	@Operation(
		summary = "로그인 or 회원가입",
		description = """
        ### 이미 회원인 경우 로그인을, 그렇지 않다면 회원가입을 진행합니다.
        
        - 회원가입을 진행한 유저는 닉네임이 카카오톡 닉네임으로 설정됩니다.
    """,
		responses = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "201", description = "회원가입 성공"),
			@ApiResponse(responseCode = "403", description = "유효하지 않은 토큰")
		}
	)
	@PostMapping("/social-login")
	private ResponseEntity<BaseResponse<SocialLoginResponseDto>> socialLogin(@RequestHeader("DEVICE_ID") String deviceId, @RequestBody SocialLoginRequestDto socialLoginRequestDto) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.socialLogin(socialLoginRequestDto,deviceId)));
	}

	@Operation(
		summary = "로그아웃",
		description = """
        ### 로그아웃
        
        - 클라이언트에서 사용하던 Access Token과 Refresh Token이 서버 측에서 파기됩니다.
        
        - 파기된 토큰은 재사용이 불가능하고, 새롭게 로그인을 하여 발급받아야 합니다.
    """)
	@DeleteMapping("/logout")
	private ResponseEntity<BaseResponse<LogoutResponseDto>> logout(@RequestHeader("accessToken") String token) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.logout(token)));
	}


	@Operation(
		summary = "로그인 아이디 중복검사",
		description = """
        ### 로그인 아이디가 중복인지 검사합니다.
    """)
	@GetMapping("/validate-login-id/{login-id}")
	private ResponseEntity<BaseResponse<ValidateDuplicatedLoginIdResponseDto>> validateDuplicatedLoginId(
		@PathVariable("login-id") String loginId) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.validateDuplicatedLoginId(loginId)));
	}

	@Operation(
		summary = "닉네임 중복검사",
		description = """
        ### 닉네임이 중복인지 검사합니다.
    """)
	@GetMapping("/validate-nickname/{nickname}")
	private ResponseEntity<BaseResponse<ValidateDuplicatedNicknameResponseDto>> validateDuplicatedNickname(
		@PathVariable("nickname") String nickname) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.validateDuplicatedNickname(nickname)));
	}

	@DisableSwaggerAuthButton
	@Operation(
		summary = "토큰 refresh",
		description = """
        ### 새로운 Access Token, Refresh Token을 재발급합니다.
        
        - 전송한 Access Token과 Refresh Token이 서버 측에서 파기됩니다.
        
        - 해당 요청 이후에는 새롭게 발급된 토큰을 사용해야 합니다.
    """,
		responses = {
			@ApiResponse(responseCode = "200", description = "발급완료"),
			@ApiResponse(responseCode = "403", description = "리프레시토큰 만료. 재로그인 필요")
		}
	)
	@PostMapping("/token")
	private ResponseEntity<BaseResponse<ReissueTokenResponseDto>> reissueToken(
		@RequestBody ReissueTokenRequestDto reissueTokenRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.reissueToken(reissueTokenRequestDto)));
	}
}
