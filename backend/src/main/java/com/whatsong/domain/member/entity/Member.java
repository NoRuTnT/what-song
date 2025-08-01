package com.whatsong.domain.member.entity;

import java.util.UUID;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import com.whatsong.domain.BaseTimeEntity;
import com.whatsong.domain.member.data.LoginType;
import com.whatsong.domain.member.data.LoginTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
@SQLDelete(sql = "UPDATE frame SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends BaseTimeEntity {

	@Id
	private UUID id;

	@Size(max = 30)
	@NotNull
	@Column
	private String loginId;

	@Size(max = 200)
	@NotNull
	@Column
	private String password;

	@NotNull
	@Column
	@Convert(converter = LoginTypeConverter.class)
	private LoginType loginType;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	@Column
	public Boolean deleted= Boolean.FALSE;


}
