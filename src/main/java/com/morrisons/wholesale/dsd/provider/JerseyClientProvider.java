package com.morrisons.wholesale.dsd.provider;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.morrisons.wholesale.dsd.config.ProxyConfig;

/**
 * Provider class for javax.ws.rs.client.Client
 * 
 * @author amol13704
 *
 */
public class JerseyClientProvider extends BaseJerseyClientProvider {

	
	public JerseyClientProvider(ProxyConfig proxyConfig) {

		super(proxyConfig);
	}

	
	public final Client get() {

		ClientConfig clientConfig = getClientConfig();

		return JerseyClientBuilder.newClient(clientConfig);
	}
}