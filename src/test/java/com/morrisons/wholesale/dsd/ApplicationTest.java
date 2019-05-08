package com.morrisons.wholesale.dsd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.exception.ConfigServiceException;
import com.morrisons.wholesale.dsd.exception.ExceptionConfigLoader;
import com.morrisons.wholesale.dsd.processor.IWholesaleDSDOrderProcessor;

/**
 * 
 * 
 *
 */
@RunWith(PowerMockRunner.class)
public class ApplicationTest {

	@Mock
	private IWholesaleDSDOrderProcessor applicationService;

	@Mock
	private ConfigServiceException wMMException;

	@Mock
	private ExceptionConfigLoader exceptionConfigLoader;

	private ApplicationConfig applicationConfig;

	@Before
	public void setUp() {

		/*
		 * applicationConfig = new ApplicationConfig();
		 * 
		 * snsConfig = new SNSConfig(); snsConfig.setEnableSNSPublishing(true);
		 * 
		 * applicationConfig.setSnsConfig(snsConfig);
		 * 
		 * PowerMockito.mockStatic(ApplicationConfigLoader.class);
		 * PowerMockito.when(ApplicationConfigLoader.getApplicationConfig()).
		 * thenReturn(applicationConfig);
		 * 
		 * PowerMockito.mockStatic(HibernateSessionFactory.class);
		 * PowerMockito.when(HibernateSessionFactory.getSessionfactory(
		 * applicationConfig)).thenReturn(sessionFactory);
		 * 
		 * PowerMockito.mockStatic(Guice.class);
		 * PowerMockito.when(Guice.createInjector(Matchers.any(Module.class))).
		 * thenReturn(injector);
		 * 
		 * PowerMockito.when(injector.getInstance(IApplicationService.class)).
		 * thenReturn(applicationService);
		 * 
		 * Mockito.when(exceptionFactory.createException(Matchers.anyInt(),
		 * Matchers.any(Throwable.class))) .thenReturn(wMMException);
		 */
	}

	@Test
	public void testMain() {

		// Application.main(null);
	}

	@Test(expected = ConfigServiceException.class)
	public void testMainWithWMMException() {

		/*
		 * Mockito.when(ApplicationConfigLoader.getApplicationConfig()).
		 * thenThrow(wMMException); Application.main(null);
		 */
	}

	@Test(expected = Exception.class)
	public void testMainWithException() {

		/*
		 * Mockito.when(ApplicationConfigLoader.getApplicationConfig()).
		 * thenThrow(new RuntimeException()); Application.main(null);
		 */
	}

	@Test
	public void testIsEnableSNSPublishingReturnsFalse() {

		/*
		 * applicationConfig.getSnsConfig().setEnableSNSPublishing(false);
		 * Application.main(null);
		 */
	}

	@Test
	public void testMainForSessionFactoryBranchCoverage() {

		/*
		 * Mockito.when(HibernateSessionFactory.getSessionfactory(
		 * applicationConfig)).thenReturn(null); Application.main(null);
		 */
	}

}