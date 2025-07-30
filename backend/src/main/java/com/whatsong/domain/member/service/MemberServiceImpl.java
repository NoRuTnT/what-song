package com.whatsong.domain.member.service;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whatsong.domain.member.data.LoginType;
import com.whatsong.domain.member.dto.requestDto.JoinRequestDto;
import com.whatsong.domain.member.dto.requestDto.LoginRequestDto;
import com.whatsong.domain.member.dto.requestDto.ReissueTokenRequestDto;
import com.whatsong.domain.member.dto.responseDto.JoinResponseDto;
import com.whatsong.domain.member.dto.responseDto.LoginResponseDto;
import com.whatsong.domain.member.dto.responseDto.LogoutResponseDto;
import com.whatsong.domain.member.dto.responseDto.ReissueTokenResponseDto;
import com.whatsong.domain.member.dto.responseDto.ValidateDuplicatedLoginIdResponseDto;
import com.whatsong.domain.member.dto.responseDto.ValidateDuplicatedNicknameResponseDto;
import com.whatsong.domain.member.entity.Member;
import com.whatsong.domain.member.entity.MemberInfo;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.domain.member.repository.MemberRepository;
import com.whatsong.global.RedisService;
import com.whatsong.global.exception.ErrorCode.MemberErrorCode;
import com.whatsong.global.exception.ErrorCode.MemberInfoErrorCode;
import com.whatsong.global.exception.exception.MemberException;
import com.whatsong.global.exception.exception.MemberInfoException;
import com.whatsong.global.jwt.JwtProvider;
import com.whatsong.global.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private static final Double EXP_INITIAL_NUMBER = 0.0;

	private final MemberRepository memberRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final RedisService redisService;
	private final JwtProvider jwtProvider;
	private final JwtValidator jwtValidator;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입
	 *
	 * @param joinRequestDto
	 * @see JoinResponseDto
	 * @return JoinResponseDto
	 *
	 */
	@Override
	@Transactional
	public JoinResponseDto signUp(JoinRequestDto joinRequestDto) {

		if (!validateDuplicatedLoginId(joinRequestDto.getLoginId()).isValid()) {
			throw new MemberException(MemberErrorCode.DUPLICATED_LONGIN_ID);
		}


		String regexId = "^[a-zA-Z]+$";
		String regexPw = "^[a-zA-Z0-9]+$";
		String regexName = "^[a-zA-Z0-9가-힣]+$";

		if(!Pattern.matches(regexId,joinRequestDto.getLoginId()) ||
			joinRequestDto.getLoginId().length() > 20 ||
			joinRequestDto.getLoginId().length() < 5) {
			throw new IllegalArgumentException();
		}

		if(!Pattern.matches(regexPw,joinRequestDto.getPassword()) ||
			joinRequestDto.getPassword().length() < 4) {
			throw new IllegalArgumentException();
		}

		if(!Pattern.matches(regexName,joinRequestDto.getNickname()) ||
			joinRequestDto.getNickname().length() < 2 ||
			joinRequestDto.getNickname().length() > 8) {
			throw new IllegalArgumentException();
		}

		UUID memberUUID = UUID.randomUUID();
		memberRepository.save(Member.builder()
			.id(memberUUID)
			.loginId(joinRequestDto.getLoginId())
			.password(passwordEncoder.encode(joinRequestDto.getPassword()))
			.loginType(LoginType.SIMPLE)
			.build());

		if (!validateDuplicatedNickname(joinRequestDto.getNickname()).isValid()) {
			throw new MemberException(MemberErrorCode.DUPLICATED_NICKNAME);
		}

		MemberInfo memberInfo = memberInfoRepository.save(MemberInfo.builder()
			.id(memberUUID)
			.nickname(joinRequestDto.getNickname())
			.exp(EXP_INITIAL_NUMBER)
			.build());

		return JoinResponseDto.of(memberInfo.getNickname());
	}

	@Override
	@Transactional
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Member member = memberRepository.findByLoginId(loginRequestDto.getLoginId())
			.orElseThrow(() -> new MemberException(MemberErrorCode.LOGIN_FAILED));

		if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.LOGIN_FAILED);
		}

		String memberNickname = memberInfoRepository.findNicknameById(member.getId())
			.orElseThrow(() -> new MemberInfoException(MemberInfoErrorCode.NOT_FOUND_MEMBER_INFO));

		String accessToken = jwtProvider.createAccessToken(member.getId());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());

		redisService.saveRefreshToken(member.getId(), refreshToken);

		return LoginResponseDto.builder()
			.nickname(memberNickname)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Override
	public LogoutResponseDto logout(String token) {

		UUID memberId = jwtValidator.getData(token);

		try {
			// 레디스에서 refreshToken 저장
			redisService.deleteKeyInRedis(memberId.toString());
			return LogoutResponseDto.builder().logoutResult("SUCCESS").build();
		} catch (Exception e) {
			throw new MemberException(MemberErrorCode.REDIS_DELETE_FAIL);
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

		return new ValidateDuplicatedLoginIdResponseDto(memberRepository.findByLoginIdNotExists(loginId));
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
