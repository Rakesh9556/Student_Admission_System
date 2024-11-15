package com.lms.models;

import java.time.Year;


public class EducationRecord {

	// Step 1; Define the attributes
	private Long educationRecordId;
    private String institutionName;
    private String boardOrUniversity;
    private String qualification; 
    private int passingYear;
    private double marksObtained; 
    private double totalMarks; 

    // Step 2: Design the constructor
    public EducationRecord(String institutionName, String boardOrUniversity, String qualification, int passingYear, double marksObtained, double totalMarks) {
    	this.setInstitutionName(institutionName);
        this.setBoardOrUniversity(boardOrUniversity);
        this.setQualification(qualification);
        this.setPassingYear(passingYear);
        this.setMarksObtained(marksObtained);
        this.setTotalMarks(totalMarks);
    }

    // Step 3: Write the Getters and Setters and add proper validations
    public Long getEducationRecordId() {
        return educationRecordId;
    }

    public void setEducationRecordId(Long educationRecordId) {
    	if(educationRecordId == null || educationRecordId <= educationRecordId) {
    		throw new IllegalArgumentException("Education record id cannot be empty!");
    	}
        this.educationRecordId = educationRecordId;
    }
    
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
    	if(institutionName == null || institutionName.trim().isEmpty()) {
    		throw new IllegalArgumentException("Institution name cannot be empty!");
    	}
        this.institutionName = institutionName;
    }

    public String getBoardOrUniversity() {
        return boardOrUniversity;
    }

    public void setBoardOrUniversity(String boardOrUniversity) {
    	if(boardOrUniversity == null || boardOrUniversity.trim().isEmpty()) {
    		throw new IllegalArgumentException("Board cannot be empty!");
    	}
        this.boardOrUniversity = boardOrUniversity;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
    	if(qualification == null || qualification.trim().isEmpty()) {
    		throw new IllegalArgumentException("Institution name cannot be empty!");
    	}
        this.qualification = qualification;
    }

    public int getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(int passingYear) {
    	int currentYear = Year.now().getValue();
    	int minimumYear = currentYear - 6;
    	
    	if(passingYear <= 0) {
    		throw new IllegalArgumentException("Passing year cannot be zero or negative!");
    	}
    	else if(passingYear > currentYear) {
    		throw new IllegalArgumentException("Passing year cannot be greater than current year");
    	}
    	else if(passingYear < minimumYear) {
    		throw new IllegalArgumentException("Pasing year cannot be earlier than" + minimumYear);
    	}
        this.passingYear = passingYear;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
    	if(marksObtained <= 0) {
    		throw new IllegalArgumentException("Obtained mark cannot be zero or negative!");
    	}
        this.marksObtained = marksObtained;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
    	if(totalMarks <= 0) {
    		throw new IllegalArgumentException("Total marks cannot be zero or negative!");
    	}
        this.totalMarks = totalMarks;
    }
        
    public double getPercentage() {
        return calculatePercentage();
    }    
    
    // Calculate percentage
    public double calculatePercentage() {
        if (totalMarks > 0) {
            return (marksObtained / totalMarks) * 100;
        }
        return 0;
    }
}
