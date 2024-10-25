package com.lms.models;

import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;
import com.lms.models.constants.StudentType;

public class UniversityStudent extends Student  {
	private String studentId;
	private String universityEmail;
	
	// No-argument constructor
    public UniversityStudent() {
        super(); 
    }
    
    
	public UniversityStudent(Role role, StudentType studentType, String fullname, String email, String password, String studentId, String universityName, Department department,
			Specialization specialization, String universityEmail) {
		super( role, studentType, fullname, email, password, universityName, department, specialization);
		this.setStudentId(studentId);
		this.setUniversityEmail(universityEmail);
	}	
	

	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		if(studentId == null || studentId.trim().isEmpty() || studentId.length() > 12) {
			throw new IllegalArgumentException("Invalid student id (must be 12 digit)!");
		}
		this.studentId = studentId;
	}
	public String getUniversityEmail() {
		return universityEmail;
		
	}
	public void setUniversityEmail(String universityEmail) {
		if(universityEmail == null || universityEmail.trim().isEmpty() || !universityEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Email is invalid");
		}
		this.universityEmail = universityEmail;
	}
	
	
}
