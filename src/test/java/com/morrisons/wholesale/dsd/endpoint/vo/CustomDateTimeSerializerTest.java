package com.morrisons.wholesale.dsd.endpoint.vo;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(value = MockitoJUnitRunner.class)
public class CustomDateTimeSerializerTest {

	@Spy
	@InjectMocks
	private CustomDateTimeSerializer customDateTimeSerializer;

	@Mock
	private JsonGenerator jsonGenerator;

	@Mock
	private SerializerProvider serializerProvider;

	@Mock
	private Date date;

	@Test
	public void testSerialize() throws IOException {

		customDateTimeSerializer.serialize(date, jsonGenerator, serializerProvider);
		Mockito.verify(jsonGenerator, Mockito.times(1)).writeString(Matchers.anyString());
	}

	@Test
	public void testSerializeNull() throws IOException {

		customDateTimeSerializer.serialize(null, jsonGenerator, serializerProvider);
		Mockito.verify(customDateTimeSerializer, Mockito.times(1)).serialize(null, jsonGenerator, serializerProvider);

	}

}