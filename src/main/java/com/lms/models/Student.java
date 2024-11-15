package com.lms.models;

import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;

public class Student extends User{
	private String studentId;
	private String universityEmail;
	private boolean isEnrolled;
	
	
	public Student() {
        super(); // Call the no-argument constructor of the User class (if it exists)
    }
	
	public Student(Role role,String studentId, String fullname, String email, String password, String universityName, Department department, Specialization specialization, String universityEmail) {
		super(role, fullname, email, password, universityName, department, specialization);
		this.setStudentId(studentId);
		this.setUniversityEmail(universityEmail);
		this.setEnrolled(isEnrolled);
		
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
	
	
	public boolean isEnrolled() {
		return isEnrolled;
	}
	public void setEnrolled(boolean isEnrolled) {
		this.isEnrolled = isEnrolled;
	}
	
	
}
