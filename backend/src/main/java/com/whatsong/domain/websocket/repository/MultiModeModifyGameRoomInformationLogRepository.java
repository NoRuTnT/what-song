package com.whatsong.domain.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsong.domain.websocket.model.log.MultiModeModifyGameRoomInformationLog;

@Repository
public interface MultiModeModifyGameRoomInformationLogRepository
	extends JpaRepository<MultiModeModifyGameRoomInformationLog, Integer> {

}
