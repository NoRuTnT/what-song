package com.whatsong.domain.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "music_sets",
	indexes = {
		@Index(name = "idx_music_sets_owner", columnList = "owner_id"),
		@Index(name = "idx_music_sets_is_public", columnList = "is_public")
	})
public class MusicSets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Column(name = "owner_id", columnDefinition = "BINARY(16)")
	private UUID ownerId;

	@NotNull
	@Column(length = 200)
	private String title;


	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "is_public")
	private Boolean isPublic;

	@Column(name = "quiz_amount")
	private Integer quizAmount;

	@OneToMany(mappedBy = "setId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Music> musics = new ArrayList<>();

}
