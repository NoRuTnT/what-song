package com.whatsong.domain.auth.service.provider;

import com.whatsong.domain.auth.data.LoginType;
import com.whatsong.domain.member.model.Member;

public interface SocialMemberProvider {

	LoginType getPlatform();

	/**
	 * 각 플랫폼의 AccessToken 또는 ID Token을 사용하여 가입된 유저를 찾거나 새로 생성합니다.
	 */
	Member findOrCreateMember(String socialIdToken);

	/**
	 * 각 플랫폼의 unlink (회원 연결 해제)
	 */
	void unlinkMember(String memberId);
}
