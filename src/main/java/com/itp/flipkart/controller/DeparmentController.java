package com.itp.flipkart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp.flipkart.dto.DepartmentDTO;
import com.itp.flipkart.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
@Validated
public class DeparmentController {

	@Autowired
	DepartmentService departmentService;
	
	@PostMapping("/saveDepartment")
	    public ResponseEntity<DepartmentDTO> saveDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
	        return new ResponseEntity<DepartmentDTO>(departmentService.saveDepartment(departmentDTO),HttpStatus.CREATED);
	    }
	
	@PostMapping("/saveDepartments")
	public ResponseEntity<List<DepartmentDTO>> saveDepartments(@Valid @RequestBody List<DepartmentDTO> departmentDtos){
		return new ResponseEntity<List<DepartmentDTO>>(departmentService.saveDepartments(departmentDtos), HttpStatus.CREATED);
	}
	
	@GetMapping("/getDepartment/{departmentID}")
	public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Integer departmentID){
		return new ResponseEntity<DepartmentDTO>(departmentService.getDepartment(departmentID),HttpStatus.OK);
	}
	
	 @GetMapping("/getDepartments")
	    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
	        return new ResponseEntity<List<DepartmentDTO>>(departmentService.getDepartments(),HttpStatus.OK);
	    }
	 
	 @GetMapping("/getDepartmentEmployeeCount")
	    public ResponseEntity<Map<String, Integer>> getDepartmentEmployeeCount(){
	        return new ResponseEntity<Map<String, Integer>>(departmentService.getDepartmentEmployeeCount(), HttpStatus.OK);
	    }
	
}
