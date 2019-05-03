package com.morrisons.wholesale.dsd.loader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.morrisons.wholesale.dsd.util.Util;

/**
 * 
 * @author surajv
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationConfigLoaderTest {

	@Mock
	ApplicationConfigLoader applicationConfigLoader;

	@Test
	public void testGetApplicationConfig()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Util.setENVVariable("local");
		Assert.assertNotNull("ApplicationConfig is null", ApplicationConfigLoader.getApplicationConfig());
		Util.setENVVariable(null);
	}

	@Test(expected = Exception.class)
	public void testIncorrectEnvConfig()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Util.setENVVariable("X");
		ApplicationConfigLoader.getApplicationConfig();
		Util.setENVVariable(null);
	}

	@Test(expected = Exception.class)
	public void testMissingEnvConfig()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Util.setENVVariable(null);
		ApplicationConfigLoader.getApplicationConfig();
		Util.setENVVariable(null);
	}
}