package com.lms.util;

import java.util.Random;

public class ApplicationIdGenerator {
	
	private final static Random random = new Random();
	
	public static long generateUniqueId() {
	    // Get the last 8 digits of the current time in milliseconds
	    String timePart = String.valueOf(System.currentTimeMillis()).substring(String.valueOf(System.currentTimeMillis()).length() - 8);
	    
	    // Generate a 4-digit random number
	    String randomPart = String.format("%04d", random.nextInt(10000));
	    
	    // Combine the timestamp part and random part for a unique 12-digit ID
	    String uniqueIdString =  timePart + randomPart;
	    return Long.parseLong(uniqueIdString);
	}

}
