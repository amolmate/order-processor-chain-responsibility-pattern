package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Configuration
public class RedisConfig extends ExternalServiceConfig {
	
	private String uri;

	private String apiKey;

	private String authorization;

	private String endpoint;

	private int connectionMinimumIdleSize;

	private int connectionPoolSize;

	private int idleConnectionTimeout;

	private int connectTimeout;

	private String evn;
}