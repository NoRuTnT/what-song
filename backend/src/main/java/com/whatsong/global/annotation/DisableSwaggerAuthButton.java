package com.whatsong.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Swagger-Ui API의 인증 버튼을 비활성화하는 어노테이션
 */
@Target(ElementType.METHOD) // 함수(메서드)에 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임에도 유지되어 리플렉션 가능
public @interface DisableSwaggerAuthButton {
}