package com.lms.servlet.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.lms.dao.ApplicationDao;
import com.lms.models.Application;
import com.lms.util.ApiError;

@WebServlet("/admin/view/applications")
public class ViewApplicationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Step 1: Create instance of application dao class to access its method
	ApplicationDao applicationDao;
	
    public ViewApplicationsServlet() {
        super();
        this.applicationDao = new ApplicationDao();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			
			// Step 2: Retrive all the applications and store it in a list
			List<Application> applications = applicationDao.getAllApplications();
			
			// Step 3: Set attribute for the request
			req.setAttribute("applications", applications);
			
			// Step 4: Create request dispatcher obj to forward the req to specific view page
			RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/dashboard/admin-dashboard.jsp");
			
			// Step 5: Forward the request
			dispatcher.forward(req, res);
			
		} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
