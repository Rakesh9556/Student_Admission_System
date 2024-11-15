//package com.lms.service;
//
//import org.json.JSONObject;
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//
//public class RazorpayService {
//	
//	public static final String RAZORPAY_SECRET_ID = "rzp_test_6RUhQmBt3iCH1Y";
//	public static final String RAZORPAY_SECRET_KEY = "1Sso3oqCCCfR0ZSBLmMRORpJ";
//	
//	public static JSONObject createTransaction() throws Exception {
//		try {
//			
//			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_SECRET_ID, RAZORPAY_SECRET_KEY);
//			
//			JSONObject transactionRequest = new JSONObject();
//			transactionRequest.put("amount", 10000);
//			transactionRequest.put("currency", "INR");
//			transactionRequest.put("receipt", "receipt#1");
//			
//			Order order = razorpay.orders.create(transactionRequest);
//			return order.toJson(); 
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("Failed to create transaction!", e);
//		}
//	}
//	
//}
