package com.morrisons.wholesale.dsd.provider;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
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
public class JerseyPatchClientProvider extends BaseJerseyClientProvider {

	public JerseyPatchClientProvider(ProxyConfig proxyConfig) {

		super(proxyConfig);
	}

	@Bean
	public final PatchClient get() {

		ClientConfig clientConfig = getClientConfig();

		Client client = JerseyClientBuilder.newClient(clientConfig);

		client = client.property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);

		return new PatchClient(client);
	}
}