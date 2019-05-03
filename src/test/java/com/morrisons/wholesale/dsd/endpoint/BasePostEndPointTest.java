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
import com.morrisons.wholesale.dsd.util.DataBuilder;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BasePostEndPointTest {

	@Spy
	@InjectMocks
	private DummyBasePostEndPoint basePostEndPoint;

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

	@Before
	public void setUp() {

		Mockito.when(client.target(Matchers.anyString())).thenReturn(webTarget);
		Mockito.when(webTarget.request(Matchers.anyString())).thenReturn(invocationBuilder);
		Mockito.when(invocationBuilder.post(Matchers.any())).thenReturn(response);
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
	public void testPostWithStatusValid() {

		//Mockito.doReturn(true).when(basePostEndPoint).isStatusValid(Matchers.anyInt());

		//Assert.assertNotNull("Post Failed", basePostEndPoint.post(DataBuilder.getParameterMappings(), output));
	}

	@Test
	public void testIsStatusValid() {

		//Assert.assertTrue(basePostEndPoint.isStatusValid(201));
		//Assert.assertFalse(basePostEndPoint.isStatusValid(200));
	}

	@Test(expected = Exception.class)
	public void testPostWithStatusInvalid() {

		//Mockito.doReturn(false).when(basePostEndPoint).isStatusValid(Matchers.anyInt());
		//basePostEndPoint.post(DataBuilder.getParameterMappings(), output);
	}

}
