//package com.lms.servlet.Payment;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import org.json.*;
//import com.razorpay.*;
//
//import com.lms.service.RazorpayService;
//import com.lms.util.ApiError;
//
//@WebServlet("/razorpay/payment")
//public class RazorPayOrderServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    public RazorPayOrderServlet() {
//        super();
//    }
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		RazorpayClient razorpay = null;
//		String orderId = null;
//		
//		try {
//			
//			razorpay = new RazorpayClient("rzp_test_6RUhQmBt3iCH1Y", "1Sso3oqCCCfR0ZSBLmMRORpJ");
//
//			JSONObject orderRequest = new JSONObject();
//			orderRequest.put("amount",100);
//			orderRequest.put("currency","INR");
//			orderRequest.put("receipt", "zxr456");
//
//			Order order = razorpay.orders.create(orderRequest);
//			orderId = order.get("id");
//			
//		} catch (RazorpayException e) {
//			e.printStackTrace();
//		}
//		res.getWriter().append(orderId);
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		
//		RazorpayClient razorpay = null;
//		
//		try {
//			
//			razorpay = new RazorpayClient("rzp_test_6RUhQmBt3iCH1Y", "1Sso3oqCCCfR0ZSBLmMRORpJ");
//
//			JSONObject options = new JSONObject();
//			options.put("razorpay_payment_id", req.getParameter("razorpay_payment_id"));
//			options.put("razorpay_order_id", req.getParameter("razorpay_order_id"));
//			options.put("razorpay_signature", req.getParameter("razorpay_signature"));
//			
//			boolean SigRes = Utils.verifyPaymentSignature(options, "EnLs21M47BllR3X8PSFtjtbd");
//			
//			if(SigRes) {
//				res.getWriter().append("Payment successful and Signature verified.");
//			}
//			else {
//				res.getWriter().append("Payment failed and Signature not verified!");
//			}
//			
//		} catch (RazorpayException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
