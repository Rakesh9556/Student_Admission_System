package com.lms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.lms.dao.IndividualStudentDao;
import com.lms.dao.UniversityStudentDao;
import com.lms.models.IndividualStudent;
import com.lms.models.UniversityStudent;
import com.lms.util.ApiError;
import com.lms.util.JwtUtil;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class to access its properties
	JwtUtil jwt = new JwtUtil();
	
	// Step 2: Create instance of the user dao class to access dao methods
	private IndividualStudentDao individualStudentDao;
	private UniversityStudentDao universityStudentDao;
	
    public AuthServlet() {
        super();
        this.universityStudentDao = new UniversityStudentDao();
        this.individualStudentDao = new IndividualStudentDao();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			// Step 2: Retrive the data from the request
			String role = req.getParameter("role").toUpperCase();
			String credential = req.getParameter("credential");
			String password = req.getParameter("password");
			
			// Step 3: Validate the input fields
			if(role == null || role.trim().isEmpty()) {
				throw new ApiError(400 , "Role is required");
			}
			
			if(credential == null || credential.trim().isEmpty()) {
				throw new ApiError(400, "User credcential is required");
			}
			
			if(password == null || password.trim().isEmpty()) {
				throw new ApiError(400, "Password is required");
			}
			
			// Step 4: Handle different type of user
			if(role.equals("STUDENT")) {
				
				// Step 5: Define which type of student
				String studentType = req.getParameter("studentType").toUpperCase();
				
				if(studentType == null || studentType.trim().isEmpty()) {
					throw new ApiError(400, "Student type is required");
				}
				
				// Step 6: Handle login based on the student type (UNIVERSITY or INDIVIDUAL)
				if(studentType.equals("UNIVERSITY")) {
					
					// Step 7: Login the user and create the user object
					UniversityStudent universityStudent = universityStudentDao.loginUser(credential, password);
					
					// Step 8: Check if user is successfully logged in or not
					boolean isUserLoggedIn = universityStudent.isLoggedIn();
					
					if(isUserLoggedIn) {
	                    res.getWriter().write("University student logged in successfully!");
					}
					else {
	                    throw new ApiError(401, "Invalid university email or student ID, or password!");
	                }	
				}
				
				else if(studentType.equals("INDIVIDUAL")) {
					
					// Login the user and create the user object
					IndividualStudent individualStudent = individualStudentDao.loginUser(credential, password);
					
					// Check if user is successfully logged in or not
					boolean isUserLoggedIn = individualStudent.isLoggedIn();
					
					if(isUserLoggedIn) {
	                    res.getWriter().write("University student logged in successfully!");
					}
					else {
	                    throw new ApiError(401, "Invalid university email or student ID, or password!");
	                }
				}
			}
			
//			else if(role.equals("FACULTY")) {
//				// Login the user and create the user object				
//			}
			
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
	}

}
