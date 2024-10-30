package com.lms.dao;

import com.lms.models.Faculty;
import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;



public class FacultyDao {

    // Register a faculty member
    public boolean registerFaculty(Faculty faculty) throws SQLException, ClassNotFoundException, DuplicateEntryException {
    	
    	// Step 1: Check if the user already exists
	    String credential = (faculty.getEmail() != null && !faculty.getEmail().isEmpty())
	        ? faculty.getEmail()
	        : faculty.getFacultyId();

	    boolean facultyExists = findByEmailOrId(credential);
	    
	    if (facultyExists) {
	        throw new DuplicateEntryException("User already exist with this email or FacultyId!", null);
	    }
	    
	    
        // Step 1: Prepare the query with fields in correct sequence as per database table
        final String INSERT_FACULTY = "INSERT INTO faculty (role, facultyId, fullname, email, password, universityName, department, specialization) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(INSERT_FACULTY);
        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(INSERT_FACULTY)) {

            // Step 3: Set the placeholder with actual values/binding values in the correct order
            st.setString(1, faculty.getRole().name());             // role
            st.setString(2, faculty.getFacultyId());               // facultyId
            st.setString(3, faculty.getFullname());                // fullname
            st.setString(4, faculty.getEmail());                   // email
            st.setString(5, faculty.getPassword());                // password
            st.setString(6, faculty.getUniversityName());          // universityName
            st.setString(7, faculty.getDepartment().name());       // department
            st.setString(8, faculty.getSpecialization().name());   // specialization
            

            // Step 4: Execute the prepared statement
            int rowsAffected = st.executeUpdate();

            // Step 5: Check if insertion was successful
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert into database!");
            }

