package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
public class ExternalServiceConfig {

	public static final String DSD_ORDERS_CONFIG = "getDSDOrdersConfig";

	private String uri;

	private String apiKey;

	private String authorization;
}