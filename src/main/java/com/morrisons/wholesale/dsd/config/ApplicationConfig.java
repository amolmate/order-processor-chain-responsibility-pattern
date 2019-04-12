package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author amol13704
 *
 */

@Getter
@Setter
@ToString
@Component
@Configuration
public class ApplicationConfig {

	private String version;

	private ProxyConfig proxyConfig;

	private Customer customer;

	private S3Configuration s3;

	private DynamoDbConfiguration dynamoDbConfiguration;

	private DatabaseConfig databaseConfig;

	private ExternalServiceConfig getDSDOrdersConfig;

	// @Bean("client")
	// public getJersyClient

	// bind(Client.class).toProvider(JerseyClientProvider.class).in(Singleton.class);

	// bind(PatchClient.class).toProvider(JerseyPatchClientProvider.class).in(Singleton.class);

	// bind(IWMMExceptionFactory.class).to(WMMExceptionFactory.class);
}