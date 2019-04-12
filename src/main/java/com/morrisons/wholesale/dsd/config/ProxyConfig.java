package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
public class ProxyConfig {

	private boolean useProxy;

	private String proxyHost;

	private Integer proxyPort;

	private String proxyUserName;

	private String proxyPassword;
}