package com.lms.models.constants;

public enum Department {
    ENGINEERING("Engineering"),
    SCIENCE("Science"),
    ARTS("Arts"),
    MANAGEMENT("Management Studies"),
    MEDICAL("Medical Sciences"),
    COMMERCE("Commerce");

    private final String description;

    Department(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
