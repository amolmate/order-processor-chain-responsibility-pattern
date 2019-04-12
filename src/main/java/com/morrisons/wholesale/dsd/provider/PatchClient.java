package com.morrisons.wholesale.dsd.provider;

import javax.ws.rs.client.Client;

public class PatchClient {

	private final Client client;

	public PatchClient(Client client) {

		this.client = client;
	}

	public Client getClient() {
		return client;
	}
}
