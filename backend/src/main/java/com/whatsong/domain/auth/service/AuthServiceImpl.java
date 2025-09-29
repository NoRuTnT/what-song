package com.whatsong.domain.auth.service;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whatsong.domain.auth.data.LoginType;
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
import com.whatsong.domain.auth.service.provider.KakaoMemberProvider;
import com.whatsong.domain.member.model.Member;
import com.whatsong.domain.member.model.MemberInfo;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.domain.auth.repository.AuthRepository;
import com.whatsong.global.client.dto.ClickEventRequestDto;
import com.whatsong.global.kafka.GameEventProducer;
import com.whatsong.global.redis.RedisService;
import com.whatsong.global.exception.ErrorCode.AuthErrorCode;
import com.whatsong.global.exception.ErrorCode.MemberInfoErrorCode;
import com.whatsong.global.exception.exception.AuthException;
import com.whatsong.global.exception.exception.MemberInfoException;
import com.whatsong.global.jwt.JwtProvider;
import com.whatsong.global.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private static final Double EXP_INITIAL_NUMBER = 0.0;
	private static final Long LEVEL_INITIAL_NUMBER = 1L;
	private static final Long WINS_INITIAL_NUMBER = 0L;

	private final AuthRepository authRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final RedisService redisService;
	private final JwtProvider jwtProvider;
	private final JwtValidator jwtValidator;
	private final PasswordEncoder passwordEncoder;
	private final KakaoMemberProvider kakaoMemberProvider;
	private final GameEventProducer gameEventProducer;


	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


	/**
	 * 회원가입
	 *
	 * @param signupRequestDto
	 * @see SignupResponseDto
	 * @return SignupResponseDto
	 *
	 */
	@Override
	@Transactional
	public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {

		if (!validateDuplicatedLoginId(signupRequestDto.getLoginId()).isValid()) {
			throw new AuthException(AuthErrorCode.DUPLICATED_LONGIN_ID);
		}


		String regexId = "^[a-zA-Z]+$";
		String regexPw = "^[a-zA-Z0-9]+$";
		String regexName = "^[a-zA-Z0-9가-힣]+$";

		if(!Pattern.matches(regexId, signupRequestDto.getLoginId()) ||
			signupRequestDto.getLoginId().length() > 20 ||
			signupRequestDto.getLoginId().length() < 5) {
			throw new IllegalArgumentException();
		}

		if(!Pattern.matches(regexPw, signupRequestDto.getPassword()) ||
			signupRequestDto.getPassword().length() < 4) {
			throw new IllegalArgumentException();
		}

		if(!Pattern.matches(regexName, signupRequestDto.getNickname()) ||
			signupRequestDto.getNickname().length() < 2 ||
			signupRequestDto.getNickname().length() > 8) {
			throw new IllegalArgumentException();
		}

		UUID memberUUID = UUID.randomUUID();
		authRepository.save(Member.builder()
			.id(memberUUID)
			.loginId(signupRequestDto.getLoginId())
			.password(passwordEncoder.encode(signupRequestDto.getPassword()))
			.loginType(LoginType.LOCAL)
			.build());

		if (!validateDuplicatedNickname(signupRequestDto.getNickname()).isValid()) {
			throw new AuthException(AuthErrorCode.DUPLICATED_NICKNAME);
		}

		MemberInfo memberInfo = memberInfoRepository.save(MemberInfo.builder()
			.id(memberUUID)
			.nickname(signupRequestDto.getNickname())
			.exp(EXP_INITIAL_NUMBER)
			.level(LEVEL_INITIAL_NUMBER)
			.wins(WINS_INITIAL_NUMBER)
			.build());

		return SignupResponseDto.of(memberInfo.getNickname());
	}

	/**
	 * 게스트계정생성
	 *
	 * @see GuestSignupResponseDto
	 * @return GuestSignupResponseDto
	 *
	 */
	@Override
	@Transactional
	public GuestSignupResponseDto guestSignUp() {
		String loginId;

		do {
			int randomNum = (int) (Math.random() * 100000);
			loginId = String.format("guest%05d", randomNum);
		} while (!validateDuplicatedLoginId(loginId).isValid());


		UUID memberUUID = UUID.randomUUID();
		authRepository.save(Member.builder()
			.id(memberUUID)
			.loginId(loginId)
			.password(null)
			.loginType(LoginType.GUEST)
			.build());

		MemberInfo memberInfo = memberInfoRepository.save(MemberInfo.builder()
			.id(memberUUID)
			.nickname(loginId)
			.exp(EXP_INITIAL_NUMBER)
			.level(LEVEL_INITIAL_NUMBER)
			.wins(WINS_INITIAL_NUMBER)
			.build());
		return GuestSignupResponseDto.of(memberInfo.getNickname());
	}

	/**
	 * 일반로그인
	 *
	 * @param loginRequestDto
	 * @see LoginResponseDto
	 * @return LoginResponseDto
	 *
	 */
	@Override
	@Transactional
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Member member = authRepository.findByLoginId(loginRequestDto.getLoginId())
			.orElseThrow(() -> new AuthException(AuthErrorCode.LOGIN_FAILED));

		if(member.getLoginType() == LoginType.LOCAL){
			if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
				throw new AuthException(AuthErrorCode.LOGIN_FAILED);
			}
		}

		String memberNickname = memberInfoRepository.findNicknameById(member.getId())
			.orElseThrow(() -> new MemberInfoException(MemberInfoErrorCode.NOT_FOUND_MEMBER_INFO));

		String accessToken = jwtProvider.createAccessToken(member.getId());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());

		redisService.saveRefreshToken(member.getId(), refreshToken);

		ClickEventRequestDto clickDto = ClickEventRequestDto.builder()
			.userId(member.getId())
			.element("login")
			.timestamp(System.currentTimeMillis())
			.build();


		gameEventProducer.sendClickEvent(clickDto);


		return LoginResponseDto.builder()
			.nickname(memberNickname)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * 소셜로그인
	 *
	 * @param socialLoginRequestDto, deviceId
	 * @see SocialLoginResponseDto
	 * @return SocialLoginResponseDto
	 *
	 */
	public SocialLoginResponseDto socialLogin(SocialLoginRequestDto socialLoginRequestDto, String deviceId) {
		Member member;
		try {
			member = kakaoMemberProvider.findOrCreateMember(socialLoginRequestDto.getIdToken());
		}  catch (Exception e) {
			logger.warn("User sign-in failed.", e);
			throw new AuthException(AuthErrorCode.UNKNOWN);
		}

		String memberNickname = memberInfoRepository.findNicknameById(member.getId())
			.orElseThrow(() -> new MemberInfoException(MemberInfoErrorCode.NOT_FOUND_MEMBER_INFO));


		String accessToken = jwtProvider.createAccessToken(member.getId());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());

		redisService.saveRefreshToken(member.getId(), refreshToken);

		return SocialLoginResponseDto.builder()
			.nickname(memberNickname)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}



	@Override
	public LogoutResponseDto logout(String token) {

		UUID memberId = jwtValidator.getData(token);

		try {
			redisService.deleteKeyInRedis(memberId.toString());
			return LogoutResponseDto.builder().logoutResult("SUCCESS").build();
		} catch (Exception e) {
			throw new AuthException(AuthErrorCode.REDIS_DELETE_FAIL);
		}
	}


	/**
	 * 로그인 아이디 중복 검사
	 *
	 * @param loginId
	 * @see ValidateDuplicatedLoginIdResponseDto
	 * @return ValidateDuplicatedLoginIdResponseDto
	 */
	@Override
	@Transactional(readOnly = true)
	public ValidateDuplicatedLoginIdResponseDto validateDuplicatedLoginId(String loginId) {

		return new ValidateDuplicatedLoginIdResponseDto(authRepository.findByLoginIdNotExists(loginId));
	}

	/**
	 * 닉네임 중복 검사
	 *
	 * @param nickname
	 * @see ValidateDuplicatedNicknameResponseDto
	 * @return ValidateDuplicatedNicknameResponseDto
	 */
	@Override
	@Transactional(readOnly = true)
	public ValidateDuplicatedNicknameResponseDto validateDuplicatedNickname(String nickname) {

		return new ValidateDuplicatedNicknameResponseDto(memberInfoRepository.findByNicknameNotExists(nickname));
	}

	/**
	 * 토큰 재발급
	 *
	 * @param reissueTokenRequestDto
	 * @see ReissueTokenResponseDto
	 * @return ReissueTokenResponseDto
	 */
	@Override
	public ReissueTokenResponseDto reissueToken(ReissueTokenRequestDto reissueTokenRequestDto) {

		// Refresh Token으로 유효성 검사 및 member Id 반환
		String memberId = jwtValidator.validateRefreshToken(reissueTokenRequestDto.getRefreshToken());

		// 반환된 memberId로 Access, Refresh Token 생성
		String accessToken = jwtProvider.createAccessToken(memberId);
		String refreshToken = jwtProvider.createRefreshToken(memberId);

		// 재생성된 Refresh Token을 Redis에 저장
		redisService.saveRefreshToken(UUID.fromString(memberId), refreshToken);

		return ReissueTokenResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

}
