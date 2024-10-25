package com.lms.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.lms.util.ApiError;
import com.lms.util.JwtUtil;


// Purpose: Verifies the validity of the access token which is sent with requests access protected endpoint

@WebServlet("/auth/verify")
public class TokenVerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	// Step 1: Create an instance of JwtUtil class and student classes to access their properties
	JwtUtil jwt = new JwtUtil();
	
	
    public TokenVerificationServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Step 1: Extract the access token from the http header (authorization header)
		String token = req.getHeader("Authorization");
		
		// Step 2: Validate the token
		if (token == null || !token.startsWith("Bearer ")) {
	        throw new ApiError(400, "Token is missing or incorrectly formatted");
	    }
	    token = token.replace("Bearer ", "").trim();
		
		// Step 3: If access token exist verify it 
		// Verification done in jwt class
		try {
			
			// Step 4: Vaklidate access token expiry then extract the user id and role 
			jwt.validateTokenExpiry(token);
			String userId = jwt.getUserIdFromToken(token);
            String role = jwt.getUserRoleFromToken(token);
            
            // Step 5: Set the user ID and role as request attributes to forward to the next layer
            req.setAttribute("userId", userId);
            req.setAttribute("role", role);
            
            // Step 6: Send a response to the client
            res.getWriter().write("User id: " + userId + " Role: " + role);
            
            // We will handle role specific route here

		} 
		catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token: " + e.getMessage());
		}
	}

}
