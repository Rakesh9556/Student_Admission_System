<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lms.models.Application" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<div class="flex-1 h-screen flex flex-col">
    <!-- Applications Table -->
    <table class="w-full px-4">
      <tr class="text-2xl h-10 font-bold bg-blue-400">
        <th>Application ID</th>
        <th>Full Name</th>
        <th>Program</th>
        <th>Branch</th>
        <th>Status</th>
        <th>Actions</th>
      </tr>

      <%
      // Retrieve or initialize applications list
      @SuppressWarnings("unchecked")
      List<Application> applications = (List<Application>) request.getAttribute("applications");
      
      if (applications == null) {
          applications = new ArrayList<Application>();
      }

      // Iterate through applications
      for (Application app : applications) {
      %>

      <tr class="text-xl bg-red-400">
        <td><%= app.getApplicationId() %></td>
        <td><%= app.getFullName() %></td>
        <td><%= app.getEnrolledProgram() %></td>
        <td><%= app.getEnrolledBranch() %></td>
        <td><%= app.getStatus() %></td>
        <td>
          <form action="/Learning_Management_System/admin/update/applicationStatus" method="post">
            <input type="hidden" name="applicationId" value="<%= app.getApplicationId() %>">
            <select class="text-center rounded-lg p-2 bg-zinc-300 outline-none" name="action" onchange="this.form.submit()">
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
</div>
