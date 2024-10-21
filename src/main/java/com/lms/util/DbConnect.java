package com.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	// 1. create a method to establish and return database connection
	public static Connection getConnnection() throws SQLException, ClassNotFoundException {
		
		// 2. Define database fields
		final String DB_URI = System.getenv("DB_URI");
		final String DB_USER = System.getenv("DB_USER");
		final String DB_PASSWORD = System.getenv("DB_PASSWORD");
		
		
		// Debugging: Envirnment varibles correctly loads or not
		System.out.println("DB URI: " + DB_URI + " USER: " + DB_USER + " PASSWORD: " + DB_PASSWORD);
		
		Connection conn;
		
		if (DB_URI == null || DB_USER == null || DB_PASSWORD == null) {
	        throw new SQLException("Database connection details are not properly set in environment variables.");
	    }

		
		try {
			// 3. Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 4. Create a connection object
			conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("MySQL JDBC Driver not found");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Unable to connect to the database");
		}
		
		// 5. Return the connection
		return conn;
	}

}
