package com.morrisons.wholesale.dsd.endpoint.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.exception.ConfigServiceException;

@RunWith(MockitoJUnitRunner.class)
public class GenerateDistributionOrderEndPointTest {

	@Mock
	private ConfigServiceException exception;

	@Before
	public void setUp() {

	}

	@Test
	public void testGetExceptionForErrorResponse() {

		// Assert.assertEquals("WMMException is not matching", exception,
		// generateDistributionOrderEndPoint.getExceptionForErrorResponse("X",0));
	}

	@Test
	public void testGetOutputEntityClass() {
		// Assert.assertTrue("Class is null",
		// generateDistributionOrderEndPoint.getOutputEntityClass() instanceof
		// Class);
	}

	@Test
	public void testGetOutputEntity() {

		// Assert.assertNotNull("output entity not null",
		// generateDistributionOrderEndPoint.getOutputEntity(Response.noContent().build()));

	}

	@Test
	public void testIsStatusValid() {

		// Assert.assertTrue(generateDistributionOrderEndPoint.isStatusValid(202));
		// Assert.assertFalse(generateDistributionOrderEndPoint.isStatusValid(212));
	}
}