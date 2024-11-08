package com.lms.models.constants;

public enum Specialization {

    // Specializations for Computer Science Branch
    DATA_SCIENCE("Data Science", Branch.COMPUTER_SCIENCE),
    ARTIFICIAL_INTELLIGENCE("Artificial Intelligence", Branch.COMPUTER_SCIENCE),
    SOFTWARE_ENGINEERING("Software Engineering", Branch.COMPUTER_SCIENCE),
    CYBER_SECURITY("Cyber Security", Branch.COMPUTER_SCIENCE),

    // Specializations for Mechanical Engineering Branch
    THERMAL_ENGINEERING("Thermal Engineering", Branch.MECHANICAL_ENGINEERING),
    MACHINE_DESIGN("Machine Design", Branch.MECHANICAL_ENGINEERING),
    AUTOMOBILE_ENGINEERING("Automobile Engineering", Branch.MECHANICAL_ENGINEERING),

    // Specializations for Civil Engineering Branch
    STRUCTURAL_ENGINEERING("Structural Engineering", Branch.CIVIL_ENGINEERING),
    ENVIRONMENTAL_ENGINEERING("Environmental Engineering", Branch.CIVIL_ENGINEERING),

    // Specializations for Nursing Branch
    PEDIATRIC_NURSING("Pediatric Nursing", Branch.NURSING_SCIENCE),
    GERIATRIC_NURSING("Geriatric Nursing", Branch.NURSING_SCIENCE),

    // Specializations for Pharmacy Branch
    CLINICAL_PHARMACY("Clinical Pharmacy", Branch.PHARMACEUTICS),
    PHARMACEUTICAL_CHEMISTRY("Pharmaceutical Chemistry", Branch.PHARMACEUTICS),

    // Specializations for MBA Branch
    STRATEGIC_MANAGEMENT("Strategic Management", Branch.MARKETING),
    INTERNATIONAL_BUSINESS("International Business", Branch.MARKETING),

    // Specializations for BCom Branch
    FINANCIAL_ACCOUNTING("Financial Accounting", Branch.ACCOUNTING),
    MANAGEMENT_ACCOUNTING("Management Accounting", Branch.ACCOUNTING),

    // Specializations for Science Branches
    ASTROPHYSICS("Astrophysics", Branch.PHYSICS),
    ORGANIC_CHEMISTRY("Organic Chemistry", Branch.CHEMISTRY);

    private final String description;
    private final Branch branch;

    Specialization(String description, Branch branch) {
        this.description = description;
        this.branch = branch;
    }

    public String getDescription() {
        return description;
    }

    public Branch getBranch() {
        return branch;
    }
}
