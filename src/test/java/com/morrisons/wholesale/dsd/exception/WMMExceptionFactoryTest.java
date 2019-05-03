package com.morrisons.wholesale.dsd.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WMMExceptionFactoryTest {

	@InjectMocks
	private WMMExceptionFactory wMMExceptionFactory;

	@Mock
	private ExceptionConfigLoader exceptionConfigLoader;

	@Mock
	private ExceptionConfigDetails exceptionConfigDetails;

	@Before
	public void setUp() {

		Mockito.when(exceptionConfigLoader.getErrorMessage(Matchers.anyInt())).thenReturn(exceptionConfigDetails);
	}

	@Test
	public void testCreateExceptionInt() {
		Assert.assertNotNull("WMMException is null", wMMExceptionFactory.createException(1));
	}

	@Test
	public void testCreateExceptionIntThrowable() {
		Assert.assertNotNull("WMMException is null", wMMExceptionFactory.createException(1, new Throwable()));
	}

	@Test
	public void testCreateExceptionIntString() {
		Assert.assertNotNull("WMMException is null", wMMExceptionFactory.createException(1, "X"));
	}

	@Test
	public void testCreateExceptionIntStringThrowable() {
		Assert.assertNotNull("WMMException is null", wMMExceptionFactory.createException(1, "X", new Throwable()));
	}
}
