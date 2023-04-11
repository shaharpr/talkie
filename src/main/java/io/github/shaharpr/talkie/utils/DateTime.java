package io.github.shaharpr.talkie.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	public static String getTimeNow() {
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
		return dt.format(format);
	}
}
