package com.whatsong.global.openfeign;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.Encoder;
import lombok.Getter;
import lombok.Setter;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsong.global.exception.ErrorCode.KakaoServerErrorCode;
import com.whatsong.global.exception.exception.KakaoServerException;


@Configuration
public class KakaoKapiConfig {

	private static final Logger logger = LoggerFactory.getLogger(KakaoKapiConfig.class);


	@Bean
	public ErrorDecoder kapiErrorDecoder() {
		return new KapiErrorDecoder();
	}

	public static class KapiErrorDecoder implements ErrorDecoder {

		private final ObjectMapper objectMapper = new ObjectMapper();

		@Override
		public Exception decode(String methodKey, Response response) {
			if (response.status() < 400) {
				return new Default().decode(methodKey, response);
			}

			KapiErrorResponse errorResponse = toObject(response, KapiErrorResponse.class);
			logger.error("kapi exception. response: {}", errorResponse);

			HttpStatus status = HttpStatus.valueOf(response.status());

			switch (status) {
				case BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, SERVICE_UNAVAILABLE:
					throw new KakaoServerException(KakaoServerErrorCode.fromKakaoErrorCode(errorResponse.code));
				default:
					throw new KakaoServerException(KakaoServerErrorCode.UNKNOWN);
			}
		}
	}

	@Getter
	@Setter
	public static class KapiErrorResponse {
		private int code;
		private String msg;
	}

	public static <T> T toObject(Response response, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(response.body().asInputStream(), clazz);
		} catch (IOException e) {
			logger.warn("Kakao API 에러 메시지 파싱에 실패했습니다. 대상 class: {}", clazz.getSimpleName());
			throw new KakaoServerException(KakaoServerErrorCode.UNKNOWN);
		}
	}
}
