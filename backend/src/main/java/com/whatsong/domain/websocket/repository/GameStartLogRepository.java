package com.whatsong.domain.websocket.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.whatsong.domain.websocket.model.log.GameStartLog;

@Repository
public interface GameStartLogRepository extends JpaRepository<GameStartLog, Integer> {

	@Query("select MAX(m.startedAt) "
		+ "from GameStartLog m "
		+ "where m.multiModeCreateGameRoomLogId = :multiModeCreateGameRoomLogId")
	Optional<LocalDateTime> findLatestStartedAtByMultiModeCreateGameRoomLogId(
		@Param("multiModeCreateGameRoomLogId") int multiModeCreateGameRoomLogId);
}
