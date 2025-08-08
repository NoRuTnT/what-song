package com.whatsong.domain.ranking.dto.responseDto;

import java.util.List;

import com.whatsong.domain.ranking.dto.FullRankItem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FullRankResponseDto {
	private List<FullRankItem> rankList;
	private String myRank;

	@Builder
	public FullRankResponseDto(List<FullRankItem> rankList, String myRank) {

		this.rankList = rankList;
		this.myRank = myRank;
	}
}
