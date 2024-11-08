package com.lms.servlet.admin;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.lms.dao.ApplicationDao;
import com.lms.models.Application;
import com.lms.service.SMSSender;

@WebServlet("/admin/update/applicationStatus")
public class UpdateApplicationStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	// Step 1: Create instance of application dao class to access its method
	ApplicationDao applicationDao;

    public UpdateApplicationStatusServlet() {
        super();
        this.applicationDao = new ApplicationDao();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
    	res.getWriter().append("Served at: ").append(req.getContextPath());
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        Long applicationId = Long.valueOf(req.getParameter("applicationId"));

        try {
        	
        	boolean isStatusUpdated = false;
        	
            // Handle the action
            if ("approve".equals(action)) {
            	isStatusUpdated = applicationDao.approveApplication(applicationId);
            } else if ("reject".equals(action)) {
            	isStatusUpdated = applicationDao.rejectApplication(applicationId);
            } else if ("schedule".equals(action)) {
            	isStatusUpdated = applicationDao.scheduleExam(applicationId);
            }
            
            
            // Check if status is updated successfully or not
            if(isStatusUpdated) {
            	
            	// If status updated successfully retrieve the Application
            	Application application = applicationDao.getApplicationByIdOrPhone(String.valueOf(applicationId));
            	
            	if(action.equals("reject")) {
            		 SMSSender.sendApplicationRejectedSMS(application.getFullName(), application.getPhone());
            	}
            	else if(action.equals("schedule")) {
            		SMSSender.sendExamScheduledSMS(application.getFullName(), application.getPhone(), applicationId);
            	}
            	else if(action.equals("approve")) {
            		SMSSender.sendApplicationApprovedSMS(application.getFullName(), application.getPhone());
            		
            	}
            }
            
            // Redirect or forward after the action is completed
            res.sendRedirect("/Learning_Management_System/admin/view/applications");
            
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().write("Error updating application status: " + e.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
