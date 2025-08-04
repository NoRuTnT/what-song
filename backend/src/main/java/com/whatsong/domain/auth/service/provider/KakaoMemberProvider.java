package com.whatsong.domain.auth.service.provider;

import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.whatsong.domain.auth.data.LoginType;
import com.whatsong.domain.auth.repository.AuthRepository;
import com.whatsong.domain.auth.service.OIDCHelper;
import com.whatsong.domain.member.model.Member;
import com.whatsong.domain.member.model.MemberInfo;
import com.whatsong.domain.member.repository.MemberInfoRepository;
import com.whatsong.global.client.KakaoKapiClient;
import com.whatsong.global.client.KakaoOIDCClient;
import com.whatsong.global.client.dto.KakaoIdTokenPayload;
import com.whatsong.global.client.dto.KakaoUnlinkMemberRequest;
import com.whatsong.global.client.dto.KakaoUnlinkMemberResponse;
import com.whatsong.global.exception.ErrorCode.AuthErrorCode;
import com.whatsong.global.exception.exception.AuthException;
import com.whatsong.global.properties.OauthProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoMemberProvider implements SocialMemberProvider {
	private static final Double EXP_INITIAL_NUMBER = 0.0;

	private final KakaoOIDCClient kakaoOIDCClient;
	private final AuthRepository authRepository;
	private final OIDCHelper oidcHelper;
	private final KakaoKapiClient kakaoKapiClient;
	private final OauthProperties oauthProperties;
	private final MemberInfoRepository memberInfoRepository;

	@Override
	public LoginType getPlatform() {
		return LoginType.KAKAO;
	}

	@Override
	public Member findOrCreateMember(String socialIdToken) {
		var kakaoPublicKey = kakaoOIDCClient.getOIDCPublicKey();
		UUID memberUUID = UUID.randomUUID();


		KakaoIdTokenPayload payload = oidcHelper.parseKakaoIdToken(
			socialIdToken,
			kakaoPublicKey.getKeys()
		);

		Optional<Member> optionalMember = authRepository.findByLoginId(payload.getPlatformMemberId());
		if (optionalMember.isPresent()) {
			return optionalMember.get();
		}
		Member member = authRepository.save(Member.builder()
			.id(memberUUID)
			.loginId(payload.getPlatformMemberId())
			.password(null)
			.loginType(LoginType.KAKAO)
			.build());

		memberInfoRepository.save(MemberInfo.builder()
			.id(memberUUID)
			.nickname(payload.getNickname())
			.exp(EXP_INITIAL_NUMBER)
			.build());

		return member;
	}

	@Override
	public void unlinkMember(String memberId) {
		Member member = authRepository.findByLoginId(memberId)
			.orElseThrow(() -> new AuthException(AuthErrorCode.NOT_FOUND_MEMBER));

		try {
			KakaoUnlinkMemberResponse response = kakaoKapiClient.unlinkMemberByAdminKey(
				oauthProperties.getKakao().getAdminKeyWithPrefix(),
				new KakaoUnlinkMemberRequest(Long.parseLong(member.getLoginId()),"user_id")
			);
			log.debug("Kakao member unlinked. memberId: {}, platformId: {}", memberId, response.getId());
		} catch (Exception e) {
			log.debug("Kakao member unlink failed. memberId: {}", memberId, e);
		}
	}

}