            // Step 6: Return the result
            return rowsAffected > 0;

        } catch (SQLException e) {
            // SQLState '23' indicates integrity constraint violations
            if (e.getSQLState().startsWith("23")) {
                throw new DuplicateEntryException("Faculty ID or Email ID already exists!", e);
            }
            e.printStackTrace();
            throw new SQLException("Failed to connect to the database!" + e.getMessage(), e);
        }
    }
    
    
    // Check if user already present or registered
 	public boolean findByEmailOrId (String emailOrId) throws SQLException, ClassNotFoundException {
 		
 		// Step 1: Prepare the query
 		final String findFaculty = "SELECT COUNT(*) FROM faculty WHERE facultyId = ? OR email = ?";
 		
 		// Step 2: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(findFaculty)) {
 			
 			// Step 3: Setting the placeholder with actual values
 			st.setString(1, emailOrId);
 			st.setString(2, emailOrId);
 			
 			// Step 4: Execute the query and store the result into result set
 			ResultSet rs = st.executeQuery();
 			
 			// Step 5: Checking if user exist or not
 			if(rs.next()) {
 				return rs.getInt(1) > 0;
 			}
 		} catch (SQLException e) {
 			throw new SQLException("Failed to connect to the database!", e.getMessage());
 		}
 		
 		return false;
 	}
 	
    // Retrieve faculty info
 	public Faculty getByfacultyIdOrEmail(String facultyIdOrEmail) throws Exception {
 		
 		// Step 1: Prepare the query
 		final String getFaculty = "SELECT * FROM faculty WHERE facultyId = ? OR email = ?";
 		
 		// Step 2: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(getFaculty)) {
 			
 			// Step 3: Setting up the placeholders with actual values
 			st.setString(1, facultyIdOrEmail);
 			st.setString(2, facultyIdOrEmail);
 			
 			// Step 4: Execute the query and store the results into result set
 			ResultSet rs = st.executeQuery();
 			
 			// Step 5: If resultset exist retrieve the student details
 			if(rs.next()) {
 				
 				Faculty faculty = new Faculty();
 				faculty.setRole(Role.valueOf(rs.getString("role")));
 				faculty.setFacultyId(rs.getString("facultyId"));	
 				faculty.setFullname(rs.getString("fullname"));
 				faculty.setEmail(rs.getString("email"));
 				faculty.setUniversityName(rs.getString("universityName"));
 				faculty.setDepartment(Department.valueOf(rs.getString("department")));
 				faculty.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
 				faculty.setRefreshToken(rs.getString("refreshToken"));
 				
 				// Step 6: Return the faculty object
 				return faculty;
 			}
 			else {
                 throw new Exception("Faculty doesn't exist with this email or id!");
 			}
 			
 		} catch (SQLException e) {
 			throw new SQLException("Failed to connect to the database!", e);
 		}
 	}
 	
    // Login the faculty
 	public Faculty loginFaculty(String emailOrId, String password) throws Exception {
 		
 		// Step 1: Check if faculty exist or not
 		boolean isUserExist = findByEmailOrId(emailOrId);
 		
 		if(!isUserExist) {
 			throw new Exception("User doesn't exist with this email or Id!");
 		}
 		
 		// Step 2: Prepare the query
 		final String findUserDetails = "SELECT * FROM faculty WHERE (email = ? OR facultyId = ?)";
 				
 		// Step 3: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(findUserDetails)) {
 			
 			// Step 4: Setting up the placeholder with actual values
 			st.setString(1, emailOrId);
 			st.setString(2, emailOrId);
 			
 			// Step 5: Execute the query and store the result into result set
 			ResultSet rs = st.executeQuery();			
 			
 			// Step 6: If the user is found create the user model and validate the password
 			if(rs.next()) {
 				
 				Faculty faculty = new Faculty();				
 				faculty.setRole(Role.valueOf(rs.getString("role")));
 				faculty.setFacultyId(rs.getString("facultyId"));	
 				faculty.setFullname(rs.getString("fullname"));
 				faculty.setEmail(rs.getString("email"));
 				faculty.setUniversityName(rs.getString("universityName"));
 				faculty.setDepartment(Department.valueOf(rs.getString("department")));
 				faculty.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
 				
 				
 				// Step 7: Get the password from the result set
 				String hashedPassword = rs.getString("password");
 				
 				// Step 8: Validate the password
 				boolean isPasswordCorrect = faculty.verifyPassword(password, hashedPassword);
 				
 				// Step 9: If password matches return the university student object
 				if(isPasswordCorrect) {
 					// Set login status related fields
 					faculty.setLoggedIn(true);
 					faculty.setUpdatedAt(LocalDateTime.now());
 					
 					// Update the logged in field in the db
 					final String updateLoginStatus = "UPDATE faculty SET isLoggedIn = ?, updated_at = ? WHERE email = ? OR facultyId = ?";
 					
 					try(PreparedStatement updateStatus = conn.prepareStatement(updateLoginStatus)) {
 						
 						// Setting the palceholders with acutal values
 						updateStatus.setBoolean(1, faculty.isLoggedIn());
 						updateStatus.setTimestamp(2, Timestamp.valueOf(faculty.getUpdatedAt()));
 						updateStatus.setString(3, faculty.getFacultyId());
 						updateStatus.setString(4, faculty.getEmail());
 						
 						
 						// Execute the query
 						updateStatus.executeUpdate();
 						
 					}
 				    catch (SQLException e) {
 				    	throw new SQLException("Failed to update login status", e);
 			        }
 					
 					return faculty;
 				}
 				else {
 	                throw new Exception("Incorrect password. Please try again!");
 				}	
 			}
 			else {
                 throw new Exception("Faculty doesn't exist with this email or id!");
 			}
 			
 		} catch (SQLException e) {
 			throw new SQLException("Failed to connect to the database!", e);
 		}
 	}
 	
	// Update the refreshToken field in database
	public void updateRefreshToken(Faculty faculty) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String updateRefreshToken = "UPDATE faculty SET refreshToken = ?, updated_at = ? WHERE email = ? OR facultyId = ?";
		
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(updateRefreshToken)) {
			
			// Step 3: Setting up placeholders with actual values
			st.setString(1, faculty.getRefreshToken());
			st.setTimestamp(2, Timestamp.valueOf(faculty.getUpdatedAt()));
			st.setString(3, faculty.getEmail());
			st.setString(4, faculty.getFacultyId());
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("Refresh token updated successfully.");
            } else {
                System.out.println("No faculty found with the given email or id.");
            }
				
		} catch (SQLException e) {
			throw new SQLException("Failed to update refresh token!");
		}
	}
	
	// logout the student
	public void logout(String emailOrId) throws SQLException, ClassNotFoundException {
		
		// Step 1: Preapare the query
		String faculty = "UPDATE faculty SET refreshToken = NULL, isLoggedIn = FALSE WHERE email = ? OR facultyId = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(faculty)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, emailOrId);
			st.setString(2, emailOrId);
			
			// Step 4: Execute the prepared statement
			int rowsAffected = st.executeUpdate();
			// Step 5: Validate if database operation performed or not
			if (rowsAffected > 0) {
                System.out.println("faculty logout successfully.");
            } else {
                System.out.println("User not found with this faculty id or email.");
            }
				
		} catch (SQLException e) {
			System.err.println("SQL State: " + e.getSQLState());
	        System.err.println("Error Code: " + e.getErrorCode());
			throw new SQLException("Failed to logout faculty!", e);
		}
	}
	

}
