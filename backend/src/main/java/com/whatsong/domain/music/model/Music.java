package com.whatsong.domain.music.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Size(max = 200)
	@NotNull
	@Column
	private String title;

	@Size(max = 20)
	@NotNull
	@Column
	private String year;

	@Size(max = 20)
	@NotNull
	@Column
	private String singer;

	@Size(max = 255)
	@NotNull
	@Column
	private String url;

	@Size(max = 200)
	@NotNull
	@Column
	private String hint;
}