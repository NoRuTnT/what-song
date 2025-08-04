package com.whatsong.global.openfeign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsong.global.exception.ErrorCode.KakaoServerErrorCode;
import com.whatsong.global.exception.exception.KakaoServerException;

import feign.Response;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import lombok.Getter;
import lombok.Setter;

@Configuration
public class KakaoKauthConfig {

	private static final Logger logger = LoggerFactory.getLogger(KakaoKauthConfig.class);

	@Bean
	public Encoder formEncoder(ObjectFactory<HttpMessageConverters> converters) {
		return new SpringFormEncoder(new SpringEncoder(converters));
	}

	@Bean
	public ErrorDecoder kauthErrorDecoder() {
		return new KauthErrorDecoder();
	}

	public static class KauthErrorDecoder implements ErrorDecoder {

		private final ObjectMapper objectMapper = new ObjectMapper();

		@Override
		public Exception decode(String methodKey, Response response) {
			if (response.status() < 400) {
				return new Default().decode(methodKey, response);
			}

			KauthErrorResponse errorResponse = KakaoKapiConfig.toObject(response, KauthErrorResponse.class);
			logger.error("kauth exception. response: {}", errorResponse);

			throw new KakaoServerException(KakaoServerErrorCode.fromKakaoErrorCode(errorResponse.getError()));
		}
	}

	@Getter
	@Setter
	public static class KauthErrorResponse {
		private String error;
		private String errorDescription;
	}
}
