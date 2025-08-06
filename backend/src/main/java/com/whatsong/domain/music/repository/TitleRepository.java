package com.whatsong.domain.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.whatsong.domain.music.model.Title;

public interface TitleRepository extends JpaRepository<Title, Integer> {

	List<Title> findAllByMusicId(@Param("musicId") Integer musicId);
}
