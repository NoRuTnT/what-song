package com.whatsong.domain.websocket.model.log;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ModifyGameRoomInformationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column
	private int createGameRoomLogId;

	@NotNull
	@Column
	private String previousTitle;

	@NotNull
	@Column
	private int previousMusicSetId;

	@NotNull
	@Column
	private int previousQuizAmount;

	@NotNull
	@Column
	private int previousMaxUserNumber;

	@NotNull
	@Column
	private String modifiedTitle;

	@NotNull
	@Column
	private int modifiedMusicSetId;

	@NotNull
	@Column
	private int modifiedQuizAMount;

	@NotNull
	@Column
	private int modifiedMaxUserNumber;
}