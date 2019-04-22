package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class Redisconfig {

	private String endpoint;

	private int connectionMinimumIdleSize;

	private int connectionPoolSize;

	private int idleConnectionTimeout;

	private int connectTimeout;

	private String evn;
}