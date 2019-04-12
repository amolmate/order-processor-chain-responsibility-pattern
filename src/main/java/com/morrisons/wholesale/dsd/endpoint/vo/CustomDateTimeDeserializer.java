package com.morrisons.wholesale.dsd.endpoint.vo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.morrisons.wholesale.dsd.exception.WMMException;

public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {

		String dateAsString = jsonparser.getText();
		try {
			if (!StringUtils.isBlank(dateAsString)) {
				return CustomDateTimeSerializer.DATE_FORMAT.get().parse(dateAsString);
			} else {
				return null;
			}
		} catch (ParseException e) {
			throw new WMMException(1, "Date parsing Exception", e, WMMException.DEFAULT_HTTP_STATUS_CODE);
		}
	}
}