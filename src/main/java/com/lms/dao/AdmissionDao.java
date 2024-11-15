package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lms.models.Admission;
import com.lms.models.constants.AdmissionStatus;
import com.lms.models.constants.Branch;
import com.lms.models.constants.Department;
import com.lms.models.constants.Program;
import com.lms.util.DbConnect;

public class AdmissionDao {
	
	// submit admission form method
	public boolean subitAdmissionForm(Admission admission) throws SQLException, ClassNotFoundException {
		
		// Step 1: Preopare the sql query
		final String CREATE_ADMISSION_FORM = "INSERT INTO admission (admissionId, applicationId, fullName, dateOfBirth, gender, nationality, aadharNumber, phone, email, addressId, department, program, branch, admission_fee, photo_id, previous_marksheet, transfer_certificate, caste_certificate, income_certificate, aadhar_card) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(CREATE_ADMISSION_FORM)) {
			
			// Step 3: Setting the placeholder with the actual values
			st.setLong(1, admission.getAdmissionId());
			st.setLong(2, admission.getApplicationId());  // thi will come from application table
			st.setString(3, admission.getFullName());  // this will come from application table
			st.setDate(4, admission.getDateOfBirth());
			st.setString(5, admission.getGender());
			st.setString(6, admission.getNationality());
			st.setString(7, admission.getAadharNumber());
			st.setString(8, admission.getPhone());
			st.setString(9, admission.getEmail());
			st.setLong(10, admission.getAddressId());
			st.setString(11, admission.getDepartment().toString());
			st.setString(12, admission.getProgram().toString());
			st.setString(13, admission.getBranch().toString());
//			st.setDate(13, admission.getAdmissionDate());
//			st.setString(14, admission.getAdmissionStatus().toString()); // this could be PENDING by default
			st.setDouble(14, admission.getAdmissionFee());
//			st.setBoolean(16, admission.isFeePaid()); // this could be false by default
			st.setString(15, admission.getPhotoId());
			st.setString(16, admission.getPreviousMarksheet());
			st.setString(17, admission.getTransferCertificate());
			st.setString(18, admission.getCasteCertificate());
			st.setString(19, admission.getIncomeCertificate());
			st.setString(20, admission.getAadharCard());
			
			// Step 4: Execute the query
			int isInserted = st.executeUpdate();
			
			// Step 5: Validate if database operation is performed or not
			if(isInserted == 0) {
				throw new SQLException("Failed to insert data into the database!");
			}
			
			// Step 6: if data inserted successfully return true
			return isInserted > 0;
		}
		catch(SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
		
	}
	
	
	// Find admission form by id or phone
	public boolean findAdmissionByIdOrPhone(String admissionIdOrPhone) throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
		final String FIND_ADMISSION_FORM = "SELECT COUNT(*) FROM admission where admissionId = ? OR phone = ?";
		
		// Step 2: Establish the connection
		try(Connection conn = DbConnect.getConnnection();
				PreparedStatement st = conn.prepareStatement(FIND_ADMISSION_FORM)) {
			
			// Step 3: Setting up the placeholders with actual values
			st.setString(1, admissionIdOrPhone);
			st.setString(2, admissionIdOrPhone);
			
			// Step 4: Execute the query and store the result into result set
			ResultSet findAdmissionForm = st.executeQuery();
			
			// Step 5: Check if admission form exist or not
			if(findAdmissionForm.next()) {
				return findAdmissionForm.getInt(1) > 0;
			}
			
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to the database!", e);
		}
		return false;
	}
	
	
	// Retrive all admission forms
	public List<Admission> getAllAdmissions() throws SQLException, ClassNotFoundException {
		
		// Step 1: Prepare the sql query
	    final String GET_ALL_ADMISSIONS = "SELECT * FROM admission";
	    
	    // Step 2: Create a list to store the applications
	    List<Admission> admissionsList = new ArrayList<>();
	    
	    // Step 3: Establish the connection
	    try (Connection conn = DbConnect.getConnnection();
	         PreparedStatement st = conn.prepareStatement(GET_ALL_ADMISSIONS)) {
	        
	    	// Step 4: Execute the query
	        ResultSet getAdmission = st.executeQuery();
	        
	        // Step 5: Retrieve the applications and store into arraylist
	        while (getAdmission.next()) {
	        	Admission admission = new Admission();
	        	admission.setAdmissionId(getAdmission.getLong("admissionId"));
	        	admission.setApplicationId(getAdmission.getLong("applicationId"));
	        	admission.setFullName(getAdmission.getString("fullName"));
	        	admission.setDateOfBirth(getAdmission.getDate("dateOfBirth"));
	        	admission.setGender(getAdmission.getString("gender"));
	        	admission.setNationality(getAdmission.getString("nationality"));
	        	admission.setAadharNumber(getAdmission.getString("aadharNumber"));
	        	admission.setPhone(getAdmission.getString("phone"));
	        	admission.setEmail(getAdmission.getString("email"));
	        	admission.setAddressId(getAdmission.getLong("addressId"));
	        	admission.setDepartment(Department.valueOf(getAdmission.getString("department")));
	        	admission.setProgram(Program.valueOf(getAdmission.getString("program")));
	        	admission.setBranch(Branch.valueOf(getAdmission.getString("branch")));
	        	admission.setAdmissionStatus(AdmissionStatus.valueOf(getAdmission.getString("admission_status")));
	        	admission.setAdmissionFee(getAdmission.getDouble("admission_fee"));
	        	admission.setIsFeePaid(getAdmission.getBoolean("isFeePaid"));
	        	admission.setPhotoId(getAdmission.getString("photoId"));
	        	admission.setPreviousMarksheet(getAdmission.getString("previous_marksheet"));
	        	admission.setTransferCertificate(getAdmission.getString("transfer_certificate"));
	        	admission.setCasteCertificate(getAdmission.getString("caste_certificate"));
	        	admission.setIncomeCertificate(getAdmission.getString("income_certificate"));
	        	admission.setAadharCard(getAdmission.getString("aadhar_card"));
	        	
	        	// create teh list
	            admissionsList.add(admission);
	        }
	    } 
	    
	    catch (SQLException e) {
	        throw new SQLException("Failed to retrieve applications from the database!", e);
	    }
	    
	    return admissionsList;
	}


}
