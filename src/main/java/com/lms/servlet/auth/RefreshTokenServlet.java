package com.lms.servlet.auth;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.lms.dao.StudentDao;
import com.lms.dao.AdminDao;
import com.lms.dao.FacultyDao;

import com.lms.models.Student;
import com.lms.models.Admin;
import com.lms.models.Faculty;

import com.lms.util.ApiError;
import com.lms.util.JwtUtil;



// Purpose: Verifies the refresh token and if valid issue a new access token

@WebServlet("/auth/refresh")
public class RefreshTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class and student classes to access their properties
	JwtUtil jwt = new JwtUtil();
	
	StudentDao studentDao = new StudentDao();
	FacultyDao facultyDao = new FacultyDao();
	AdminDao adminDao = new AdminDao();
	
		
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
	    // Verification done in jwt
		try {
			
			// Step 4: Validate token expiry and extract the user id
			jwt.validateTokenExpiry(refreshToken);
			String userId = jwt.getUserIdFromToken(refreshToken);
			
			// Step 4: Verifies if the user exist or not in the database
			boolean isStudentExist = studentDao.findByIdOrEmail(userId);
			boolean isFacultyExist = facultyDao.findByEmailOrId(userId);
			boolean isAdminExist = adminDao.findByEmailOrId(userId);
			
			String newAccessToken;
			
			// Step 5: If user exist retrieve the user data
			if(isStudentExist) {
				
				// Step 6: Retrieve the the individual student object
				Student student = studentDao.getByUsernameOrEmail(userId);
				System.out.println("Stored token: " + student.getRefreshToken());
				
				// Step 7: Check if refresh token matches to the stored ones in database
				if(!refreshToken.equals(student.getRefreshToken())) {
					System.out.println("Invalid token check: Received token: " + refreshToken);
					throw new ApiError(401, "Invalid refresh token!");
				}
				
				// Step 8: If token matches generate a new access token
				newAccessToken = jwt.generateStudentAccessToken(
                		student.getEmail(),
                		student.getRole().toString(),
                		Map.of("universityName", student.getUniversityName(),
                				"universityEmail", student.getEmail(),
                				"department", student.getDepartment().toString(),
                				"specialization", student.getSpecialization().toString())		
                );
				
				// Step 9: Return the new access token in response
	            res.getWriter().write("New Access Token: " + newAccessToken);
			}
			
			else if(isFacultyExist) {
				
				// Retrieve the the individual student object
				Faculty faculty = facultyDao.getByfacultyIdOrEmail(userId);
				System.out.println("Stored token: " + faculty.getRefreshToken());

				// Check if refresh token matches to the stored ones in database
				if(!refreshToken.equals(faculty.getRefreshToken())) {
					 System.out.println("Invalid token check: Received token: " + refreshToken);
					
					throw new ApiError(401, "Invalid refresh token!");
				}
				
				// If token matches generate a new access token
				newAccessToken = jwt.generateFacultyAccessToken(
                		faculty.getEmail(),
                		faculty.getRole().toString(),
                		faculty.getFacultyId(),
                		faculty.getDepartment().toString()
                );
				
				// Step 9: Return the new access token in response
	            res.getWriter().write("New Access Token: " + newAccessToken);		
			}
			
			else if(isAdminExist) {
				
				// Retrieve Admin object by email or id
			    Admin admin = adminDao.getByEmailOrId(userId); 
			    System.out.println("Stored token: " + admin.getRefreshToken());

			    if (!refreshToken.equals(admin.getRefreshToken())) {
			        System.out.println("Invalid token check: Received token: " + refreshToken);
			        throw new ApiError(401, "Invalid refresh token!");
			    }

			    // Generate new access token for Admin
			    newAccessToken = jwt.generateAdminAccessToken(
			    	admin.getEmail(),
			    	admin.getRole().toString(),
			        admin.getAdminId(),
			        admin.getAdminLevel()
			    );

			    // Return the new access token in response
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
