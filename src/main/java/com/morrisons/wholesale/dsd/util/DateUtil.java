package com.morrisons.wholesale.dsd.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil
{
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public String getCurrentDateInString()
	{
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Date date = new Date();
		return formatter.format(date);
	}
}
