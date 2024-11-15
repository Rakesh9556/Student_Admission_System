package com.lms.servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.lms.dao.AdminDao;
import com.lms.dao.FacultyDao;
import com.lms.dao.StudentDao;
import com.lms.models.Admin;
import com.lms.models.Faculty;
import com.lms.models.Student;
import com.lms.util.ApiError;
import com.lms.util.JwtUtil;

// Purpose: Handle user authentication and generates accessToken and refreshToken upon successful login

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class to access its properties
	JwtUtil jwt = new JwtUtil();
	
	// Step 2: Create instance of the user dao class to access dao methods
	private StudentDao studentDao;
	private FacultyDao facultyDao;
	private AdminDao adminDao;
	
    public AuthServlet() {
        super();
        this.studentDao = new StudentDao();
        this.facultyDao = new FacultyDao();
        this.adminDao = new AdminDao();
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
				
				// Step 7: Login the user and create the user object
				Student student = studentDao.loginUser(credential, password);
				
				// Step 8: Check if user is successfully logged in or not
				boolean isUserLoggedIn = student.isLoggedIn();
				
				if(isUserLoggedIn) {
                    res.getWriter().write("University student logged in successfully!");
                    
                    //  Step 9: If user logged in successfully then generate the access token
                    String accessToken = jwt.generateStudentAccessToken(
                    		student.getStudentId(),
                    		student.getRole().toString(),
                    		Map.of("universityName" , student.getUniversityName(),
                    				"universityEmail", student.getEmail(),
                    			    "department", student.getDepartment().toString(),
                    			    "specialization", student.getSpecialization().toString())
                    );
                    
                    // Step 10: Generate the refresh token
                    String refreshToken = jwt.generateRefreshToken(student.getStudentId());
                    
                    // Step 11: Set the refresh token in the UniversityStudent object
                    student.setRefreshToken(refreshToken);
                    
                    // Step 12: Update the student in the database here
                    studentDao.updateRefreshToken(student);
                    
                    // Debugging
                    // System.out.println("Access token: " + accessToken);
                    // System.out.println("Refresh token: " + refreshToken);
                    
                    // Send tokens back to client
                    res.getWriter().write("Access Token: " + accessToken + ", Refresh Token: " + refreshToken);
                
				}
				else {
                    throw new ApiError(401, "Invalid university email or student ID, or password!");
                }	
			}
            
			else if(role.equals("FACULTY")) {
				
				// Login the user and create the user object	
				Faculty faculty = facultyDao.loginFaculty(credential, password);
				
				// Check if user is successfully logged in or not
				boolean isUserLoggedIn = faculty.isLoggedIn();
				
				if(isUserLoggedIn) {
                    res.getWriter().write("Faculty logged in successfully!");
                    
                    // If user logged in successfully then generate the access token
                    String accessToken = jwt.generateFacultyAccessToken(
                    		faculty.getEmail(),
                    		faculty.getRole().toString(),
                    		faculty.getFacultyId(),
                    		faculty.getDepartment().toString()		
                    );
                    
                    // Generate refresh token
                    String refreshToken = jwt.generateRefreshToken(faculty.getEmail());
                    
                    // Set the refresh token in the Faculty object
                    faculty.setRefreshToken(refreshToken);
                    
                    // Optionally, update the faculty in the database here
                    facultyDao.updateRefreshToken(faculty);
                    
                    // Send tokens back to client
                    res.getWriter().write("Access Token: " + accessToken + ", Refresh Token: " + refreshToken);
				}
				else {
                    throw new ApiError(401, "Invalid email or faculty ID, or password!");
                }
			}
			
            else if(role.equals("ADMIN")) {
				
				// Login the user and create the user object
				Admin admin = adminDao.loginAdmin(credential, password);
				
				// Check if user is successfully logged in or not
				boolean isUserLoggedIn = admin.isLoggedIn();
				
				if(isUserLoggedIn) {
                    res.getWriter().write("Admin logged in successfully!");
                    
                    // If Admin logged in successfully then generate the access token
                    String accessToken = jwt.generateAdminAccessToken(
                    		admin.getRole().toString(),
                    		admin.getAdminId(),
                    		admin.getAdminLevel(),
                    		admin.getEmail()
                    		
                    );
                    
                    // Generate refresh token
                    String refreshToken = jwt.generateRefreshToken(admin.getEmail());
                    
                    // Set the refresh token in the Admin object
                    admin.setRefreshToken(refreshToken);
                    
                    // Optionally, update the admint in the database here
                    adminDao.updateRefreshToken(admin);
                    
                    // Send tokens back to client
                    res.getWriter().write("Access Token: " + accessToken + ", Refresh Token: " + refreshToken);
       
				}
				else {
                    throw new ApiError(401, "Invalid email or ID, or password!");
                }
				
			}
			
			else {
				throw new ApiError(400, "User role not found!");
			}
			
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
	}

}
