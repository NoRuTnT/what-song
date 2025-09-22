package com.whatsong.domain.member.model;

import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.whatsong.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class MemberInfo extends BaseTimeEntity {

	@Id
	private UUID id;

	@Size(max = 30)
	@Column
	private String nickname;

	@NotNull
	@Column
	private Double exp;

	@NotNull
	@Column
	private Long level;

	@NotNull
	@Column
	private Long wins;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	@Column
	private Boolean deleted = Boolean.FALSE;

	public void gainExp(double exp) {
		this.exp += exp;
		levelUp();
	}

	private void levelUp() {
		while (this.exp >= 50) {
			this.level += 1;
			this.exp -= 50;
		}
	}
}
