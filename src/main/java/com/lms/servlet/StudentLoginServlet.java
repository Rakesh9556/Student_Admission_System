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

@WebServlet("/student/login")
public class StudentLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Step 1: Create instance of DAO class to access its methods
	private IndividualStudentDao individualStudentDao;
	private UniversityStudentDao universityStudentDao;
	       
	// Step 2: Initialize the dao instance in the constructor
    public StudentLoginServlet() {
    	this.individualStudentDao = new IndividualStudentDao();
		this.universityStudentDao = new UniversityStudentDao();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// Step 3: Retrive the data from the request
			String studentType = req.getParameter("studentType");
			String credential = req.getParameter("credential");
			String password = req.getParameter("password");
			
			// Step 4: Validate the input fields
			if(studentType == null || studentType.trim().isEmpty()) {
				throw new ApiError(400, "Student type is required!");
			}
			
			if(credential == null || credential.trim().isEmpty()) {
				throw new ApiError(400, "User credential is required!");
			}
			
			if(password == null || password.trim().isEmpty()) {
				throw new ApiError(400, "Password is required!");
			}
			
			studentType = studentType.toUpperCase();
			
			// Step 5: Handle login based on the student type (UNIVERSITY or INDIVIDUAL)
			if(studentType.equals("UNIVERSITY")) {
				
				// Step 6: Login the user and create the user object
				UniversityStudent universityStudent = universityStudentDao.loginUser(credential, password);
				
				// Step 7: Check if user is successfully logged in or not
				if(universityStudent.isLoggedIn()) {
                    res.getWriter().write("University student logged in successfully!");
				}
				else {
                    throw new ApiError(401, "Invalid university email or student ID, or password!");
                }	
			}
			else if(studentType.equals("INDIVIDUAL")) {
				
				// Step 6: Login the user and create the user object
				IndividualStudent individualStudent = individualStudentDao.loginUser(credential, password);
				
				// Step 7: Check if user is successfully logged in or not
				if(individualStudent.isLoggedIn()) {
                    res.getWriter().write("Individual student logged in successfully!");
				}
				else {
                    throw new ApiError(401, "Invalid university email or student ID, or password!");
                }	
			}
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
		
	}

}
