package com.whatsong.global.client.dto;

import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class OIDCPublicKeysResponse {
	private List<JsonWebKey> keys = Collections.emptyList();

}
