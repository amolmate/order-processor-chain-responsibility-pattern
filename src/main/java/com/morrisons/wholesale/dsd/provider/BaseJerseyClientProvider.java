package com.morrisons.wholesale.dsd.provider;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.morrisons.wholesale.dsd.config.ProxyConfig;

/**
 * Abstract Provider class for javax.ws.rs.client.Client
 * 
 * @author amol13704
 *
 */


public abstract class BaseJerseyClientProvider {

	public static final String HTTP_SCHEME = "http";

	protected final ProxyConfig proxyConfig;

	public BaseJerseyClientProvider(ProxyConfig proxyConfig) {

		this.proxyConfig = proxyConfig;
	}

	public ClientConfig getClientConfig() {

		ClientConfig clientConfig = new ClientConfig();

		if (proxyConfig.isUseProxy()) {

			String proxyURIFinal = HTTP_SCHEME + "://" + proxyConfig.getProxyHost() + ":" + proxyConfig.getProxyPort();

			clientConfig.property(ClientProperties.PROXY_URI, proxyURIFinal);
			clientConfig.property(ClientProperties.PROXY_USERNAME, proxyConfig.getProxyUserName());
			clientConfig.property(ClientProperties.PROXY_PASSWORD, proxyConfig.getProxyPassword());
			clientConfig.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
			clientConfig.connectorProvider(new ApacheConnectorProvider());
		}

		return clientConfig;
	}
}
