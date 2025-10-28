package com.whatsong.domain.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsong.domain.music.model.MusicSets;

@Repository
public interface MusicSetsRepository extends JpaRepository<MusicSets, Integer> {


}
