package com.morrisons.wholesale.dsd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "proxyConfig")
public class ProxyConfig {

	private boolean useProxy;

	private String proxyHost;

	private Integer proxyPort;

	private String proxyUserName;

	private String proxyPassword;
}