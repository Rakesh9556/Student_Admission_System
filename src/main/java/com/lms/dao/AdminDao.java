package com.lms.dao;

import com.lms.models.Admin;
import com.lms.models.Department;
import com.lms.models.Role;
import com.lms.models.Specialization;
import com.lms.util.DbConnect;
import com.lms.util.DuplicateEntryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao {

    private String hashPassword(String password) {
        return password;
    }

    // Register an admin
    public boolean registerAdmin(Admin admin) throws SQLException, ClassNotFoundException, DuplicateEntryException {
        final String INSERT_ADMIN = "INSERT INTO admins (role, adminId, adminLabel, fullname, email, password, universityName, department, specialization, permissions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(INSERT_ADMIN)) {

            st.setString(1, admin.getRole().name());
            st.setString(2, admin.getAdminId());
            st.setString(3, admin.getAdminLabel());
            st.setString(4, admin.getFullname());
            st.setString(5, admin.getEmail());
            st.setString(6, hashPassword(admin.getPassword()));
            st.setString(7, admin.getUniversityName());
            st.setString(8, admin.getDepartment().name());
            st.setString(9, admin.getSpecialization().name());
            st.setString(10, String.join(",", admin.getPermissions()));

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert admin into the database.");
            }
            return true;

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new DuplicateEntryException("Admin ID or Email ID already exists!", e);
            }
            throw new SQLException("Failed to register the admin.", e);
        }
    }

    // Check if an admin already exists
    public boolean findAdmin(String adminId, String email) throws SQLException, ClassNotFoundException {
        final String FIND_ADMIN = "SELECT 1 FROM admins WHERE adminId = ? OR email = ? LIMIT 1";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(FIND_ADMIN)) {

            st.setString(1, adminId);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new SQLException("Failed to check admin existence.", e);
        }
    }

    // Login admin
    public Admin loginAdmin(String adminIdOrEmail, String password) throws SQLException, ClassNotFoundException {
        final String LOGIN_ADMIN = "SELECT * FROM admins WHERE (adminId = ? OR email = ?) AND password = ?";

        try (Connection conn = DbConnect.getConnnection();
             PreparedStatement st = conn.prepareStatement(LOGIN_ADMIN)) {

            st.setString(1, adminIdOrEmail);
            st.setString(2, adminIdOrEmail);

            st.setString(3, hashPassword(password));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setRole(Role.valueOf(rs.getString("role")));
                admin.setAdminId(rs.getString("adminId"));
                admin.setAdminLabel(rs.getString("adminLabel"));
                admin.setFullname(rs.getString("fullname"));
                admin.setEmail(rs.getString("email"));
                admin.setUniversityName(rs.getString("universityName"));
                admin.setDepartment(Department.valueOf(rs.getString("department")));
                admin.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
                admin.setPermissions(List.of(rs.getString("permissions").split(",")));
                return admin;
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to login admin.", e);
        }
        return null;
    }
}
