package com.lms.models;

import java.util.List;



public class Admin extends User {
    private String adminId;
    private String adminLabel;
    private List<String> permissions;
    
    public Admin() {
    	super();
    }

    // Constructor
    public Admin(Role role, String adminId, String adminLabel, String fullname, String email, String password, String universityName, Department department,
                 Specialization specialization, List<String> permissions) {
        super(role, fullname, email, password, universityName, department, specialization);
        this.setAdminId(adminId);
        this.setAdminLabel(adminLabel);
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

    public String getAdminLabel() {
        return adminLabel;
    }

    public void setAdminLabel(String adminLabel) {
        if (adminLabel == null || adminLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("Admin Label cannot be empty");
        }
        this.adminLabel = adminLabel;
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
