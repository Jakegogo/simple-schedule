package com.common.util.profile;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.util.Calendar;
import java.util.Date;

public class Util {

	public static Date dateAdd(Date theDate, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}
	
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
	
		cal.add(HOUR_OF_DAY, addHours);
		cal.add(MINUTE, addMinutes);
		cal.add(SECOND, addSecond);
	
		return cal.getTime();
	}

}
