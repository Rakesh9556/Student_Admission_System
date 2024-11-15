package com.lms.models;

public class Guardian {
	
	// Step 1; Define the attributes
	private Long guardianId;
    private String name;
    private String relationship;
    private String phone;
    private String email;
    private String occupation;

    // Step 2: Design the constructor
    public Guardian(String name, String relationship, String phone, String email, String occupation) {
    	this.setName(name);
        this.setRelationship(relationship);
        this.setPhone(phone);
        this.setEmail(email);
        this.setOccupation(occupation);
    }

    // Step 3: Design the getters and setters and add proper validations
    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        if (guardianId == null || guardianId <= 0) {
            throw new IllegalArgumentException("Guardian id cannot be empty!");
        }
        this.guardianId = guardianId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Guardian name cannot be empty!");
        }
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        if (relationship == null || relationship.trim().isEmpty()) {
            throw new IllegalArgumentException("Guardian relationship cannot be empty!");
        }
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format!");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email address!");
        }
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        if (occupation == null || occupation.trim().isEmpty()) {
            throw new IllegalArgumentException("Guardian occupation cannot be empty!");
        }
        this.occupation = occupation;
    }
}
