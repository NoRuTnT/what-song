package com.whatsong.global.common.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
	@Builder.Default
	private Integer code = 200;

	@Builder.Default
	private String message = "SUCCESS";

	private T data;

	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(HttpStatus.OK.value(), "success", data);
	}

	public static <T> BaseResponse<T> success(T data, String message) {
		return new BaseResponse<>(HttpStatus.OK.value(), message, data);
	}

	public static <T> BaseResponse<T> error(int code, String message) {
		return new BaseResponse<>(code, message, null);
	}

	public BaseResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
