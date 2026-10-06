package com.morrisons.wholesale.dsd.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExceptionConfigLoaderTest - JUnit 5 rewrite
 * Tests exception configuration loading and error message retrieval.
 * 
 * @author surajv
 */
@ExtendWith(MockitoExtension.class)
public class ExceptionConfigLoaderTest {

	@InjectMocks
	private ExceptionConfigLoader exceptionConfigLoader;
	
	@Mock
	private ExceptionConfigListWrapper exceptionConfigListWrapper;

	@Test
	public void testInitializeExceptionMap() {
		exceptionConfigLoader.initializeExceptionMap();
	}

	@Test
	public void testGetErrorMessage() {
		assertNotNull(exceptionConfigLoader.getErrorMessage(401), "ExceptionConfigDetails should not be null");
	}
}
