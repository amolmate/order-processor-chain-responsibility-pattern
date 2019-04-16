package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@Component("getDSDOrdersConfig")
public class ExternalServiceConfig {

	private String uri;

	private String apiKey;

	private String authorization;
}