package com.lms.servlet;

import com.lms.dao.AdminDao;
import com.lms.models.Admin;
import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.util.ApiError;
import com.lms.util.DuplicateEntryException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/adminRegister")
public class AdminRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Step 1: Create an instance of AdminDao to access its methods (register, find, etc.)
    private AdminDao adminDao;

    public AdminRegistrationServlet() {
        // Step 2: Initialize the dao
        this.adminDao = new AdminDao();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.getWriter().append("Served at: ").append(req.getContextPath());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Step 3: Retrieve data/parameters from request (all data is coming from requests)
        try {
            // Ensure user selects the role first
            String role = req.getParameter("role");

            // Validate if the user selected its role or not
            if (role == null || role.trim().isEmpty()) {
                throw new ApiError(400, "Role is required!");
            }

            role = role.toUpperCase();

            // Admin Registration
            if (role.equals("ADMIN")) {
                // Step 2: Define the fields for the admin
            	String adminId = req.getParameter("adminId");
            	String adminLabel = req.getParameter("adminLabel");
                String fullname = req.getParameter("fullname");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String universityName = req.getParameter("universityName");
                String department = req.getParameter("department");
                String specialization = req.getParameter("specialization");
//                String permissions = req.getParameter("permissions");

                // Step 3: Validate all the fields
                if (adminId == null || adminId.trim().isEmpty()) {
                    throw new ApiError(400, "Admin ID is required!");
                }

                if (adminLabel == null || adminLabel.trim().isEmpty()) {
                    throw new ApiError(400, "Admin Label is required!");
                }
                if (fullname == null || fullname.trim().isEmpty()) {
                    throw new ApiError(400, "Full name is required!");
                }

                if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    throw new ApiError(400, "Invalid email address!");
                }

                if (password == null || password.trim().isEmpty()) {
                    throw new ApiError(400, "Password is required!");
                }

                if (universityName == null || universityName.trim().isEmpty()) {
                    throw new ApiError(400, "University name is required!");
                }

                if (department == null) {
                    throw new ApiError(400, "Department is required!");
                }

                if (specialization == null) {
                    throw new ApiError(400, "Specialization is required!");
                }

//                if (permissions == null || permissions.trim().isEmpty()) {
//                    throw new ApiError(400, "Permissions are required!");
//                }

                // Step 4: Validate enum fields and parse permissions
                try {
                    Role roleEnum = Role.valueOf(role);
                    Department departmentEnum = Department.valueOf(department.toUpperCase());
                    Specialization specializationEnum = Specialization.valueOf(specialization.toUpperCase());

                    // Parse permissions from comma-separated string
//                    List<String> permissionsList = Arrays.asList(permissions.split(","));

                    // Step 5: Create the Admin object
                    Admin admin = new Admin();
                    admin.setRole(roleEnum);
                    admin.setAdminId(adminId);
                    admin.setAdminLabel(adminLabel);
                    admin.setFullname(fullname);
                    admin.setEmail(email);
                    admin.setPassword(password);
                    admin.setUniversityName(universityName);
                    admin.setDepartment(departmentEnum);
                    admin.setSpecialization(specializationEnum);
//                    admin.setPermissions(permissions);

                    // Step 6: Check if admin already exists with email or admin ID
                    boolean isExist = adminDao.findAdmin(admin.getAdminId(), admin.getEmail());
                    if (isExist) {
                        throw new DuplicateEntryException("User already exists with this email or admin ID!", null);
                    }

                    // Step 7: Register the admin
                    boolean isRegistered = adminDao.registerAdmin(admin);

                    // Step 8: Check if admin is successfully registered
                    if (isRegistered) {
                        res.getWriter().write("Admin registered successfully!");
                    } else {
                        throw new ApiError(500, "Failed to register admin!");
                    }
                } catch (IllegalArgumentException e) {
                    throw new ApiError(400, "Invalid department or specialization!");
                }
            } else {
                throw new ApiError(400, "Invalid role selected!");
            }
        } catch (ApiError e) {
            res.setStatus(e.getStatusCode());
            res.getWriter().write(e.getMessage());
        } catch (Exception e) {
            res.getWriter().write(e.getMessage());
        }
    }
}
