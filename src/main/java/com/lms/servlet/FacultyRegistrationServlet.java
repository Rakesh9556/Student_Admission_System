package com.lms.servlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.lms.dao.FacultyDao;
import com.lms.models.Department;
import com.lms.models.Faculty;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.util.ApiError;
import com.lms.util.DuplicateEntryException;

@WebServlet("/facultyRegister")
public class FacultyRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Step 1: Create an instance of FacultyDao to access its methods (register, login, etc.)
    private FacultyDao facultyDao;

    public FacultyRegistrationServlet() {
        // Step 2: Initialize the dao
        this.facultyDao = new FacultyDao();
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

            // Faculty Registration
           // if role exist then check if the user is faculty or not
            if (role.equals("FACULTY")) {
                // Step 2: Define the fields for the faculty
            	 String facultyId = req.getParameter("facultyId");         // Faculty ID
                 String firstname = req.getParameter("firstname");         // First name
                 String lastname = req.getParameter("lastname");           // Last name
                 String email = req.getParameter("email");                 // Email
                 String password = req.getParameter("password");           // Password
                 String universityName = req.getParameter("universityName"); // University name
                 String department = req.getParameter("department");       // Department
                 String specialization = req.getParameter("specialization"); // Specialization
                 String joiningDate = req.getParameter("joiningDate");     // Joining date
                 String permissionsStr = req.getParameter("permissions");  // Permissions (comma-separated)

                 // Step 3: Validate all the fields
                 if (facultyId == null || facultyId.trim().isEmpty()) {
                     throw new ApiError(400, "Faculty ID is required!");
                 }
                 if (firstname == null || firstname.trim().isEmpty()) {
                     throw new ApiError(400, "First name is required!");
                 }
                 if (lastname == null || lastname.trim().isEmpty()) {
                     throw new ApiError(400, "Last name is required!");
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
                 if (joiningDate == null || joiningDate.trim().isEmpty()) {
                     throw new ApiError(400, "Joining date is required!");
                 }
                 if (permissionsStr == null || permissionsStr.trim().isEmpty()) {
                     throw new ApiError(400, "Permissions are required!");
                 }

                // Convert joiningDate to LocalDate
                LocalDate joiningDateParsed = LocalDate.parse(joiningDate);

                // Step 4: Validate enum fields and parse permissions
                try {
                    Role roleEnum = Role.valueOf(role);
                    Department departmentEnum = Department.valueOf(department.toUpperCase());
                    Specialization specializationEnum = Specialization.valueOf(specialization.toUpperCase());

                    // Parse permissions from comma-separated string
                    List<String> permissions = Arrays.asList(permissionsStr.split(","));

                    // Step 5: Create the Faculty object
                    Faculty faculty = new Faculty();
                    faculty.setRole(roleEnum);                                 // Set Role
                    faculty.setFacultyId(facultyId);                           // Set Faculty ID
                    faculty.setFullname(firstname + " " + lastname);           // Set Full Name
                    faculty.setEmail(email);                                   // Set Email
                    faculty.setPassword(password);                             // Set Password
                    faculty.setUniversityName(universityName);                 // Set University Name
                    faculty.setDepartment(departmentEnum);                     // Set Department
                    faculty.setSpecialization(specializationEnum);             // Set Specialization
                    faculty.setJoiningDate(joiningDateParsed);                 // Set Joining Date
                    faculty.setPermissions(permissions);  
                    //Faculty faculty = new Faculty(roleEnum, firstname + " " + lastname, email, password, universityName,
                    //    departmentEnum, specializationEnum, facultyId, joiningDateParsed, permissions);

                    // Step 6: Check if user already exists with email or faculty ID
                    boolean isExist = facultyDao.findFaculty(faculty.getEmail(), faculty.getFacultyId());
                    if (isExist) {
                        throw new DuplicateEntryException("User already exists with this email or faculty ID!", null);
                    }

                    // Step 7: Register the faculty
                    boolean isRegistered = facultyDao.registerFaculty(faculty);

                    // Step 8: Check if faculty is successfully registered
                    if (isRegistered) {
                        res.getWriter().write("Faculty registered successfully!");
                    } else {
                        throw new ApiError(500, "Failed to register faculty!");
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