package com.lms.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.lms.dao.FacultyDao;
import com.lms.dao.IndividualStudentDao;
import com.lms.dao.UniversityStudentDao;
import com.lms.util.ApiError;
import com.lms.util.JwtUtil;

// Purpose: Invalidate the token so that no access token can be created and set the isLoggedIn field to flase in database

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class and student classes to access their properties
	JwtUtil jwt = new JwtUtil();
	IndividualStudentDao individualStudentDao = new IndividualStudentDao();
    UniversityStudentDao universityStudentDao = new UniversityStudentDao();
    FacultyDao facultyDao = new FacultyDao();
		
    public LogoutServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Step 1: Extract the access token from the header
		String token = req.getHeader("Authorization");
		
		// Step 2: Validate the token
		if (token == null || !token.startsWith("Bearer ")) {
	        throw new ApiError(400, "Token is missing or incorrectly formatted");
	    }
	    token = token.replace("Bearer ", "").trim();
		
		// Step 3: if token exist identify the user and invalidate teh token
		try {
			// Step 4: Retrieve the user
			String userId = jwt.getUserIdFromToken(token);
			String role = jwt.getUserRoleFromToken(token);
			System.out.println("Role is : " + role);
			
			// Step 5: Handle different type of user
			if(role.equals("STUDENT")) {
				if (individualStudentDao.findUser(userId)) {
	                individualStudentDao.logout(userId);
	            } else if (universityStudentDao.findUser(userId)) {
	                universityStudentDao.logout(userId);
	            } else {
	                throw new ApiError(404, "Student not found");
	            }
			}
			else if(role.equals("FACULTY")){
				if(facultyDao.findByEmailOrId(userId)) {
					facultyDao.logout(userId);
				}else if(facultyDao.findByEmailOrId(userId)) {
					facultyDao.logout(userId);
				}else {
					throw new ApiError(404, "Faculty not found");
				}
			}
			
			
			res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("Logout successful");
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			res.getWriter().write(e.getMessage());
		}
	}

}
