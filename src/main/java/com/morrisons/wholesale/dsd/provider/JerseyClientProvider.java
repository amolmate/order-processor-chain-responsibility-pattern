package com.morrisons.wholesale.dsd.provider;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.ProxyConfig;

/**
 * Provider class for javax.ws.rs.client.Client
 * 
 * @author amol13704
 *
 */

@Component
public class JerseyClientProvider extends BaseJerseyClientProvider {

	
	public JerseyClientProvider(ProxyConfig proxyConfig) {

		super(proxyConfig);
	}

	@Bean
	public final Client get() {

		ClientConfig clientConfig = getClientConfig();

		return JerseyClientBuilder.newClient(clientConfig);
	}
}