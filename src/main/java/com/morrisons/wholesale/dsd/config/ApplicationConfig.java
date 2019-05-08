package com.morrisons.wholesale.dsd.config;

import javax.ws.rs.client.Client;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.provider.JerseyClientProvider;
import com.morrisons.wholesale.dsd.provider.JerseyPatchClientProvider;
import com.morrisons.wholesale.dsd.provider.PatchClient;

import lombok.Data;

/**
 * @author amol13704
 *
 */

@Data
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
	
	private ExternalServiceConfig pollingConfig;
	
	private ExternalServiceConfig storeServiceConfig;
	
	private ExternalServiceConfig customerServiceConfig;
	
	private RedisConfig redisConfig;
	
	@Bean("customerServiceConfig")
	public ExternalServiceConfig getCustomerServiceConfig() {

		return customerServiceConfig;
	}

	@Bean("updateItemConfig")
	public ExternalServiceConfig getUpdateItemConfig() {

		return updateItemConfig;
	}

	@Bean("dSDOrdersConfig")
	public ExternalServiceConfig getDSDOrdersConfig() {

		return dSDOrdersConfig;
	}
	
	@Bean("pollingConfig")
	public ExternalServiceConfig getPollingConfig() {

		return pollingConfig;
	}
	
	@Bean("storeServiceConfig")
	public ExternalServiceConfig getStoreServiceConfig() {

		return storeServiceConfig;
	}
	
	@Bean("redisConfig")
	public RedisConfig getRedisConfig() {

		return redisConfig;
	}
	
	@Bean
	public Client getJersyClient() {

		return new JerseyClientProvider(getProxyConfig()).get();
	}

	@Bean
	public PatchClient getJersyPathClient() {

		return new JerseyPatchClientProvider(getProxyConfig()).get();
	}
	
	@Bean("restTemplate")
	public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
		
		return builder.build();
	}

	@Bean("redissonClient")
	public RedissonClient redissonClient() {

		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress(redisConfig.getEndpoint());
		singleServerConfig.setConnectionMinimumIdleSize(redisConfig.getConnectionMinimumIdleSize());
		singleServerConfig.setConnectionPoolSize(redisConfig.getConnectionPoolSize());
		singleServerConfig.setIdleConnectionTimeout(redisConfig.getIdleConnectionTimeout());
		singleServerConfig.setConnectTimeout(redisConfig.getConnectTimeout());
		return Redisson.create(config);
	}
}