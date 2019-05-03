package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;
import com.morrisons.wholesale.dsd.exception.WMMException;

@RunWith(MockitoJUnitRunner.class)
public class StockMovePostEndPointTest {

	@InjectMocks
	private StockMovePostEndPoint stockMovePostEndPoint;

	@Mock
	private IWMMExceptionFactory exceptionFactory;

	@Mock
	private WMMException exception;

	@Before
	public void setUp() {

		Mockito.when(exceptionFactory.createException(Matchers.anyInt(), Matchers.anyString(),Matchers.anyInt())).thenReturn(exception);
	}

	@Test
	public void testGetExceptionForErrorResponse() {
		Assert.assertEquals("WMMException is not matching", exception,
				stockMovePostEndPoint.getExceptionForErrorResponse("X",0));
	}

	@Test
	public void testGetOutputEntityClass() {
		Assert.assertTrue("Class is null", stockMovePostEndPoint.getOutputEntityClass() instanceof Class);
	}

	@Test
	public void testgetOutputEntity() {

		Assert.assertNotNull("output entity not null",
				stockMovePostEndPoint.getOutputEntity(Response.noContent().build()));

	}

	@Test
	public void testisStatusValid() {

		Assert.assertTrue("Status not valid", stockMovePostEndPoint.isStatusValid(202));
		Assert.assertFalse("Status not valid", stockMovePostEndPoint.isStatusValid(212));

	}

}
