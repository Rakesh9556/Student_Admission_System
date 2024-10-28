package com.lms.models;

import java.time.LocalDateTime;
import java.util.List;

import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;

public class Faculty extends User {
	 private String facultyId;
	 private LocalDateTime joiningDate;
	 private List<String> permissions;
	 
	 
	 public Faculty() {
		super(); 
	 }
	
	// Constructor 
	public Faculty(Role role, String fullname, String email, String password, String universityName,Department department,
	Specialization specialization, String facultyId) {
		        super(role, fullname, email, password, universityName, department, specialization);
		        this.setFacultyId(facultyId);
	}
	
	//Getters and Setters
	public String getFacultyId() {
	    return facultyId;
	}
	
	public void setFacultyId(String facultyId) {
	    if(facultyId == null || facultyId.trim().isEmpty()) {
	        throw new IllegalArgumentException("Faculty ID cannot be empty");
	    }
	    this.facultyId = facultyId;
	}
	
	public LocalDateTime getJoiningDate() {
	    return joiningDate;
	}
	
	public void setJoiningDate(LocalDateTime joiningDate) {
	    if(joiningDate == null) {
	        throw new IllegalArgumentException("Joining date cannot be empty");
	    }
	    this.joiningDate = joiningDate;
	}
	
	
	public List<String> getPermissions() {
	    return permissions;
	}
	
	public void setPermissions(List<String> permissions) {
	    if(permissions == null || permissions.isEmpty()) {
	        throw new IllegalArgumentException("Permissions list cannot be empty");
	    }
	    this.permissions = permissions;
	}
}