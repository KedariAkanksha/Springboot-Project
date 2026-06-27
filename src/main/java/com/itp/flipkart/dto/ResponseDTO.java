package com.itp.flipkart.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;

@Builder
public class ResponseDTO {
	 private int status;
	    private String message;
	    private Map<String, String> fieldErrors; // fieldname - message
	    private LocalDateTime timestamp;

	    public ResponseDTO() {}

	    public ResponseDTO(int status, String message, Map<String, String> fieldErrors, LocalDateTime timestamp) {
	        super();
	        this.status = status;
	        this.message = message;
	        this.fieldErrors = fieldErrors;
	        this.timestamp = timestamp;
	    }

	    public int getStatus() {
	        return status;
	    }

	    public void setStatus(int status) {
	        this.status = status;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public Map<String, String> getFieldErrors() {
	        return fieldErrors;
	    }

	    public void setFieldErrors(Map<String, String> fieldErrors) {
	        this.fieldErrors = fieldErrors;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(LocalDateTime timestamp) {
	        this.timestamp = timestamp;
	    }

	    @Override
	    public String toString() {
	        return "ErrorResponse [status=" + status + ", message=" + message + ", fieldErrors=" + fieldErrors
	                + ", timestamp=" + timestamp + "]";
	    }

}
