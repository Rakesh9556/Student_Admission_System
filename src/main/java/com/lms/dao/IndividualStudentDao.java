package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.lms.models.IndividualStudent;
import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;
import com.lms.models.constants.StudentType;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

public class IndividualStudentDao {
	
	// Register an individual student
	public boolean registerStudent(IndividualStudent individualStudent) throws SQLException, ClassNotFoundException, DuplicateEntryException {
		
		// Step 1: Check if the user already exists
	    String credential = (individualStudent.getUsername() != null && !individualStudent.getUsername().isEmpty())
	        ? individualStudent.getUsername()
	        : individualStudent.getEmail();

	    boolean userExists = findUser(credential);
	    
	    if (userExists) {
	        throw new DuplicateEntryException("User already exist with this email or username!", null);
	    }
	    
		// Step 2: Prepare the query
		final String INSERT_INDIVIDUAL_STUDENTS = "INSERT into individual_students (role, studentType, username, fullname, email, password, universityName, department, specialization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step 3: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(INSERT_INDIVIDUAL_STUDENTS)){
			
			// Step 4: Setting the placeholder with actual values/ binding values
			st.setString(1, individualStudent.getRole().name());
			st.setString(2, individualStudent.getStudentType().name());
			st.setString(3, individualStudent.getUsername());
			st.setString(4, individualStudent.getFullname());
			st.setString(5, individualStudent.getEmail());
			st.setString(6, individualStudent.getPassword());
			st.setString(7, individualStudent.getUniversityName());
			st.setString(8, individualStudent.getDepartment().name());
			st.setString(9, individualStudent.getSpecialization().name());
			
			// Step 5: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 6: Validate if database operation performed or not
			if(rowsAffected == 0) {
				throw new SQLException("Failed to insert into database!");
			}
			
			// Step 7: Return the result
			return rowsAffected > 0;
			
		} catch (SQLException e) {
			// SQLState '23' indicates integrity constraint violations
			if (e.getSQLState().startsWith("23")) {  
	            throw new DuplicateEntryException("Username or Email ID already exists!", e);
	        }
			throw new SQLException("Failed to connect to the database!", e.getMessage());
		}
	}
	
	
	// Check if user already present or registered
	public boolean findUser (String emailOrUsername) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the query
		final String findUser = "SELECT COUNT(*) FROM individual_students WHERE username = ? OR email = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUser)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, emailOrUsername);
			st.setString(2, emailOrUsername);
			
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
	public IndividualStudent getByUsernameOrEmail(String usernameOrEmail) throws Exception {
		
		// Step 1: Prepare the query
		final String getUser = "SELECT * FROM individual_students WHERE username = ? OR email = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(getUser)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, usernameOrEmail);
			st.setString(2, usernameOrEmail);
			
			// Step 4: Execute the query and store the results into result set
			ResultSet rs = st.executeQuery();
			
			// Step 5: If resultset exist retrieve the student details
			if(rs.next()) {
				
				IndividualStudent individualStudent = new IndividualStudent();
				individualStudent.setRole(Role.valueOf(rs.getString("role")));
				individualStudent.setStudentType(StudentType.valueOf(rs.getString("studentType")));	
				individualStudent.setUsername("username");
				individualStudent.setFullname(rs.getString("fullname"));
				individualStudent.setEmail(rs.getString("email"));
				individualStudent.setUniversityName(rs.getString("universityName"));
				individualStudent.setDepartment(Department.valueOf(rs.getString("department")));
				individualStudent.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				individualStudent.setRefreshToken(rs.getString("refreshToken"));
				
				// Step 6: Return the student object
				return individualStudent;
			}
			else {
                throw new Exception("Student doesn't exist with this email or id!");
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	
	// Login the user
	public IndividualStudent loginUser(String emailOrUsername, String password) throws Exception {
		
		// Step 1: Check if user exist or not
		boolean isUserExist = findUser(emailOrUsername);
		
		if(!isUserExist) {
			throw new Exception("User doesn't exist with this email or username!");
		}
		
		// Step 2: Prepare the query
		final String findUserDetails = "SELECT * FROM individual_students WHERE (email = ? OR username = ?)";
				
		// Step 3: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(findUserDetails)) {
			
			// Step 4: Setting up the placeholder with actual values
			st.setString(1, emailOrUsername);
			st.setString(2, emailOrUsername);
			
			// Step 5: Execute the query and store the result into result set
			ResultSet rs = st.executeQuery();			
			
			// Step 6: If the user is found create the user model and validate the password
			if(rs.next()) {
				
				IndividualStudent individualStudent = new IndividualStudent();				
				individualStudent.setRole(Role.valueOf(rs.getString("role")));
				individualStudent.setStudentType(StudentType.valueOf(rs.getString("studentType")));	
				individualStudent.setUsername("username");
				individualStudent.setFullname(rs.getString("fullname"));
				individualStudent.setEmail(rs.getString("email"));
				individualStudent.setUniversityName(rs.getString("universityName"));
				individualStudent.setDepartment(Department.valueOf(rs.getString("department")));
				individualStudent.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
				
				
				// Step 7: Get the password from the result set
				String hashedPassword = rs.getString("password");
				
				// Step 8: Validate the password
				boolean isPasswordCorrect = individualStudent.verifyPassword(password, hashedPassword);
				
				// Step 9: If password matches return the university student object
				if(isPasswordCorrect) {
					// Set login status related fields
					individualStudent.setLoggedIn(true);
					individualStudent.setUpdatedAt(LocalDateTime.now());
					
					// Update the logged in field in the db
					final String updateLoginStatus = "UPDATE individual_students SET isLoggedIn = ?, updated_at = ? WHERE email = ? OR username = ?";
					
					try(PreparedStatement updateStatus = conn.prepareStatement(updateLoginStatus)) {
						
						// Setting the palceholders with acutal values
						updateStatus.setBoolean(1, individualStudent.isLoggedIn());
						updateStatus.setTimestamp(2, Timestamp.valueOf(individualStudent.getUpdatedAt()));
						updateStatus.setString(3, individualStudent.getEmail());
						updateStatus.setString(4, individualStudent.getUsername());
						
						// Execute the query
						updateStatus.executeUpdate();
						
					}
				    catch (SQLException e) {
				    	throw new SQLException("Failed to update login status", e);
			        }
					
					return individualStudent;
				}
				else {
	                throw new Exception("Incorrect password. Please try again!");
				}	
			}
			else {
                throw new Exception("User doesn't exist with this email or id!");
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	
	// Update the refreshToken field in database
	public void updateRefreshToken(IndividualStudent individualStudent) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String updateRefreshToken = "UPDATE individual_students SET refreshToken = ?, updated_at = ? WHERE email = ? OR username = ?";
		
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(updateRefreshToken)) {
			
			// Step 3: Setting up placeholders with actual values
			st.setString(1, individualStudent.getRefreshToken());
			st.setTimestamp(2, Timestamp.valueOf(individualStudent.getUpdatedAt()));
			st.setString(3, individualStudent.getEmail());
			st.setString(4, individualStudent.getUsername());
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("Refresh token updated successfully.");
            } else {
                System.out.println("No user found with the given username or email.");
            }
				
		} catch (SQLException e) {
			throw new SQLException("Failed to update refresh token!");
		}
	}
	
	
	// logout the student
	public void logout(String emailOrUsername) throws SQLException, ClassNotFoundException {
		
		// Step 1: Preapare the query
		String invalidateStudent = "UPDATE individual_students SET refreshToken = NULL, isLoggedIn = FALSE WHERE email = ? OR username = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(invalidateStudent)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, emailOrUsername);
			st.setString(2, emailOrUsername);
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("Student logout successfully.");
            } else {
                System.out.println("User not found with this studenty id or university email.");
            }
				
		} catch (SQLException e) {
			System.err.println("SQL State: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
			throw new SQLException("Failed to logout student!", e);
		}
	}
		
}
