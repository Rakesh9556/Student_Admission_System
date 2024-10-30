package com.lms.dao;

import com.lms.models.Admin;
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


public class AdminDao {

    private String hashPassword(String password) {
        return password;
    }



    // Register an admin
    public boolean registerAdmin(Admin admin) throws SQLException, ClassNotFoundException, DuplicateEntryException {
        
    	// Step 1: Check if the user already exists
	    String credential = (admin.getAdminId() != null && !admin.getAdminId().isEmpty())
	        ? admin.getAdminId()
	        : admin.getEmail();

	    boolean adminExists = findByEmailOrId(credential);
	    
	    if (adminExists) {
	        throw new DuplicateEntryException("Admin already exist with this id or email!", null);
	    }
	    
    	final String INSERT_ADMIN = "INSERT INTO admin (role, adminId, adminLevel, fullname, email, password, universityName, department, specialization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(INSERT_ADMIN)) {

            st.setString(1, admin.getRole().name());
            st.setString(2, admin.getAdminId());
            st.setString(3, admin.getAdminLevel());
            st.setString(4, admin.getFullname());
            st.setString(5, admin.getEmail());
            st.setString(6, hashPassword(admin.getPassword()));
            st.setString(7, admin.getUniversityName());
            st.setString(8, admin.getDepartment().name());
            st.setString(9, admin.getSpecialization().name());
            

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert admin into the database.");
            }
            return true;

        }
        catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new DuplicateEntryException("Admin ID or Email ID already exists!", e);
            }
            e.printStackTrace(); // Log detailed error information
            throw new SQLException("Failed to register the admin. Error: " + e.getMessage(), e);
        }

    }


 	
    // Login Admin
 	public Admin loginAdmin(String emailOrId, String password) throws Exception {
 		
 		// Step 1: Check if user exist or not
 		boolean isUserExist = findByEmailOrId(emailOrId);
 		
 		if(!isUserExist) {
 			throw new Exception("Admin doesn't exist with this student id or email!");
 		}
 		
 		// Step 2: Prepare the query
        final String FIND_ADMIN = "SELECT * FROM admin WHERE (adminId = ? OR email = ?)";
 		
 		// Step 3: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(FIND_ADMIN)) {
 			
 			// Step 4: Setting up the placeholder with actual values
 			st.setString(1, emailOrId);
 			st.setString(2, emailOrId);
 			
 			// Step 5: Execute the query and store the result into result set
 			ResultSet rs = st.executeQuery();
 			
 			
 			// Step 6: If the user is found create the user model and validate the password
 			if(rs.next()) {
 				
 				 Admin admin = new Admin();
                 admin.setRole(Role.valueOf(rs.getString("role")));
                 admin.setFullname(rs.getString("fullname"));
                 admin.setEmail(rs.getString("email"));
                 admin.setAdminId(rs.getString("adminId"));
                 admin.setAdminLevel(rs.getString("adminLevel"));
                 admin.setUniversityName(rs.getString("universityName"));
                 admin.setDepartment(Department.valueOf(rs.getString("department")));
                 admin.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
 				
 				// Step 7: Get the password from the result set
 				String hashedPassword = rs.getString("password");
 				
 				// Step 8: Validate the password
 				boolean isPasswordCorrect = admin.verifyPassword(password, hashedPassword);
 				
 				// Step 9: If password matches return the university student object
 				if(isPasswordCorrect) {
 					// Set login status related fields
 					admin.setLoggedIn(true);
 					admin.setUpdatedAt(LocalDateTime.now());
 					
 					// Update the logged in field in the db
 					final String updateLoginStatus = "UPDATE admin SET isLoggedIn = ?, updated_at = ? WHERE Email = ? OR AdminId = ?";
 					
 					try(PreparedStatement updateStatus = conn.prepareStatement(updateLoginStatus)) {
 						
 						// Setting the palceholders with acutal values
 						updateStatus.setBoolean(1, admin.isLoggedIn());
 						updateStatus.setTimestamp(2, Timestamp.valueOf(admin.getUpdatedAt()));
 						updateStatus.setString(3, admin.getEmail());
 						updateStatus.setString(4, admin.getAdminId());
 						
 						// Execute the query
 						updateStatus.executeUpdate();
 						
 					}
 				    catch (SQLException e) {
 				    	throw new SQLException("Failed to update login status", e);
 			        }
 					
 					return admin;
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

    
    // New Method: Update refresh token in the database
    public void updateRefreshToken(Admin admin) throws SQLException, ClassNotFoundException {
        final String UPDATE_REFRESH_TOKEN = "UPDATE admin SET refreshToken = ? WHERE adminId = ?";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(UPDATE_REFRESH_TOKEN)) {

            st.setString(1, admin.getRefreshToken());
            st.setString(2, admin.getAdminId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update refresh token for admin.");
            }
        }
    }
    

    // Check if Admin already present or registered
 	public boolean findByEmailOrId (String emailOrId) throws SQLException, ClassNotFoundException {
 		
 		// Step 1: Prepare the query
 		final String FIND_ADMIN = "SELECT COUNT(*) FROM admin WHERE email = ? OR adminId = ?";
 		
 		// Step 2: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(FIND_ADMIN)) {
 			
 			// Step 3: Setting the placeholder with actual values
 			st.setString(1, emailOrId);
 			st.setString(2, emailOrId);
 			
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
 	

    // Retrieve an Admin object by admin ID
    public Admin getByEmailOrId(String emailOrId) throws Exception {
        final String GET_ADMIN = "SELECT * FROM admin WHERE email = ? OR adminId = ?";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(GET_ADMIN)) {

            st.setString(1, emailOrId);
            st.setString(2, emailOrId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
            	
                Admin admin = new Admin();
                admin.setRole(Role.valueOf(rs.getString("role")));
                admin.setAdminId(rs.getString("adminId"));
                admin.setAdminLevel(rs.getString("adminLevel"));
                admin.setFullname(rs.getString("fullname"));
                admin.setEmail(rs.getString("email"));
                admin.setUniversityName(rs.getString("universityName"));
                admin.setDepartment(Department.valueOf(rs.getString("department")));
                admin.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
                admin.setRefreshToken(rs.getString("refreshToken"));
                
                return admin;
            }
            else {
                throw new Exception("Admin doesn't exist with this email or id!");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving admin by ID: " + e.getMessage());
            throw new SQLException("Failed to retrieve admin by ID.", e);
        }
    }
    
    // logout the Admin
 	public void logout(String emailOrId) throws SQLException, ClassNotFoundException {
 		
 		// Step 1: Preapare the query
 		String ADMIN = "UPDATE admin SET refreshToken = NULL, isLoggedIn = FALSE WHERE email = ? OR adminId = ?";
 		
 		// Step 2: Establish the connection
 		try(Connection conn = DbConnect.getConnnection();
 				PreparedStatement st = conn.prepareStatement(ADMIN)) {
 			
 			// Step 3: Setting up the placeholders with actual values
 			st.setString(1, emailOrId);
 			st.setString(2, emailOrId);
 			
 			// Step 4: Execute the prepared statement
 			int rowsAffected = st.executeUpdate();
 			// Step 5: Validate if database operation performed or not
 			if (rowsAffected > 0) {
                 System.out.println("Admin logout successfully.");
             } else {
                 System.out.println("User not found with this Admin id or email.");
             }
 				
 		} catch (SQLException e) {
 			System.err.println("SQL State: " + e.getSQLState());
 	        System.err.println("Error Code: " + e.getErrorCode());
 			throw new SQLException("Failed to logout by Admin!", e);
 		}
 	}
}
