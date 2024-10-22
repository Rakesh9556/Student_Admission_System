package com.lms.models;

import java.time.LocalDate;
import java.util.List;

public class Faculty extends User {
	 private String facultyId;
	 private LocalDate joiningDate;
	 private List<String> permissions;
	 
	 
	 public Faculty() {
		super(); 
	 }
	
	// Constructor 
	public Faculty(Role role, String fullname, String email, String password, String universityName,Department department,
	Specialization specialization, String facultyId, LocalDate joiningDate, List<String> permissions)
	{
		        super(role, fullname, email, password, universityName, department, specialization);
		        this.setFacultyId(facultyId);
		        this.setJoiningDate(joiningDate);
		        this.setPermissions(permissions);
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
	
	public LocalDate getJoiningDate() {
	    return joiningDate;
	}
	
	public void setJoiningDate(LocalDate joiningDate) {
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