package com.whatsong.domain.websocket.data;

public enum MessageDtoType {
	MUSICPROBLEM, HINT1, HINT2, TIME, GAMERESULT, CHAT, BEFORESKIP, AFTERSKIP, BEFOREANSWERCORRECT, MUSICPLAY, MUSICEND, GOWAITING, GAMESTART, ENTERUSER, EXITUSER, MODIFYINFO
	/** MUSICPROBLEM : 문제 출제
	 * HINT1 : 힌트1
	 * HINT2 : 힌트2
	 * TIME : time --
	 * GAMERESULT : 전체 게임 종료시 전달하는 메시지
	 * CHAT : 모든 채팅
	 * BEFORESKIP : 플레이 타입이 BeforeAnswer 일 때 skip
	 * AFTERSKIP :플레이 타입이 Answer 일 때 skip
	 * BEFOREANSWERCORRECT : 플레이 타입이 BeforeAneswer 일 때 정답 맞은 경우 pub 하는 메시지
	 * MUSICPLAY : RoundStart에서 time == 0이 됐을 때 BeforeAnswer 상태로 바뀌면서 노래시작을 알리는 메시지
	 * MUSICEND : 음악 종료 안내
	 * GOWAITING : 모든 게임 종료 시 게임 타입을 Waiting 로 바꼈다고 알리는 메시지
	 * GAMESTART : 게임 시작 안내
	 * ENTERUSER : 게임방 입장
	 * EXITUSER : 게임방 퇴장
	 * MODIFYINFO : 게임방 정보 변경
	 * */
}