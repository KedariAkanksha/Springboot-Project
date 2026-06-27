package com.itp.flipkart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp.flipkart.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	  @RequestMapping("/test")
	    private ResponseEntity<String> test() {
	        return new ResponseEntity<String>("Testing Spring Boot Application", HttpStatus.OK);
	    }
}
