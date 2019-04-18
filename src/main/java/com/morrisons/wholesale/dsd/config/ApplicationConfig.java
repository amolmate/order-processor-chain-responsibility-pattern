package com.morrisons.wholesale.dsd.config;

import javax.ws.rs.client.Client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.provider.JerseyClientProvider;
import com.morrisons.wholesale.dsd.provider.JerseyPatchClientProvider;
import com.morrisons.wholesale.dsd.provider.PatchClient;

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
@ConfigurationProperties(prefix = "pers")
public class ApplicationConfig {

	private String version;

	private ProxyConfig proxyConfig;

	private Customer customer;

	private S3Configuration s3;

	private DynamoDbConfiguration dynamoDbConfiguration;

	private DatabaseConfig databaseConfig;

	private ExternalServiceConfig dSDOrdersConfig;
	
	private ExternalServiceConfig updateItemConfig;
	
	@Bean("updateItemConfig")
	public ExternalServiceConfig getUpdateItemConfig() {
		
		return updateItemConfig;
	}
	
	@Bean("dSDOrdersConfig")
	public ExternalServiceConfig getDSDOrdersConfig() {
		
		return dSDOrdersConfig;
	}

	@Bean
	public Client getJersyClient() {

		return new JerseyClientProvider(getProxyConfig()).get();
	}

	@Bean
	public PatchClient getJersyPathClient() {

		return new JerseyPatchClientProvider(getProxyConfig()).get();
	}
}