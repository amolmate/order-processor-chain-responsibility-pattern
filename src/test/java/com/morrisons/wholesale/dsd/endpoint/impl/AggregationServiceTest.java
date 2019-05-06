package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;

@RunWith(MockitoJUnitRunner.class)
public class AggregationServiceTest {

	// @InjectMocks
	// private CancelStockMovementEndPoint cancelStockMovementEndPoint;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Mock
	private WMMException exception;

	@Mock
	private ExternalServiceConfig externalServiceConfig;

	private Response response;

	@Before
	public void setUp() {
		Mockito.when(exceptionFactory.createException(Matchers.anyInt(), Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(exception);
	}

	@Test
	public void testGetExceptionForErrorResponses() {

		// Assert.assertEquals("WMMException is not matching", exception,
		// cancelStockMovementEndPoint.getExceptionForErrorResponse("X",0));
	}

	@Test
	public void testGetOutputEntityClass() {

		// Assert.assertTrue("Class is null",
		// cancelStockMovementEndPoint.getOutputEntityClass() instanceof Class);
	}

	@Test
	public void testGetOutputEntity() {

		// Assert.assertEquals(null,
		// cancelStockMovementEndPoint.getOutputEntity(response));
	}
}