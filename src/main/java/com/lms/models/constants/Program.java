package com.lms.models.constants;

public enum Program {
	
    // Engineering Department Programs
    BTECH("Bachelor of Technology", Department.ENGINEERING),
    MTECH("Master of Technology", Department.ENGINEERING),

    // Management Department Programs
    BBA("Bachelor of Business Administration", Department.MANAGEMENT),
    MBA("Master of Business Administration", Department.MANAGEMENT),

    // Commerce Department Programs
    BCOM("Bachelor of Commerce", Department.COMMERCE),
    MCOM("Master of Commerce", Department.COMMERCE),

    // Science Department Programs
    BSC("Bachelor of Science", Department.SCIENCE),
    MSC("Master of Science", Department.SCIENCE),

    // Arts Department Programs
    BA("Bachelor of Arts", Department.ARTS),
    MA("Master of Arts", Department.ARTS),

    // Medical Department Programs
    MBBS("Bachelor of Medicine, Bachelor of Surgery", Department.MEDICAL),
    BDS("Bachelor of Dental Surgery", Department.MEDICAL),
    BPHARM("Bachelor of Pharmacy", Department.MEDICAL),
	NURSING("Bachelor of Science in Nursing", Department.MEDICAL);
	

    private final String description;
    private final Department department;

    Program(String description, Department department) {
        this.description = description;
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public Department getDepartment() {
        return department;
    }
}
