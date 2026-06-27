package com.itp.flipkart.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itp.flipkart.controller.AddressController;
import com.itp.flipkart.controller.DeparmentController;
import com.itp.flipkart.controller.EmployeeController;
import com.itp.flipkart.controller.HelloController;
import com.itp.flipkart.controller.ProductController;
import com.itp.flipkart.controller.ProductControllerFE;
import com.itp.flipkart.controller.StudentController;
import com.itp.flipkart.controller.StudentTaskController;
import com.itp.flipkart.util.ProductCategory;

//@ControllerAdvice 
//@RestControllerAdvice = @ControllerAdvice + @ResponseBody. It tells Spring Boot to automatically convert your custom APIError objects directly into JSON format 
//without needing to explicitly define it everywhere.
@RestControllerAdvice(assignableTypes = {
	    ProductController.class, AddressController.class, DeparmentController.class,EmployeeController.class,HelloController.class,
	    StudentController.class,StudentTaskController.class,ProductControllerFE.class
	})

public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex)
	{
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST); //missing should return 404(not_found)
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<APIError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		List<APIError> errors=new ArrayList();
		for(FieldError error: ex.getBindingResult().getFieldErrors())
		{
			APIError er1=new APIError(error.getField(),error.getRejectedValue(),error.getDefaultMessage());
			errors.add(er1);
		}
		return new ResponseEntity<List<APIError>>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e) {

		logger.error("Unexpected server error", e);

		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<APIError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//		logger.warn("Validation Problem while adding Product ");
//		
//		 Throwable cause=ex.getCause();
//		 Object rejected = null; 
//		 String fieldName=null;
//		 String message="Invalid Request";
//		 String allowedValues=null; 
//		 
//		 
//		 if(cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatEx)
//		 {
//			 rejected=invalidFormatEx.getValue();
//			 
//			 if (!invalidFormatEx.getPath().isEmpty()) 
//			 fieldName=invalidFormatEx.getPath().get(0).getFieldName();
//		 }
//		 
//
//		// message=ex.getCause() !=null ? ex.getCause().getMessage() : ex.getMessage();
//		 
//		 allowedValues= Arrays.stream(ProductCategory.values())
//				 .map(ProductCategory::getCategory)
//				 .collect(Collectors.joining(", "));
//		
//		 APIError error = new APIError("Invalid "+fieldName+", Allowed values ["+allowedValues+"]", rejected, message);
//
//		return new ResponseEntity<APIError>(error,HttpStatus.BAD_REQUEST);
//
//	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<APIError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	    logger.warn("Validation Problem while adding Product ");
	    
	    Throwable cause = ex.getCause();
	    Object rejected = null; 
	    String fieldName = "category"; // Default fallback since we know it's failing here
	    String message = "Invalid Request";
	    
	    // CASE 1: This handles your custom @JsonCreator IllegalArgumentException error!
	    if (cause instanceof com.fasterxml.jackson.databind.exc.ValueInstantiationException valueEx) {
	        if (!valueEx.getPath().isEmpty()) {
	            fieldName = valueEx.getPath().get(0).getFieldName();
	        }
	        // Extract the "Invalid category: HOME APPLIANCE" message you wrote in Step 1
	        if (valueEx.getCause() != null) {
	            message = valueEx.getCause().getMessage();
	            // Pull "HOME APPLIANCE" out of the message string dynamically
	            if (message != null && message.contains(": ")) {
	                rejected = message.substring(message.indexOf(": ") + 2);
	            }
	        }
	    }
	    // CASE 2: Keep your old check for handling structural data type issues (e.g. text in numeric fields)
	    else if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatEx) {
	        rejected = invalidFormatEx.getValue();
	        if (!invalidFormatEx.getPath().isEmpty()) {
	            fieldName = invalidFormatEx.getPath().get(0).getFieldName();
	        }
	        message = "Format mismatch processing field value";
	    }
	    
	    // Collect clean allowed values dynamically from your Enum map
	    String allowedValues = Arrays.stream(ProductCategory.values())
	            .map(ProductCategory::getCategory)
	            .collect(Collectors.joining(", "));
	    
	    APIError error = new APIError("Invalid " + fieldName + ", Allowed values:  [" + allowedValues + "]", rejected, message);
	    
	    return new ResponseEntity<APIError>(error, HttpStatus.BAD_REQUEST);
	}
}
