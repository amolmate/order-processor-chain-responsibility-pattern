package com.morrisons.wholesale.dsd.endpoint;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.util.DataBuilder;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseGetEndPointTest {

	@Spy
	@InjectMocks
	private DummyBaseGetEndPoint baseGetEndPoint;

	@Mock
	private Client client;

	@Mock
	private ExternalServiceConfig externalServiceConfig;

	@Mock
	private WebTarget webTarget;

	@Mock
	private Builder invocationBuilder;

	@Mock
	private Response response;

	@Mock
	private StatusType type;

	@Mock
	private DummyIO output;

	@Mock
	private ParameterMappings parameterMappings;

	@Before
	public void setUp() {

		Mockito.when(client.target(Matchers.anyString())).thenReturn(webTarget);
		Mockito.when(webTarget.request(Matchers.anyString())).thenReturn(invocationBuilder);
		Mockito.when(invocationBuilder.get()).thenReturn(response);
		Mockito.when(response.getStatusInfo()).thenReturn(type);

		Mockito.when(externalServiceConfig.getAuthorization()).thenReturn("X");
		Mockito.when(externalServiceConfig.getApiKey()).thenReturn("X");

		Mockito.when(webTarget.resolveTemplate(Matchers.anyString(), Matchers.anyString())).thenReturn(webTarget);
		Mockito.when(webTarget.queryParam(Matchers.anyString(), Matchers.anyString())).thenReturn(webTarget);

		Mockito.when(invocationBuilder.header(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(invocationBuilder);

		@SuppressWarnings("unchecked")
		Object readEntity = response.readEntity(Matchers.any(Class.class));
		Mockito.when(readEntity).thenReturn(output);
	}

	@Test
	public void testGetAndResponse() {

		//Mockito.doReturn(true).when(baseGetEndPoint).isStatusValid(Matchers.anyInt());
		//Assert.assertNotNull("Get Failed", baseGetEndPoint.get(DataBuilder.getParameterMappings()));
	}

	@Test
	public void testIsStatusValid() {

		//Assert.assertTrue(baseGetEndPoint.isStatusValid(200));
		//Assert.assertFalse(baseGetEndPoint.isStatusValid(201));
	}
}