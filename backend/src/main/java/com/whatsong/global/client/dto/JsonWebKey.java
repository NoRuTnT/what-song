package com.whatsong.global.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JsonWebKey {

	@JsonProperty("kid")
	private String kid;

	@JsonProperty("kty")
	private String kty;

	@JsonProperty("alg")
	private String alg;

	@JsonProperty("use")
	private String use;

	@JsonProperty("n")
	private String n;

	@JsonProperty("e")
	private String e;
}
