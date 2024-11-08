package com.lms.servlet.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.lms.dao.ApplicationDao;
import com.lms.models.Application;
import com.lms.models.constants.Branch;
import com.lms.models.constants.Program;
import com.lms.service.SMSSender;
import com.lms.util.ApiError;
import com.lms.util.ApplicationIdGenerator;
import com.lms.util.PasswordGenerator;

@WebServlet("/application/create")
public class CreateApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Step 1: Create an instance of Application dao class to access its methods
	private ApplicationDao applicationDao;

    public CreateApplicationServlet() {
    	// Step 2: initialize the dao
        super();
        this.applicationDao = new ApplicationDao();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// Step 3: Retreive data from requests
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String street = req.getParameter("street");
		String city = req.getParameter("city");
		String state = req.getParameter("state");
		String enrollProgram = req.getParameter("enrollProgram");
		String enrollBranch = req.getParameter("enrollBranch");
		
		// Step 4: Validate all the fields
		if(firstname == null || firstname.trim().isEmpty()) {
			throw new ApiError(400, "First name is required!");
		}
		
		if(lastname == null || lastname.trim().isEmpty()) {
			throw new ApiError(400, "Last name is required!");
		}
		
		if(email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new ApiError(400, "Invalid email address!");
		}
		
		if(phone == null || phone.trim().isEmpty() || !phone.matches("^\\+91[789]\\d{9}$")) {
			throw new ApiError(400, "Invalid phone number! Use +91XXXXXXXXXX.");
		}
		
		if(dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
			throw new ApiError(400, "Date of Birth is required!");
		}
		
		if(street == null || street.trim().isEmpty()) {
			throw new ApiError(400, "Street name is required!");
		}
		
		if(city == null || city.trim().isEmpty()) {
			throw new ApiError(400, "City name is required!");
		}
		
		if(state == null || state.trim().isEmpty()) {
			throw new ApiError(400, "State name is required!");
		}
		
		if(enrollProgram == null || enrollProgram.trim().isEmpty()) {
			throw new ApiError(400, "Program name is required!");
		}
		
		if(enrollBranch == null || enrollBranch.trim().isEmpty()) {
			throw new ApiError(400, "Branch name is required!");
		}
		
		
		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(email);
		System.out.println(phone);
		System.out.println(dateOfBirth);
		System.out.println(street);
		System.out.println(city);
		System.out.println(state);
		System.out.println(enrollProgram);
		System.out.println(enrollBranch);
		
		// Step 5: Validating the enum fields and creating the application
		try {
			Program enrollProgramEnum = Program.valueOf(enrollProgram.toUpperCase());
			Branch enrollBranchEnum = Branch.valueOf(enrollBranch.toUpperCase());
			
			Long applicationId = ApplicationIdGenerator.generateUniqueId();
			System.out.println("Generated id: " + applicationId);
			
			// Step 6: Create the application object 
			Application application = new Application();
			application.setApplicationId(applicationId);
			application.setFullName(firstname + " " + lastname);
			application.setEmail(email);
			application.setPhone(phone);
	        application.setAddress(street + ", " + city + ", " + state);
	        application.setEnrolledProgram(enrollProgramEnum);
	        application.setEnrolledBranch(enrollBranchEnum);
//	        
//	        System.out.println("Hello Rakesh");
//	        System.out.println("Retrived Application id: " + application.getApplicationId());
//	      
	        // Step 7: Generate the password
	        String password = PasswordGenerator.generatePassword(firstname, dateOfBirth);

	    	if(password == null || password.trim().isEmpty()) {
				throw new ApiError(400, "Password cannot be empty!");
			}
	        
	        // Step 8: Set the password in application obj
	        application.setPassword(password);
	        
	        // Step 9: Calling the dao class method to create the application
	        boolean isCreated = applicationDao.createApplication(application);
	        
	        // Step 10: Validate if application created or not
	        if(!isCreated) {
	        	throw new ApiError(500, "failed to create application!");
	        }
	        
	        // Step 11: Return the response
	        SMSSender.sendApplicationCreatedSMS(application.getFullName(), phone, applicationId);
	        System.out.println("Sms sent successfully!");
	        res.getWriter().write("Application created successfully with ID: " + applicationId);
			
			} catch (ApiError e) {
			res.setStatus(e.getStatusCode());
			res.getWriter().write(e.getMessage());
		} catch (Exception e) {
			res.getWriter().write(e.getMessage());
		}
	}

}
