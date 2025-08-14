package com.whatsong.domain.websocket.model.log;

import java.time.LocalDateTime;
import java.util.List;

import com.whatsong.domain.websocket.dto.gameMessageDto.GameResultItem;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameOverLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column
	private int multiModeCreateGameRoomLogId;

	@NotNull
	@Column
	private String title;

	@NotNull
	@Column
	private String years;

	@ElementCollection
	@CollectionTable(name = "game_result_member", joinColumns = @JoinColumn(name = "game_result_id"))
	private List<GameResultItem> members;

	@NotNull
	@Column
	private LocalDateTime endedAt;

	@NotNull
	@Column
	private int playTime;
}