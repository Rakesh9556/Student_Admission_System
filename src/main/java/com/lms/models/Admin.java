package com.lms.models;

import java.util.List;

import com.lms.models.constants.Department;
import com.lms.models.constants.Role;
import com.lms.models.constants.Specialization;



public class Admin extends User {
    private String adminId;
    private String adminLevel;
    private String refreshToken;
    private List<String> permissions;
    
    public Admin() {
    	super();
    }

    // Constructor
    public Admin(Role role, String adminId, String adminLevel, String fullname, String email, String password, String universityName, Department department,
                 Specialization specialization) {
        super(role, fullname, email, password, universityName, department, specialization);
        this.setAdminId(adminId);
        this.setAdminLevel(adminLevel);
        this.setPermissions(permissions);
    }

    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        if (adminId == null || adminId.trim().isEmpty()) {
            throw new IllegalArgumentException("Admin ID cannot be empty");
        }
        this.adminId = adminId;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        if (adminLevel == null || adminLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("Admin Label cannot be empty");
        }
        this.adminLevel = adminLevel;
    }
    
    // Added Getter and Setter for refreshToken
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            throw new IllegalArgumentException("Permissions list cannot be empty");
        }
        this.permissions = permissions;
    }
}