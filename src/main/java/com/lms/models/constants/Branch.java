package com.lms.models.constants;

public enum Branch {

    // Engineering Programs - BTech
    COMPUTER_SCIENCE("Computer Science and Engineering", Program.BTECH),
    INFORMATION_TECHNOLOGY("Information Technology", Program.BTECH),
    MECHANICAL_ENGINEERING("Mechanical Engineering", Program.BTECH),
    CIVIL_ENGINEERING("Civil Engineering", Program.BTECH),
    ELECTRICAL_ENGINEERING("Electrical Engineering", Program.BTECH),
    CHEMICAL_ENGINEERING("Chemical Engineering", Program.BTECH),
    BIOTECHNOLOGY("Biotechnology", Program.BTECH),
    AERONAUTICAL_ENGINEERING("Aeronautical Engineering", Program.BTECH),
    AUTOMOBILE_ENGINEERING("Automobile Engineering", Program.BTECH),

    // Engineering Programs - MTech
    DATA_SCIENCE("Data Science", Program.MTECH),
    MACHINE_DESIGN("Machine Design", Program.MTECH),
    THERMAL_ENGINEERING("Thermal Engineering", Program.MTECH),
    STRUCTURAL_ENGINEERING("Structural Engineering", Program.MTECH),
    BIOMEDICAL_ENGINEERING("Biomedical Engineering", Program.MTECH),

    // Management Programs - MBA
    MARKETING("Marketing", Program.MBA),
    FINANCE("Finance", Program.MBA),
    HUMAN_RESOURCES("Human Resources", Program.MBA),
    OPERATIONS_MANAGEMENT("Operations Management", Program.MBA),

    // Commerce Programs - BCom, MCom
    ACCOUNTING("Accounting", Program.BCOM),
    BUSINESS_LAW("Business Law", Program.BCOM),
    FINANCIAL_MARKETS("Financial Markets", Program.BCOM),
    TAXATION("Taxation", Program.BCOM),
    CORPORATE_FINANCE("Corporate Finance", Program.MCOM),
    AUDITING("Auditing", Program.MCOM),

    // Medical Programs - MBBS, Nursing, Pharmacy
    GENERAL_MEDICINE("General Medicine", Program.MBBS),
    PEDIATRICS("Pediatrics", Program.MBBS),
    NURSING_SCIENCE("Nursing Science", Program.NURSING),
    PHARMACEUTICS("Pharmaceutics", Program.BPHARM),
    
    // Science Programs - BSc, MSc
    PHYSICS("Physics", Program.BSC),
    CHEMISTRY("Chemistry", Program.BSC),
    MATHEMATICS("Mathematics", Program.BSC),
    BOTANY("Botany", Program.BSC),
    ZOOLOGY("Zoology", Program.BSC),
    MICROBIOLOGY("Microbiology", Program.MSC),
    BIOCHEMISTRY("Biochemistry", Program.MSC),
    ENVIRONMENTAL_SCIENCE("Environmental Science", Program.MSC);

    private final String description;
    private final Program program;

    Branch(String description, Program program) {
        this.description = description;
        this.program = program;
    }

    public String getDescription() {
        return description;
    }

    public Program getProgram() {
        return program;
    }
}
