package com.whatsong.domain.websocket.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

	//곡명
	private String title;
	//힌트
	private String initialHint;
	//가수
	private String singer;
	//링크
	private String url;
	//정답 List
	private List<String> answerList;

	public static Problem create(String title, String initialHint, String singer, String url,
		List<String> answerList) {

		return new Problem(title, initialHint, singer, url, answerList);
	}


}