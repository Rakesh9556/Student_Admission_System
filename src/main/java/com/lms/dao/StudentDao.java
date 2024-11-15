package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.lms.models.Student;
import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

public class StudentDao {
	
	// Register a University Student
	public boolean registerStudent(Student student) throws ClassNotFoundException, SQLException, DuplicateEntryException {
		
		// Step 1: Check if the user already exists
	    String credential = (student.getStudentId() != null && !student.getStudentId().isEmpty())
	        ? student.getStudentId()
	        : student.getUniversityEmail();

	    boolean isStudentExist = findByIdOrEmail(credential);
	    
	    if (isStudentExist) {
	        throw new DuplicateEntryException("User already exist with this Student Id or email!", null);
	    }
	    
		// Step1: Prepare the query
		final String INSERT_STUDENTS = "INSERT INTO students (role, fullname, email, password, studentId, universityName, department, specialization, universityEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step2: Establish connection and create prepared statement obj to execute query 
		// connection will be automatically closed after the try block ends
		try (Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(INSERT_STUDENTS)) {
			
			
		    // Step3: Setting the placeholder with actual values / binding values
			st.setString(1, student.getRole().name());
			st.setString(3, student.getFullname());
			st.setString(4, student.getEmail());
			st.setString(5, student.getPassword());
			st.setString(6, student.getStudentId());
			st.setString(7, student.getUniversityName());
			st.setString(8, student.getDepartment().name());
			st.setString(9, student.getSpecialization().name());
			st.setString(10,student.getUniversityEmail());
			
			
		    // Step4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 5: Validate if database operation performed or not
			if(rowsAffected == 0) {
				throw new SQLException("Failed to insert into database");
			}
			
			// Step 6: Return the result
			return rowsAffected > 0;
	
		} catch (SQLException e) {
			// SQLState '23' indicates integrity constraint violations
			if (e.getSQLState().startsWith("23")) {  
				throw new DuplicateEntryException("Email or student ID already exists!", e);
	        }
			throw new SQLException(e.getMessage(), e);
			
		}
			
	}
	
	
	// Check if user already present or registered
	public boolean findByIdOrEmail (String studentIdOrUniversityEmail) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String FIND_STUDENT = "SELECT COUNT(*) FROM students WHERE studentId = ? OR universityEmail = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(FIND_STUDENT)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, studentIdOrUniversityEmail);
			st.setString(2, studentIdOrUniversityEmail);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: Checking if multiple user exist or not
			if(rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
		
		return false;
	}
	
	
	// Retrieve student info
	public Student getByUsernameOrEmail(String studentIdOrUniversityEmail) throws Exception {
		
		// Step 1: Prepare the query
		final String GET_STUDENT = "SELECT * FROM students WHERE studentId = ? OR universityEmail = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(GET_STUDENT)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, studentIdOrUniversityEmail);
			st.setString(2, studentIdOrUniversityEmail);
			
			// Step 4: Execute the query and store the results into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: If resultset exist retrieve the student details
			if(rs.next()) {
				
				Student student = new Student();
				student.setRole(Role.valueOf(rs.getString("role")));
				student.setFullname(rs.getString("fullname"));
				student.setEmail(rs.getString("email"));
				student.setStudentId(rs.getString("studentId"));
				student.setUniversityName(rs.getString("universityName"));
				student.setDepartment(Department.valueOf(rs.getString("department")));
				student.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				student.setUniversityEmail(rs.getString("universityEmail"));
				student.setRefreshToken(rs.getString("refreshToken"));
				
				// Step 6: Return the student object
				return student;
			}
			else {
                throw new Exception("Student doesn't exist with this email or id!");
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	
	// Login the user
	public Student loginUser(String universityEmailOrStudentId, String password) throws Exception {
		
		// Step 1: Check if user exist or not
	    boolean isStudentExist = findByIdOrEmail(universityEmailOrStudentId);

		
		if(!isStudentExist) {
			throw new Exception("Student doesn't exist with this student id or email!");
		}
		
		// Step 2: Prepare the query
		final String FIND_STUDENT_DETAILS = "SELECT * FROM students WHERE (universityEmail = ? OR studentId = ?)";
		
		// Step 3: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(FIND_STUDENT_DETAILS)) {
			
			// Step 4: Setting up the placeholder with actual values
			st.setString(1, universityEmailOrStudentId);
			st.setString(2, universityEmailOrStudentId);
			
			// Step 5: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();
			
			
			// Step 6: If the user is found create the user model and validate the password
			if(rs.next()) {
				
				Student student = new Student();				
				student.setRole(Role.valueOf(rs.getString("role")));
				student.setFullname(rs.getString("fullname"));
				student.setEmail(rs.getString("email"));
				student.setStudentId(rs.getString("studentId"));
				student.setUniversityName(rs.getString("universityName"));
				student.setDepartment(Department.valueOf(rs.getString("department")));
				student.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				student.setUniversityEmail(rs.getString("universityEmail"));
				
				
				// Step 7: Get the password from the result set
				String hashedPassword = rs.getString("password");
				
				// Step 8: Validate the password
				boolean isPasswordCorrect = student.verifyPassword(password, hashedPassword);
				
				// Step 9: If password matches return the university student object
				if(isPasswordCorrect) {
					// Set login status related fields
					student.setLoggedIn(true);
					student.setUpdatedAt(LocalDateTime.now());
					
					// Update the logged in field in the db
					final String updateLoginStatus = "UPDATE students SET isLoggedIn = ?, updated_at = ? WHERE universityEmail = ? OR studentId = ?";
					
					try(PreparedStatement updateStatus = conn.prepareStatement(updateLoginStatus)) {
						
						// Setting the palceholders with acutal values
						updateStatus.setBoolean(1, student.isLoggedIn());
						updateStatus.setTimestamp(2, Timestamp.valueOf(student.getUpdatedAt()));
						updateStatus.setString(3, student.getUniversityEmail());
						updateStatus.setString(4, student.getStudentId());
						
						// Execute the query
						updateStatus.executeUpdate();
						
					}
				    catch (SQLException e) {
				    	throw new SQLException("Failed to update login status", e);
			        }
					
					return student;
				}
				else {
	                throw new Exception("Incorrect password. Please try again!");
				}	
			}
			else {
                throw new Exception("Student doesn't exist with this email or id!");
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	
	// Update the refreshToken field in database
	public void updateRefreshToken(Student student) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String UPDATE_REFRESH_TOKEN = "UPDATE students SET refreshToken = ?, updated_at = ? WHERE universityEmail = ? OR studentId = ?";
		
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(UPDATE_REFRESH_TOKEN)) {
			
			// Step 3: Setting up placeholders with actual values
			st.setString(1, student.getRefreshToken());
			st.setTimestamp(2, Timestamp.valueOf(student.getUpdatedAt()));
			st.setString(3, student.getUniversityEmail());
			st.setString(4, student.getStudentId());
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("Refresh token updated successfully.");
            } else {
                System.out.println("No students found with the studenty id or university email.");
            }
				
		} catch (SQLException e) {
			throw new SQLException("Failed to update refresh token!");
		}	
	}
	
	// logout the student
	public void logout(String universityEmailOrStudentId) throws SQLException, ClassNotFoundException {
		
		// Step 1: Preapare the query
		String INVALIDATE_STUDENT = "UPDATE students SET refreshToken = NULL, isLoggedIn = FALSE WHERE universityEmail = ? OR studentId = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(INVALIDATE_STUDENT)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, universityEmailOrStudentId);
			st.setString(2, universityEmailOrStudentId);
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("Student logout successfully.");
            } else {
                System.out.println("Student does not exist with this studenty id or university email.");
            }
				
		} catch (SQLException e) {
			System.err.println("SQL State: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
			throw new SQLException("Failed to logout student!");
		}
	}
}




