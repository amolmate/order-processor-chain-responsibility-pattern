package com.morrisons.wholesale.dsd.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.morrisons.wholesale.dsd.loader.ApplicationConfigLoader;

/**
 * 
 * @author surajv
 *
 */
public class Util {

	public static void setENVVariable(String value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field field = ApplicationConfigLoader.class.getField("ENV");

		setFinalStatic(field, value);
	}

	public static void setFinalStatic(Field field, Object newValue)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}
}
