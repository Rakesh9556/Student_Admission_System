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
import com.lms.dao.IndividualStudentDao;
import com.lms.dao.UniversityStudentDao;
import com.lms.models.Admin;
import com.lms.models.Faculty;
import com.lms.models.IndividualStudent;
import com.lms.models.UniversityStudent;
import com.lms.util.ApiError;
import com.lms.util.JwtUtil;

// Purpose: Handle user authentication and generates accessToken and refreshToken upon successful login

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create an instance of JwtUtil class to access its properties
	JwtUtil jwt = new JwtUtil();
	
	// Step 2: Create instance of the user dao class to access dao methods
	private IndividualStudentDao individualStudentDao;
	private UniversityStudentDao universityStudentDao;
	private FacultyDao facultyDao;
	private AdminDao adminDao;
	
    public AuthServlet() {
        super();
        this.universityStudentDao = new UniversityStudentDao();
        this.individualStudentDao = new IndividualStudentDao();
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
	                    
	                    //  Step 9: If user logged in successfully then generate the access token
	                    String accessToken = jwt.generateStudentAccessToken(
	                    		universityStudent.getStudentId(),
	                    		universityStudent.getRole().toString(),
	                    		universityStudent.getStudentType().toString(),
	                    		Map.of("university name" , universityStudent.getUniversityName(),
	                    				"university email", universityStudent.getEmail(),
	                    			    "department", universityStudent.getDepartment().toString(),
	                    			    "specialization", universityStudent.getSpecialization().toString())
	                    );
	                    
	                    // Step 10: Generate the refresh token
	                    String refreshToken = jwt.generateRefreshToken(universityStudent.getStudentId());
	                    
	                    // Step 11: Set the refresh token in the UniversityStudent object
	                    universityStudent.setRefreshToken(refreshToken);
	                    
	                    // Step 12: Update the student in the database here
	                    universityStudentDao.updateRefreshToken(universityStudent);
	                    
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
				
				else if(studentType.equals("INDIVIDUAL")) {
					
					// Login the user and create the user object
					IndividualStudent individualStudent = individualStudentDao.loginUser(credential, password);
					
					// Check if user is successfully logged in or not
					boolean isUserLoggedIn = individualStudent.isLoggedIn();
					
					if(isUserLoggedIn) {
	                    res.getWriter().write("Individual student logged in successfully!");
	                    
	                    // If user logged in successfully then generate the access token
	                    String accessToken = jwt.generateStudentAccessToken(
	                    		individualStudent.getEmail(),
	                    		individualStudent.getRole().toString(),
	                    		individualStudent.getStudentType().toString(),
	                    		Map.of("username", individualStudent.getUsername(),
	                    				"universityName", individualStudent.getUniversityName(),
	                    				"department", individualStudent.getDepartment().toString(),
	                    				"specialization", individualStudent.getSpecialization().toString())		
	                    );
	                    
	                    // Generate refresh token
	                    String refreshToken = jwt.generateRefreshToken(individualStudent.getEmail());
	                    
	                    // Set the refresh token in the UniversityStudent object
	                    individualStudent.setRefreshToken(refreshToken);
	                    
	                    // Optionally, update the student in the database here
	                    individualStudentDao.updateRefreshToken(individualStudent);
	                    
	                    // Send tokens back to client
	                    res.getWriter().write("Access Token: " + accessToken + ", Refresh Token: " + refreshToken);
	                

					}
					else {
	                    throw new ApiError(401, "Invalid university email or student ID, or password!");
	                }
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
