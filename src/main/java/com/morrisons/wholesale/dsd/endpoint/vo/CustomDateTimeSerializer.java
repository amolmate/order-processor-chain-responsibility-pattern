package com.morrisons.wholesale.dsd.endpoint.vo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateTimeSerializer extends JsonSerializer<Date> {

	public static final String DATE_FORMAT_IP = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.<SimpleDateFormat>withInitial(() -> {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_IP);
		sdf.setLenient(false);
		return sdf;
	});

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		if (value == null) {
			gen.writeNull();
		} else {
			gen.writeString(DATE_FORMAT.get().format(value.getTime()));
		}
	}
}