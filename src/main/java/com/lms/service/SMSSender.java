package com.lms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class SMSSender {
	
	public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_ID");
	public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
	


	// Send if application is successfully created
	public static void sendApplicationCreatedSMS(String fullname, String to, Long applicationId) throws Exception {
		
		try {
			
			if(ACCOUNT_SID == null || AUTH_TOKEN == null) {
				throw new IllegalAccessError("Account SID or Auth token is missing");
			}
			
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			
			System.out.println("Fullname: " + fullname + " Moblie: " + to + " ApplicationId: " + applicationId);
			String messageBody = "Hello " + fullname + ". Your application has been successfully submitted. Application ID: " + applicationId + ". First 4 digit of your name and dob is your password. Please keep this information safe.";
			
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(to),
					new com.twilio.type.PhoneNumber("+12177030314"),
					messageBody
					).create();
			
	        System.out.println("Message sent with SID: " + message.getSid());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to create message!", e);	
		}
	}

	// Send if exam is scheduled for the student
	public static void sendApplicationRejectedSMS(String fullname, String to) throws Exception {
		
		try {
			
			if(ACCOUNT_SID == null || AUTH_TOKEN == null) {
				throw new IllegalAccessError("Account SID or Auth token is missing");
			}
			
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			
			System.out.println("Fullname: " + fullname + " Moblie: " + to );
			String messageBody = "Hello " + fullname + "!. We are sorry to inform you that your application is not accepted this time.";
			
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(to),
					new com.twilio.type.PhoneNumber("+12177030314"),
					messageBody
					).create();
			
	        System.out.println("Message sent with SID: " + message.getSid());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to create message!", e);	
		}
	}
	
	
	public static void sendExamScheduledSMS(String fullname, String to, Long applicationId) throws Exception {
		
		try {
			
			if(ACCOUNT_SID == null || AUTH_TOKEN == null) {
				throw new IllegalAccessError("Account SID or Auth token is missing");
			}
			
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			
			System.out.println("Fullname: " + fullname + " Moblie: " + to + " ApplicationId: " + applicationId);
			String messageBody = "Hello " + fullname + ". Congratulations! You have been selected for the entrance test. Kindly check your mail to download the admit card. Your Application ID: " + applicationId + ". Please keep this information safe.";
			
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(to),
					new com.twilio.type.PhoneNumber("+12177030314"),
					messageBody
					).create();
			
	        System.out.println("Message sent with SID: " + message.getSid());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to create message!", e);	
		}
	}
	
	
	public static void sendApplicationApprovedSMS(String fullname, String to) throws Exception {
		
		try {
			
			if(ACCOUNT_SID == null || AUTH_TOKEN == null) {
				throw new IllegalAccessError("Account SID or Auth token is missing");
			}
			
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			
			System.out.println("Fullname: " + fullname + " Moblie: " + to);
			String messageBody = "Hello " + fullname + ". Congratulations! You have been selected for the admission. Kindly check your mail to get the admission link. Please keep this information safe.";
			
			Message message = Message.creator(
					new com.twilio.type.PhoneNumber(to),
					new com.twilio.type.PhoneNumber("+12177030314"),
					messageBody
					).create();
			
	        System.out.println("Message sent with SID: " + message.getSid());
		}
		
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to create message!", e);	
		}
	}
}


