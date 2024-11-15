package com.lms.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.lms.models.constants.AdmissionStatus;
import com.lms.models.constants.Branch;
import com.lms.models.constants.Department;
import com.lms.models.constants.Program;

public class Admission {

	// Step 1: Define the attributes
	
    // personal information
    private Long admissionId;
    private Long applicationId;
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String nationality;
    private String aadharNumber;

    // contact information
    private String phone;
    private String email;
    private Long addressId;


    // guradian infromation
    private List<Guardian> guardians;
    

    // eductional qualification information
    private List<EducationRecord> previousEducationDetails; 
    
    
    // enrolling program information
    private Department department;
    private Program program;
    private Branch branch;
    private Date admissionDate;
    private AdmissionStatus admissionStatus;
    private double admissionFee;
    private boolean isFeePaid;

    // document information
    private String photoId; 
    private String previousMarksheet; 
    private String transferCertificate; 
    private String casteCertificate; 
    private String incomeCertificate; 
    private String aadharCard;

   
    // Step 2: Design the constructors
    public Admission(Long admissionId, Long applicationId, String fullName, Date dateOfBirth, String gender, String nationality, String aadharNumber, String phone, String email, Department department, Program program, Branch branch,  Double admissionFee, String photoId, String previousMarksheet, String transferCertificate, String casteCertificate, String incomeCertificate, String aadharCard) {
        this.setAdmissionId(admissionId);
        this.setApplicationId(applicationId);
        this.setFullName(fullName);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
        this.setNationality(nationality);
        this.setAadharNumber(aadharNumber);
        this.setPhone(phone);
        this.setEmail(email);
        this.setDepartment(department);
        this.setProgram(program);
        this.setBranch(branch);
        this.admissionDate = new Date(System.currentTimeMillis());
        this.setAdmissionStatus(AdmissionStatus.PENDING);
        this.setAdmissionFee(admissionFee);
        this.setIsFeePaid(false);
        this.setPhotoId(photoId);
        this.setPreviousMarksheet(previousMarksheet);
        this.setTransferCertificate(transferCertificate);
        this.setCasteCertificate(casteCertificate);
        this.setIncomeCertificate(incomeCertificate);
        this.setAadharCard(aadharCard);
    }

    public Admission() {
        this.admissionDate = new Date(System.currentTimeMillis());
        this.setAdmissionStatus(AdmissionStatus.PENDING);
        this.setIsFeePaid(false);
	}

	// Step 3: Design the getter and setters and add proper validation
    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long admissionId) {
    	if(admissionId == null || admissionId <= 0) {
    		throw new IllegalArgumentException("Admission id cannot be empty or negative!");
    	}
        this.admissionId = admissionId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
    	if(applicationId == null || applicationId <= 0) {
    		throw new IllegalArgumentException("Application id cannot be empty or negative!");
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
    	if(dateOfBirth == null) {
    		throw new IllegalArgumentException(" Date of birth cannot be empty!");
    	}
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
    	if(gender == null || gender.trim().isEmpty()) {
    		throw new IllegalArgumentException("Gender cannot be empty!");
    	}
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
    	if(nationality == null || nationality.trim().isEmpty()) {
    		throw new IllegalArgumentException("Nationality cannot be empty!");
    	}
        this.nationality = nationality;
    }
    
    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
    	if(aadharNumber == null || aadharNumber.trim().isEmpty()) {
    		throw new IllegalArgumentException("Aadhar number cannot be empty!");
    	}
        this.aadharNumber = aadharNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
		if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Invalid email address!");	
		}
        this.email = email;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
		if(addressId == null) {
			throw new IllegalArgumentException("Address id cannot be empty!");
		}
        this.addressId = addressId;
    }

    public List<Guardian> getGuardians() {
        return guardians;
    }

    public void setGuardians(List<Guardian> guardians) {
        if (guardians == null) {
            this.guardians = new ArrayList<>();
        } else {
            this.guardians = guardians;
        }
    }

    public void addGuardian(Guardian guardian) {
        if (this.guardians == null) {
            this.guardians = new ArrayList<>();
        }
        this.guardians.add(guardian);
    }
    
    
    public List<EducationRecord> getPreviousEducationDetails() {
        return previousEducationDetails;
    }

    public void setPreviousEducationDetails(List<EducationRecord> previousEducationDetails) {
        this.previousEducationDetails = previousEducationDetails;
    }

    
    
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public AdmissionStatus getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(AdmissionStatus admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public double getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(double admissionFee) {
        if (admissionFee <= 0) {
            throw new IllegalArgumentException("Admission fee must be positive.");
        }
        this.admissionFee = admissionFee;
    }

    public boolean isFeePaid() {
        return isFeePaid;
    }

    public void setIsFeePaid(boolean isFeePaid) {
        this.isFeePaid = isFeePaid;
    }



    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPreviousMarksheet() {
        return previousMarksheet;
    }

    public void setPreviousMarksheet(String previousMarksheet) {
        this.previousMarksheet = previousMarksheet;
    }

    public String getTransferCertificate() {
        return transferCertificate;
    }

    public void setTransferCertificate(String transferCertificate) {
        this.transferCertificate = transferCertificate;
    }

    public String getCasteCertificate() {
        return casteCertificate;
    }

    public void setCasteCertificate(String casteCertificate) {
        this.casteCertificate = casteCertificate;
    }

    public String getIncomeCertificate() {
        return incomeCertificate;
    }

    public void setIncomeCertificate(String incomeCertificate) {
        this.incomeCertificate = incomeCertificate;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    // Complete Admission if fee is paid
    public void completeAdmission() {
        if (!isFeePaid) {
            throw new IllegalStateException("Admission cannot be completed until the fee is paid.");
        }
        this.admissionStatus = AdmissionStatus.COMPLETED;
    }


}
