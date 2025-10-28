package com.whatsong.domain.music.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whatsong.domain.music.dto.reponseDto.CreateMusicSetResponseDto;
import com.whatsong.domain.music.dto.requestDto.CreateMusicSetRequestDto;
import com.whatsong.domain.music.model.Music;
import com.whatsong.domain.music.model.MusicSet;
import com.whatsong.domain.music.repository.MusicRepository;
import com.whatsong.domain.music.repository.MusicSetRepository;
import com.whatsong.global.exception.ErrorCode.InGameErrorCode;
import com.whatsong.global.exception.exception.InGameException;
import com.whatsong.global.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

	private final MusicSetRepository musicSetRepository;
	private final MusicRepository musicRepository;
	private final JwtValidator jwtValidator;

	@Transactional
	public CreateMusicSetResponseDto createSet(String token, CreateMusicSetRequestDto request) {
		UUID memberId = jwtValidator.getData(token);

		MusicSet set = MusicSet.builder()
			.ownerId(memberId)
			.title(request.getTitle())
			.quizAmount(request.getQuizAmount())
			.description(request.getDescription())
			.isPublic(true)
			.build();


		List<Music> musics = request.getMusics().stream()
			.map(m -> Music.builder()
				.setId(set)
				.url(m.getUrl())
				.hint1(m.getHint1())
				.hint2(m.getHint2())
				.answer(m.getAnswer())
				.answers(normalizeAndDistinctWithDisplay(
					m.getAnswers(),
					m.getAnswer()
				))
				.lengthSec(m.getLengthSec())
				.startSec(m.getStartSec())
				.build())
			.toList();
		int count = musics.size();

		if (count != request.getQuizAmount()) {
			throw new InGameException(InGameErrorCode.UNKNOWN);
		}

		set.getMusics().addAll(musics);

		musicSetRepository.save(set);

		CreateMusicSetResponseDto dto = CreateMusicSetResponseDto.builder()
			.musicSetId(set.getId())
			.quizAmount(set.getQuizAmount())
			.build();

		return dto;
	}

	private static List<String> normalizeAndDistinctWithDisplay(List<String> raw, String displayAnswer) {
		LinkedHashSet<String> set = new LinkedHashSet<>();
		if (raw != null) {
			for (String s : raw) {
				String n = normalize(s);
				if (n != null && !n.isBlank()) set.add(n);
			}
		}
		String dn = normalize(displayAnswer);
		if (dn != null && !dn.isBlank()) set.add(dn);
		return new ArrayList<>(set);
	}

	private static String normalize(String s) {
		if (s == null) return null;
		String lower = s.toLowerCase(Locale.ROOT);
		return lower.replaceAll("[\\p{Punct}\\s]+", "");
	}


}
