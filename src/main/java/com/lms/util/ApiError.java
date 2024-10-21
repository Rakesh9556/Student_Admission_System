package com.lms.util;

public class ApiError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private int statusCode;
    private String message;
    private Object data;
    private boolean success;
    private String[] errors;

    // Constructor to initialize fields
    public ApiError(int statusCode, String message, String[] errors, Object data) {
        super(message); // Calls the superclass constructor
        this.statusCode = statusCode;
        this.message = message != null ? message : "Something went wrong";
        this.errors = errors != null ? errors : new String[0];
        this.data = data;
        this.success = false;
    }


	public ApiError(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
		this.errors = new String[0];
		this.data = null;
		this.success = false;
	}


	public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String[] getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}
