package com.morrisons.wholesale.dsd.endpoint.vo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.morrisons.wholesale.dsd.exception.WMMException;

@RunWith(value = MockitoJUnitRunner.class)
public class CustomDateTimeDeserializerTest {

	@InjectMocks
	private CustomDateTimeDeserializer customDateTimedeSerializer;

	@Mock
	private JsonParser jsonParser;

	@Mock
	private DeserializationContext deSerializationContext;

	@Test
	public void testdeSerialize() throws IOException {

		customDateTimedeSerializer.deserialize(jsonParser, deSerializationContext);
		Assert.assertNull(customDateTimedeSerializer.deserialize(jsonParser, deSerializationContext));

	}

	@Test(expected = WMMException.class)
	public void testdeSerializeCustomDateSerializerException() throws WMMException, IOException {

		Mockito.when(jsonParser.getText()).thenReturn("ABC");
		customDateTimedeSerializer.deserialize(jsonParser, deSerializationContext);
	}

	@Test
	public void testdeSerializeCustomDateSerializer() throws IOException {

		Mockito.when(jsonParser.getText()).thenReturn("2018-04-23T18:30:00.001Z");
		customDateTimedeSerializer.deserialize(jsonParser, deSerializationContext);
		Assert.assertNotNull(customDateTimedeSerializer.deserialize(jsonParser, deSerializationContext));
	}
}