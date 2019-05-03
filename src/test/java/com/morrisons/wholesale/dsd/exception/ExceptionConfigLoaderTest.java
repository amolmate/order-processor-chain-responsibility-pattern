package com.morrisons.wholesale.dsd.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionConfigLoaderTest {

	@InjectMocks
	private ExceptionConfigLoader exceptionConfigLoader;
	
	@Mock
	ExceptionConfigListWrapper exceptionConfigListWrapper;

	@Test
	public void testInitializeExceptionMap() {
		exceptionConfigLoader.initializeExceptionMap();
	}

	@Test
	public void testGetErrorMessage() {
		Assert.assertNotNull("ExceptionConfigDetails is null", exceptionConfigLoader.getErrorMessage(401));
	}
}
