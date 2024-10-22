package com.lms.dao;

import com.lms.models.Faculty;
import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FacultyDao {

    // Register a faculty member
    public boolean registerFaculty(Faculty faculty) throws SQLException, ClassNotFoundException, DuplicateEntryException {

        // Step 1: Prepare the query with fields in correct sequence as per database table
        final String INSERT_FACULTY = "INSERT INTO faculty (role, facultyId, fullname, email, password, universityName, department, specialization, joiningDate, permissions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(INSERT_FACULTY)) {

            // Step 3: Set the placeholder with actual values/binding values in the correct order
            st.setString(1, faculty.getRole().name());             // role
            st.setString(2, faculty.getFacultyId());               // facultyId
            st.setString(3, faculty.getFullname());                // fullname
            st.setString(4, faculty.getEmail());                   // email
            st.setString(5, faculty.getPassword());                // password
            st.setString(6, faculty.getUniversityName());          // universityName
            st.setString(7, faculty.getDepartment().name());       // department
            st.setString(8, faculty.getSpecialization().name());   // specialization
            st.setDate(9, Date.valueOf(faculty.getJoiningDate())); // joiningDate
            st.setString(10, String.join(",", faculty.getPermissions()));  // permissions

            // Step 4: Execute the prepared statement
            int rowsAffected = st.executeUpdate();

            // Step 5: Check if insertion was successful
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert into database!");
            }

            // Step 6: Return the result
            return rowsAffected > 0;

        } catch (SQLException e) {
            // SQLState '23' indicates integrity constraint violations
            if (e.getSQLState().startsWith("23")) {
                throw new DuplicateEntryException("Faculty ID or Email ID already exists!", e);
            }
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }
    }

    // Check if a faculty member already exists
    public boolean findFaculty(String facultyId, String email) throws SQLException, ClassNotFoundException {

        // Step 1: Prepare the query
        final String FIND_FACULTY = "SELECT COUNT(*) FROM faculty WHERE facultyId = ? OR email = ?";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(FIND_FACULTY)) {

            // Step 3: Set the placeholder with actual values
            st.setString(1, facultyId);
            st.setString(2, email);

            // Step 4: Execute the query and store the result into result set
            ResultSet rs = st.executeQuery();

            // Step 5: Check if the faculty exists
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }

        return false;
    }

    // Login faculty member
    public Faculty loginFaculty(String facultyIdOrEmail, String password) throws SQLException, ClassNotFoundException {

        // Step 1: Prepare the query with fields in correct sequence
        final String LOGIN_FACULTY = "SELECT * FROM faculty WHERE (facultyId = ? OR email = ?) AND password = ?";

        // Step 2: Establish the connection
        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(LOGIN_FACULTY)) {

            // Step 3: Set the placeholder with actual values
            st.setString(1, facultyIdOrEmail);
            st.setString(2, facultyIdOrEmail);
            st.setString(3, password);

            // Step 4: Execute the query and store the result into result set
            ResultSet rs = st.executeQuery();

            // Step 5: If the faculty is found, create a Faculty object
            if (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setRole(Role.valueOf(rs.getString("role")));                       // role
                faculty.setFacultyId(rs.getString("facultyId"));                           // facultyId
                faculty.setFullname(rs.getString("fullname"));                             // fullname
                faculty.setEmail(rs.getString("email"));                                   // email
                faculty.setPassword(rs.getString("password"));                             // password
                faculty.setUniversityName(rs.getString("universityName"));                 // universityName
                faculty.setDepartment(Department.valueOf(rs.getString("department")));     // department
                faculty.setSpecialization(Specialization.valueOf(rs.getString("specialization"))); // specialization
                faculty.setJoiningDate(rs.getDate("joiningDate").toLocalDate());           // joiningDate
                faculty.setPermissions(List.of(rs.getString("permissions").split(",")));   // permissions

                // Set login status related fields
                faculty.setLoggedIn(true);
                faculty.setUpdatedAt(LocalDateTime.now());

                // Step 6: Return the faculty object
                return faculty;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database!", e.getMessage());
        }

        return null;
    }
}
