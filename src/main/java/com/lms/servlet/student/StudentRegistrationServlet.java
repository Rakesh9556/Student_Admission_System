//package com.lms.servlet.registration;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//import com.lms.dao.IndividualStudentDao;
//import com.lms.dao.UniversityStudentDao;
//import com.lms.models.IndividualStudent;
//import com.lms.models.UniversityStudent;
//import com.lms.models.constants.Department;
//import com.lms.models.constants.Role;
//import com.lms.models.constants.Specialization;
//import com.lms.models.constants.StudentType;
//import com.lms.util.ApiError;
//
//@WebServlet("/student/register")
//public class StudentRegistrationServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	// Step 1: Create an instance of UniversityStudentDao to access its methods (register, login, etc)
//	private IndividualStudentDao individualStudentDao;
//	private UniversityStudentDao universityStudentDao;
//	
//	
//    public StudentRegistrationServlet() {
//    	// Step 2: Initialize the dao
//        this.individualStudentDao = new IndividualStudentDao();
//        this.universityStudentDao = new UniversityStudentDao();
//    }
//
//    
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		res.getWriter().append("Served at: ").append(req.getContextPath());
//	}
//
//	
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		
//		// Step 3: Retrieve data/parameters from request (all data is coming from requests)
//		try {
//			
//			// Ensure user select the role first
//			String role = req.getParameter("role");
//			
//			// validate if the user select its role or not
//			if(role == null || role.trim().isEmpty()) {
//				throw new ApiError(400, "Role is required!");
//			}
//			
//			role = role.toUpperCase();
//			
//			// University Student Registration 
//			// if role exist then check if the user is student or not
//			if(role.equals("STUDENT")) {
//				
//				// Ensure student select its role (individual or University)
//				String studentType = req.getParameter("studentType");
//				
//				// validate if the student select its type or not
//				if(studentType == null  || studentType.trim().isEmpty()) {
//					throw new ApiError(400, "Student type is required!");
//				}
//				
//				studentType = studentType.toUpperCase();
//				
//				// if student role exist then check if the student is individual or university student
//				if(studentType.equals("UNIVERSITY")) {
//					// define the fields for the university student
//					String firstname = req.getParameter("firstname");
//					String lastname = req.getParameter("lastname");
//					String email = req.getParameter("email");
//					String password = req.getParameter("password");
//					String universityName = req.getParameter("universityName");
//					String studentId = req.getParameter("studentId");
//					String universityEmail = req.getParameter("universityEmail");
//					String department = req.getParameter("department");
//					String specialization = req.getParameter("specialization");
//					
//					
//					// validate all the fields
//					if(firstname == null || firstname.trim().isEmpty()) {
//						throw new ApiError(400, "First name is required!");
//					}
//					
//					if(lastname == null || lastname.trim().isEmpty()) {
//						throw new ApiError(400, "Last name is required!");
//					}
//					
//					if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//						throw new ApiError(400, "Invalid email address!");
//					}
//					
//					if(password == null || password.trim().isEmpty()) {
//						throw new ApiError(400, "Password is required!");
//					}
//					
//					if(universityName == null || universityName.trim().isEmpty()) {
//						throw new ApiError(400, "University name is required!");
//					}
//					
//					if(studentId == null || studentId.trim().isEmpty()) {
//						throw new ApiError(400, "Student id is required!");
//					}
//					
//					if(universityEmail == null || universityEmail.trim().isEmpty()) {
//						throw new ApiError(400, "University mail id is required!");
//					}
//					
//					if(department == null) {
//						throw new ApiError(400, "Department is required!");
//					}
//					
//					if(specialization == null) {
//						throw new ApiError(400, "Specialization is required!");
//					}
//					
//
//					
//					// Validating the enum fields and registering the student
//					try {
//						Role roleEnum = Role.valueOf(role);
//						StudentType studentTypeEnum = StudentType.valueOf(studentType.toUpperCase());
//					    Department departmentEnum = Department.valueOf(department.toUpperCase());
//					    Specialization specializationEnum = Specialization.valueOf(specialization.toUpperCase());
//
//					    // Create the UniversityStudent object
//					    UniversityStudent universityStudent = new UniversityStudent();
//					    universityStudent.setRole(roleEnum);
//					    universityStudent.setStudentType(studentTypeEnum);
//					    universityStudent.setFullname(firstname + " " + lastname);
//					    universityStudent.setEmail(email);
//					    universityStudent.setPassword(password);
//					    universityStudent.setStudentId(studentId);
//					    universityStudent.setUniversityName(universityName);
//					    universityStudent.setDepartment(departmentEnum);
//					    universityStudent.setSpecialization(specializationEnum); 
//					    universityStudent.setUniversityEmail(universityEmail);
//					    universityStudent.setUpdatedAt(LocalDateTime.now());
//					    
//					    
//					    // Testing data
////					    System.out.println(universityStudent.getRole());
////					    System.out.println(universityStudent.getStudentType());
////					    System.out.println(universityStudent.getFullname());
////					    System.out.println(universityStudent.getEmail());
////					    System.out.println(universityStudent.getPassword());
////					    System.out.println(universityStudent.getStudentId());
////					    System.out.println(universityStudent.getUniversityName());
////					    System.out.println(universityStudent.getDepartment());
////					    System.out.println(universityStudent.getSpecialization());
////					    System.out.println(universityStudent.getUniversityEmail());
//					    
//					    
//					    // Calling the DAO class method to register the university student
//					    boolean isRegistered = universityStudentDao.registerStudent(universityStudent);
//
//					    if (isRegistered) {
//					        res.getWriter().write("University student registered successfully!");
//					    } else {
//					        throw new ApiError(500, "Failed to register university student!");
//					    }
//					} catch (IllegalArgumentException e) {
//					    throw new ApiError(400, "Invalid department or specialization!");
//					}
//				} 
//				// Handle registration for individual student
//				else if(studentType.equals("INDIVIDUAL")) {
//					
//					// Step 1: Define the fields for the individual student
//					String username = req.getParameter("username");
//					String firstname = req.getParameter("firstname");
//					String lastname = req.getParameter("lastname");
//					String email = req.getParameter("email");
//					String password = req.getParameter("password");
//					String universityName = req.getParameter("universityName");
//					String department = req.getParameter("department");
//					String specialization = req.getParameter("specialization");
//					
//					// Step 2: Validate all the fields
//					if(username == null || username.trim().isEmpty()) {
//						throw new ApiError(400, "Username is required!");
//					}
//					
//					if(firstname == null || firstname.trim().isEmpty()) {
//						throw new ApiError(400, "First name is required!");
//					}
//					
//					if(lastname == null || lastname.trim().isEmpty()) {
//						throw new ApiError(400, "Last name is required!");
//					}
//					
//					if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//						throw new ApiError(400, "Invalid email address!");
//					}
//					
//					if(password == null || password.trim().isEmpty()) {
//						throw new ApiError(400, "Password is required!");
//					}
//					
//					if(universityName == null || universityName.trim().isEmpty()) {
//						throw new ApiError(400, "University name is required!");
//					}
//					
//					if(department == null) {
//						throw new ApiError(400, "Department is required!");
//					}
//					
//					if(specialization == null) {
//						throw new ApiError(400, "Specialization is required!");
//					}
//					
//					try {
//						// Step 3: Validating the enum field 
//						Role roleEnum = Role.valueOf(role.toUpperCase());
//						StudentType studentTypeEnum = StudentType.valueOf(studentType.toUpperCase());
//						Department departmentEnum = Department.valueOf(department.toUpperCase());
//						Specialization specializationEnum = Specialization.valueOf(specialization.toUpperCase());
//						
//						// Step 4: Create the individual student object
//						IndividualStudent individualStudent = new IndividualStudent();
//						individualStudent.setRole(roleEnum);
//						individualStudent.setStudentType(studentTypeEnum);
//						individualStudent.setUsername(username);
//					    individualStudent.setFullname(firstname + " " + lastname);
//						individualStudent.setEmail(email);
//						individualStudent.setPassword(password);
//						individualStudent.setUniversityName(universityName);
//						individualStudent.setDepartment(departmentEnum);
//						individualStudent.setSpecialization(specializationEnum);
//						individualStudent.setUpdatedAt(LocalDateTime.now());
//						
//						// Testing data
////					    System.out.println(individualStudent.getRole());
////					    System.out.println(individualStudent.getStudentType());
////					    System.out.println(individualStudent.getUsername());
////					    System.out.println(individualStudent.getFullname());
////					    System.out.println(individualStudent.getEmail());
////					    System.out.println(individualStudent.getPassword());
////					    System.out.println(individualStudent.getUniversityName());
////					    System.out.println(individualStudent.getDepartment());
////					    System.out.println(individualStudent.getSpecialization());
//					    
//						
//						
//						// Step 5: Register the individual student
//						boolean isRegistered = individualStudentDao.registerStudent(individualStudent);
//						
//						// Step 6: Check if student is successfully registered or not
//						 if (isRegistered) {
//							 res.getWriter().write("Individual student registered successfully!");
//						 } 
//						 else {
//							 throw new ApiError(500, "Failed to register individual student!");
//						 }	 
//					} 
//					catch (IllegalArgumentException e) {
//					    throw new ApiError(400, "Invalid department or specialization!");
//					}	
//				}	
//				else {
//                    throw new ApiError(400, "Invalid student type selected");
//				}
//			}
//			
//		} catch (ApiError e) {
//			res.setStatus(e.getStatusCode());
//			res.getWriter().write(e.getMessage());
//		} catch (Exception e) {
//			res.getWriter().write(e.getMessage());
//		}
//		
//	}
//
//}



