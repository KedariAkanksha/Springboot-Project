package com.itp.flipkart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp.flipkart.model.Employee;
import com.itp.flipkart.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee saveStudent(Employee employee){
        return employeeRepository.save(employee);
    }
}
