package com.whatsong.global.config;

import java.util.Collections;
import java.util.List;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.whatsong.global.annotation.DisableSwaggerAuthButton;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@Profile("!production")
public class SwaggerConfig {

	// @Value("${swagger.local-server-url}")
	// private String localServerUrl;
	//
	// @Value("${swagger.dev-server-url}")
	// private String devServerUrl;

	@Bean
	public OpenAPI openApi() {
		String jwtScheme = "whatsong Access Token";

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(jwtScheme);

		SecurityScheme securityScheme = new SecurityScheme()
			.name(jwtScheme)
			.type(SecurityScheme.Type.HTTP)
			.in(SecurityScheme.In.HEADER)
			.scheme("Bearer")
			.bearerFormat("JWT");

		Components components = new Components()
			.addSecuritySchemes(jwtScheme, securityScheme);

		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components)
			// .servers(List.of(
			// 	createServer(devServerUrl, "for dev"),
			// 	createServer(localServerUrl, "for local")
			// ))
			;
	}

	private Server createServer(String url, String description) {
		Server server = new Server();
		server.setUrl(url);
		server.setDescription(description);
		return server;
	}

	@Bean
	public OperationCustomizer operationCustomizer() {
		return (operation, handlerMethod) -> {
			if (handlerMethod.hasMethodAnnotation(DisableSwaggerAuthButton.class)) {
				operation.setSecurity(Collections.emptyList());
			}
			if (operation.getDescription() != null) {
				operation.setDescription(operation.getDescription().strip());
			}
			return operation;
		};
	}
}