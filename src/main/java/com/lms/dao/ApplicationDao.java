package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lms.models.Application;
import com.lms.models.constants.ApplicationStatus;
import com.lms.models.constants.Branch;
import com.lms.models.constants.Program;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

public class ApplicationDao {
	
	public boolean createApplication(Application application) throws SQLException, ClassNotFoundException, DuplicateEntryException {
		
		// Step 1: Check if application already exists or not
	    String credential = (application.getPhone() != null && application.getPhone().length() == 10)
	        ? application.getPhone() : application.getApplicationId().toString();

	    boolean isApplicationExists = findApplicationByIdOrPhone(credential);
		
	    if (isApplicationExists) {
	        throw new DuplicateEntryException("Application already exist with this application id or phone number!", null);
	    }
	    
		// Step 2: Prepare the sql query
		final String CREATE_APPLICATION = "INSERT into applications(applicationId, fullName, email, phone, password, address, enrollProgram, enrollBranch, submissionDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step 3: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(CREATE_APPLICATION)) {
			
			// Step 4: Setting the placholders with actual values
			st.setLong(1, application.getApplicationId());
			st.setString(2, application.getFullName()); 
			st.setString(3, application.getEmail());
			st.setString(4, application.getPhone());
			st.setString(5, application.getPassword()); // password will be auto generated
			st.setString(6, application.getAddress());
			st.setString(7, application.getEnrolledProgram().toString());
			st.setString(8, application.getEnrolledBranch().toString());
			st.setDate(9, application.getSubmissionDate());
			
			// Step 5: Execute the prepared statement
			int isInserted = st.executeUpdate();

			// Step 6: Validate if database operation is performed or not
			if(isInserted == 0) {
				throw new SQLException("Failed to insert data!");
			}
			
			// Step 7: If data successfull[y inserted return 
			return isInserted > 0;
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	
	// Find the application by id or phone
	public boolean findApplicationByIdOrPhone(String applicationIdOrPhone) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String FIND_APPLICATION = "SELECT COUNT(*) FROM applications WHERE applicationId = ? OR phone = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(FIND_APPLICATION)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, applicationIdOrPhone);
			st.setString(2, applicationIdOrPhone);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet findApplication = st.executeQuery();
			
			// Step 5: Check if a application exist or not
			if(findApplication.next()) {
				return findApplication.getInt(1) > 0;
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
		return false;
	}
	
	// Get the application by id or phone
	public Application getApplicationByIdOrPhone(String applicationIdOrPhone) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String GET_APPLICATION = "SELECT * FROM applications WHERE applicationId = ? OR phone = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(GET_APPLICATION)) {
			
			// Step 3: Setting the placeholder with actual values
			st.setString(1, applicationIdOrPhone);
			st.setString(2, applicationIdOrPhone);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet getApplication = st.executeQuery();
			
			// Step 5: Check if a application exist or not
			if(getApplication.next()) {
				Application application = new Application();
				application.setApplicationId(getApplication.getLong("applicationId"));
				application.setFullName(getApplication.getString("fullName"));
				application.setPhone(getApplication.getString("phone"));
				application.setEnrolledProgram(Program.valueOf(getApplication.getString("enrollProgram")));
				application.setEnrolledBranch(Branch.valueOf(getApplication.getString("enrollBranch")));
				application.setStatus(ApplicationStatus.valueOf(getApplication.getString("status")));
				// application.setPaymentStatus(PaymentStatus.valueOf(getApplication.getString("paymentStatus")));
				return application;
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
		return null;
	}
	
	
	// get all application list
	public List<Application> getAllApplications() throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
	    final String GET_ALL_APPLICATIONS = "SELECT * FROM applications";
	    
	    // Step 2: Create a list to store the applications
	    List<Application> applications = new ArrayList<>();
	    
	    // Step 3: Establish the connection
	    try (Connection conn = DbConnect.getConnnection();
	         PreparedStatement st = conn.prepareStatement(GET_ALL_APPLICATIONS)) {
	        
	    	// Step 4: Execute the query
	        ResultSet getApplication = st.executeQuery();
	        
	        // Step 5: Retrieve the applications and store into arraylist
	        while (getApplication.next()) {
	            Application application = new Application();
				application.setApplicationId(getApplication.getLong("applicationId"));
				application.setFullName(getApplication.getString("fullName"));
				application.setEnrolledProgram(Program.valueOf(getApplication.getString("enrollProgram")));
				application.setEnrolledBranch(Branch.valueOf(getApplication.getString("enrollBranch")));
				application.setStatus(ApplicationStatus.valueOf(getApplication.getString("status")));
				// application.setPaymentStatus(PaymentStatus.valueOf(getApplication.getString("paymentStatus")));
	            applications.add(application);
	        }
	    } 
	    
	    catch (SQLException e) {
	        throw new SQLException("Failed to retrieve applications from the database!", e);
	    }
	    
	    return applications;
	}
	
	
	// Update application status
	public boolean updateApplicationStatus(Long applicationId, ApplicationStatus newApplicationStatus) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String UPDATE_APPLICATION = "UPDATE applications SET status = ? where applicationId = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(UPDATE_APPLICATION)) {
			
			// Step 3: Setting the placholder with actual values
			st.setString(1, newApplicationStatus.name());
			st.setLong(2, applicationId);
			
			// Step 4: Execute the query
			return st.executeUpdate() > 0;
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
	}
	
	// Approve application
	public boolean approveApplication(Long applicationId) throws ClassNotFoundException, SQLException {
		return updateApplicationStatus(applicationId, ApplicationStatus.APPROVED);
	}
	
	// Reject application
	public boolean rejectApplication(Long applicationId) throws ClassNotFoundException, SQLException {
		return updateApplicationStatus(applicationId, ApplicationStatus.REJECTED);
	}
	
	// Schedule Exam
	public boolean scheduleExam(Long applicationId) throws ClassNotFoundException, SQLException {
		return updateApplicationStatus(applicationId, ApplicationStatus.SCHEDULED);
	}

}
