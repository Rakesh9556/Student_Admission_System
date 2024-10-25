package com.lms.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.lms.dao.IndividualStudentDao;
import com.lms.dao.UniversityStudentDao;
import com.lms.models.IndividualStudent;
import com.lms.models.UniversityStudent;
import com.lms.util.ApiError;
import com.lms.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

// Purpose: Verifies the refresh token and if valid issue a new access token

@WebServlet("/auth/refresh")
public class RefreshTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class and student classes to access their properties
	JwtUtil jwt = new JwtUtil();
	
	IndividualStudentDao individualStudentDao = new IndividualStudentDao();
	UniversityStudentDao universityStudentDao = new UniversityStudentDao();
	
		
    public RefreshTokenServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Step 1: Extract the refresh token from the http header (RefreshToken header)
		String refreshToken = req.getHeader("RefreshToken");
		
		// Step 2: Validate the token
		if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
	        throw new ApiError(400, "Refresh token is missing or incorrectly formatted");
	    }
	    refreshToken = refreshToken.replace("Bearer ", "").trim();
		
		
		// Step 3: If refresh token exist, verify it
		try {
			
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(jwt.getSecret())
					.build()
					.parseClaimsJws(refreshToken)
					.getBody();
			
			System.out.println("Claims: " + claims);

			
			// Step 4: Extract the user id
			String userId = claims.getSubject();
			Date expiration = claims.getExpiration();
	        if (expiration != null && expiration.before(new Date())) {
	            throw new ApiError(401, "Refresh token has expired!");
	        }
			
			// Step 4: Verifies if the user exist or not in the database
			boolean isIndividualStudentExist = individualStudentDao.findUser(userId);
			boolean isUniversityStudentExist = universityStudentDao.findUser(userId);
			
			String newAccessToken;
			
			// Step 5: If user exist retrieve the user data
			if(isIndividualStudentExist) {
				
				// Step 6: Retrieve the the individual student object
				IndividualStudent individualStudent = individualStudentDao.getByUsernameOrEmail(userId);
				System.out.println("Stored token: " + individualStudent.getRefreshToken());
				
				// Step 7: Check if refresh token matches to the stored ones in database
				if(!refreshToken.equals(individualStudent.getRefreshToken())) {
					System.out.println("Invalid token check: Received token: " + refreshToken);
					throw new ApiError(401, "Invalid refresh token!");
				}
				
				// Step 8: If token matches generate a new access token
				newAccessToken = jwt.generateStudentAccessToken(
                		individualStudent.getEmail(),
                		individualStudent.getRole().toString(),
                		individualStudent.getStudentType().toString(),
                		Map.of("username", individualStudent.getUsername(),
                				"universityName", individualStudent.getUniversityName(),
                				"department", individualStudent.getDepartment().toString(),
                				"specialization", individualStudent.getSpecialization().toString())		
                );
				
				// Step 9: Return the new access token in response
	            res.getWriter().write("New Access Token: " + newAccessToken);
			}
			else if(isUniversityStudentExist) {
				
				// Retrieve the the individual student object
				UniversityStudent universityStudent = universityStudentDao.getByUsernameOrEmail(userId);
				System.out.println("Stored token: " + universityStudent.getRefreshToken());

				// Check if refresh token matches to the stored ones in database
				if(!refreshToken.equals(universityStudent.getRefreshToken())) {
					 System.out.println("Invalid token check: Received token: " + refreshToken);
					
					throw new ApiError(401, "Invalid refresh token!");
				}
				
				// If token matches generate a new access token
				newAccessToken = jwt.generateStudentAccessToken(
                		universityStudent.getStudentId(),
                		universityStudent.getRole().toString(),
                		universityStudent.getStudentType().toString(),
                		Map.of("university name" , universityStudent.getUniversityName(),
                				"university email", universityStudent.getEmail(),
                			    "department", universityStudent.getDepartment().toString(),
                			    "specialization", universityStudent.getSpecialization().toString())
                );
				
				
				// Step 9: Return the new access token in response
	            res.getWriter().write("New Access Token: " + newAccessToken);
			}
			else {
				throw new ApiError(404, "User not found");
			}
								
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write(e.getMessage());
		}
	}

}
