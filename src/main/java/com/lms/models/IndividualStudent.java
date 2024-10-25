package com.lms.models;

import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;
import com.lms.models.constants.StudentType;

public class IndividualStudent extends Student {
	private String username;
	
	
	// No-argument constructor
    public IndividualStudent() {
        super(); 
    }

	public IndividualStudent(Role role, StudentType studentType, String username, String fullname, String email,
			String password, String universityName, Department department, Specialization specialization) {
		super( role, studentType, fullname, email, password, universityName, department, specialization);
		this.setUsername(username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		if(!username.matches("^[a-zA-Z0-9_.]{3,12}$")) {
			throw new IllegalArgumentException("Username must be 3-12 characters and can include letters, digits, '_', and '.'!");		
		}
		this.username = username;
	}
	
}