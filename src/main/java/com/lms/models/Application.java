package com.lms.models;
import java.sql.Date;

import org.mindrot.jbcrypt.BCrypt;

import com.lms.models.constants.ApplicationStatus;
import com.lms.models.constants.Branch;
import com.lms.models.constants.PaymentStatus;
import com.lms.models.constants.Program;

public class Application {
	
	// Step1: Define the attributes
	private Long applicationId;
	private String fullName;
	private String email;
	private String phone;
	private String password;
	private String address;
	private Program enrollProgram;
	private Branch enrollBranch;
	private Date submissionDate;
	private ApplicationStatus status;
	private boolean isExamScheduled;
	private PaymentStatus paymentStatus; 
	
	// Step 2: Design the constructors
	public Application() {
		this.submissionDate = new Date(System.currentTimeMillis());
		this.status = ApplicationStatus.PENDING;
		this.isExamScheduled = false;
		this.paymentStatus = PaymentStatus.PENDING;
	}
	
	public Application(Long applicationId, String fullName, String email, String phone, String password, String address, Program enrollProgram, Branch enrollBranch) {
		this.setApplicationId(applicationId);
		this.setFullName(fullName);
		this.setEmail(email);
		this.setPhone(phone);
		this.setPassword(password);
		this.setAddress(address);
		this.setEnrolledProgram(enrollProgram);
		this.setEnrolledBranch(enrollBranch);
		this.submissionDate = new Date(System.currentTimeMillis());
		this.status = ApplicationStatus.PENDING;
		this.isExamScheduled = false;
		this.paymentStatus = PaymentStatus.PENDING;
	}
	
	// Step 3: Design the getters and setters
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		if(applicationId == null || applicationId <= 0) {
			throw new IllegalArgumentException("Invalid application id!");
		}
		this.applicationId = applicationId;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		if(fullName == null || fullName.trim().isEmpty()) {
			throw new IllegalArgumentException("Fullname cannot be empty!");
		}
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Invalid email address!");	
		}
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		if(phone == null || !phone.matches("^\\+91[789]\\d{9}$")) {
			throw new IllegalArgumentException("Invalid phone number! Use +91XXXXXXXXXX.");
		}
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		this.password = hashPassword(password);
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		if(address == null || address.trim().isEmpty()) {
			throw new IllegalArgumentException("Address cannot be empty!");
		}
		this.address = address;
	}
	
	public Program getEnrolledProgram() {
		return enrollProgram;
	}
	public void setEnrolledProgram(Program enrollProgram) {
		if(enrollProgram == null) {
			throw new IllegalArgumentException("Program cannot be empty!");
		}
		this.enrollProgram = enrollProgram;
	}
	
	public Branch getEnrolledBranch() {
		return enrollBranch;
	}
	public void setEnrolledBranch(Branch enrollBranch) {
		if(enrollBranch == null) {
			throw new IllegalArgumentException("Branch cannot be empty!");
		}
		this.enrollBranch = enrollBranch;
	}
	
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		if(status == null) {
			throw new IllegalArgumentException("Status cannot be empty!");
		}
		this.status = status;
	}
	
	public Date getSubmissionDate() {
		return submissionDate;
	}
	
	public boolean isExamScheduled() {
		return isExamScheduled;
	}
	public void scheduleExam() {
		if(!status.equals(ApplicationStatus.APPROVED)) {
			throw new IllegalArgumentException("Cannot schedule exam unless application is approved!");
		}
		this.isExamScheduled = true;
	}
	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	// Password util methods
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
	
	public boolean verifyPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password.trim(), hashedPassword);
	}
	
}
