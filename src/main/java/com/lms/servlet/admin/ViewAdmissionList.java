package com.lms.servlet.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.lms.dao.AdmissionDao;
import com.lms.models.Admission;
import com.lms.util.ApiError;


@WebServlet("/admin/view/admissions")
public class ViewAdmissionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Step 1: Create instance of application dao class to access its method
	AdmissionDao admissionDao;
	
    public ViewAdmissionList() {
        super();
        this.admissionDao = new AdmissionDao();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			
			// Step 2; Retrive all admission forms and store it in a list
			List<Admission> admissions = admissionDao.getAllAdmissions();
			
			// Step 3: Pass the admission list onto the request
			req.setAttribute("admissions", admissions);
			
			// Step 4: Create a request dispatcher to forward the request
			RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/dashboard/admin-dashboard.jsp");
			
			// Step 5: Forward the request
			dispatcher .forward(req, res);
			
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
