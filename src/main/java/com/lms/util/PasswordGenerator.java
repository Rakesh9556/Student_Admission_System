package com.lms.util;

public class PasswordGenerator {
	
	public static String generatePassword(String firstname, String dateOfBirth) {
		String namePart = (firstname.length() >= 4) ? firstname.substring(0, 4) : firstname;
        String datePart = dateOfBirth.replace("-", "");
        
        return namePart.toUpperCase() + datePart;
	}
}
