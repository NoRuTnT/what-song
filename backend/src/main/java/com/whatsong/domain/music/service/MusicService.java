package com.whatsong.domain.music.service;

import com.whatsong.domain.music.dto.reponseDto.CreateMusicSetResponseDto;
import com.whatsong.domain.music.dto.requestDto.CreateMusicSetRequestDto;

public interface MusicService {

	CreateMusicSetResponseDto createSet(String token, CreateMusicSetRequestDto request);
}
