package com.whatsong.domain.music.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "set_id", nullable = false)
	private MusicSets setId;

	@Size(max = 255)
	@NotNull
	@Column
	private String url;

	@NotNull
	@Column
	private int lengthSec;

	@NotNull
	@Column
	private int startSec=0;

	@Size(max = 200)
	@NotNull
	@Column
	private String hint1;

	@Size(max = 200)
	@NotNull
	@Column
	private String hint2;

	@Size(max = 200)
	@NotNull
	@Column
	private String answer;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private List<String> answers = new ArrayList<>();

}