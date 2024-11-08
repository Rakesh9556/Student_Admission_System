<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lms.models.Application" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Applications List</h2>
    <table>
        <tr>
            <th>Application ID</th>
            <th>Full Name</th>
            <th>Program</th>
            <th>Branch</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            // Safely retrieve the applications list
            @SuppressWarnings("unchecked")
            List<Application> applications = (List<Application>) request.getAttribute("applications");
            // If applications is null, initialize as an empty list
            if (applications == null) {
                applications = new ArrayList<Application>();
            }
            // Iterate through the list of applications
            for (Application app : applications) {
        %>
            <tr>
                <td><%= app.getApplicationId() %></td>
                <td><%= app.getFullName() %></td>
                <td><%= app.getEnrolledProgram() %></td>
                <td><%= app.getEnrolledBranch() %></td>
                <td><%= app.getStatus() %></td>
                <td>
					<form action="/Learning_Management_System/admin/update/applicationStatus" method="post">
					    <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>">
					    
					    <!-- Dropdown to select action -->
					    <select name="action" onchange="this.form.submit()">
					        <option value="" disabled selected>Select Action</option>
					        <option value="approve">APPROVE</option>
					        <option value="reject">REJECT</option>
					        <option value="schedule">SCHEDULE</option>
					    </select>	
					</form>
                </td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
