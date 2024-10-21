package com.lms.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public abstract class User {
	private Role role;
	private String fullname;
	private String email;
	private String password;
	private String universityName;
	private Department department;
	private Specialization specialization;
	private String avatar;
	private List<String> notifications;
	private boolean isLoggedIn;
	private LocalDate createdAt;
	private LocalDateTime updatedAt;
	
	
	
	// Constructor
	public User(Role role, String fullname, String email, String password, String universityName, Department department, Specialization specialization) {
		this.setRole(role);
		this.setFullname(fullname);
		this.setEmail(email);
		this.setPassword(password);
		this.setUniversityName(universityName);
		this.setDepartment(department);
		this.setSpecialization(specialization);
		this.isLoggedIn = false;
		this.createdAt = LocalDate.now();
		this.updatedAt = LocalDateTime.now();
		// we will handle notification here later
	}
	
	public User() {
	    this.role = Role.STUDENT; 
	    this.fullname = ""; 
	    this.email = ""; 
	    this.password = ""; 
	    this.universityName = "";
	    this.department = null; 
	    this.specialization = null; 
	 }

	

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be empty");
	    }
		this.role = role;
	}
	
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		if(fullname == null || fullname.trim().isEmpty()) {
			throw new IllegalArgumentException("Full name cannot be empty");
		}
		this.fullname = fullname;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Email is invalid");
		}
		this.email = email;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password == null || password.trim().isEmpty() || password.trim().length() < 8) {
			throw new IllegalArgumentException("Password must be atleast 8 characters");
		}
		this.password = hashPassword(password);
	}
	
	
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		if(universityName == null || universityName.trim().isEmpty()) {
			throw new IllegalArgumentException("University name cannot be empty");
		}
		this.universityName = universityName;
	}
	
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		if(department == null) {
			throw new IllegalArgumentException("Department cannot be empty");
		}
		this.department = department;
	}
	
	
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		if(specialization == null) {
			throw new IllegalArgumentException("Department cannot be empty");
		}
		this.specialization = specialization;
	}
	
	
	public List<String> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<String> notifications) {
		this.notifications = notifications;
	}
	
	
	// we will set avatar after successful login
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		if(isLoggedIn) {
			this.avatar = avatar;
			this.updatedAt = LocalDateTime.now();
		}
		else {
			throw new IllegalArgumentException("User must be logged in");
		}
		
	}
	
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}	
	
	
	// Password util methods
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
	
	public boolean verifyPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password.trim(), hashedPassword);
	}
	
}


