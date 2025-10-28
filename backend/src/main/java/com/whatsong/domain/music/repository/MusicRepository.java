package com.whatsong.domain.music.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whatsong.domain.music.model.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {

	@Query("select m from Music m where m.setId.id = :setId")
	List<Music> findAllBySetId(Integer setId);
}
