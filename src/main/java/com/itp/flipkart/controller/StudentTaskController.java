package com.itp.flipkart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itp.flipkart.dto.StudentTaskDto;
import com.itp.flipkart.exception.ResourceNotFoundException;
import com.itp.flipkart.model.StudentTask;
import com.itp.flipkart.service.StudentService;
import com.itp.flipkart.service.StudentTaskService;

import jakarta.validation.Valid;

@RestController
public class StudentTaskController {
	
	@Autowired
	StudentTaskService studentTaskService;
	
	
//	private static final Logger logger=Logger.getLogger(StudentTaskController.class);
	
	private static final Logger logger =LoggerFactory.getLogger(StudentTaskController.class);
	
	@PostMapping("/saveStudentUsingDTOWithValidation")
	public ResponseEntity<StudentTaskDto> saveStudentUsingDTOWithValidation(@Valid @RequestBody  StudentTaskDto studDTO)
	{
		logger.info("Request Received to save Student {}",studDTO.getStudentName());
		StudentTaskDto stDTO=studentTaskService.saveStudentUsingDto(studDTO);
		logger.info("Record saved successfully for Student {}",studDTO.getStudentName());
		return new ResponseEntity<StudentTaskDto>(stDTO,HttpStatus.OK);
	}


	//save single student data
	@PostMapping("/saveStudentUsingRequestParam")
	public StudentTask saveStudentUsingRequestParam(
			@RequestParam("a") String studentName,
			@RequestParam("b") String course,
			@RequestParam("c") double fees,
			@RequestParam("d") String email,
			@RequestParam("e") String city,
			@RequestParam("f") double percentage,
			@RequestParam("g") String collegeName
			
			)
	{
		StudentTask s1=StudentTask.builder()
				.studentName(studentName)
				.course(course)
				.fees(fees)
				.email(email)
				.city(city)
				.percentage(percentage)
				.collegeName(collegeName)
				.build();
		
		return studentTaskService.saveStudent(s1);
		
		
	}
	
	@PostMapping("/saveUsingResponseBody")  //single details
	public StudentTask saveUsingResponseBody(@RequestBody StudentTask s1){
		return studentTaskService.saveStudent(s1);
	}
	
	@PostMapping("/saveUsingResponseBody1")   //multiple
	public ResponseEntity<List<StudentTask>> saveUsingResponseBody1(@RequestBody List<StudentTask> studentList){
		logger.info("Request received in service to add Students " + studentList.size());
		return new ResponseEntity<List<StudentTask>>(studentTaskService.saveStudents(studentList),HttpStatus.OK);
	}
	
	//Get all the records
	@GetMapping("/getAllRecords")
	public ResponseEntity<List<StudentTask>> getAllRecords(){
		return new ResponseEntity<List<StudentTask>>(studentTaskService.getAllStudents(),HttpStatus.OK);
	}
	
	//Get records by ID
	@GetMapping("/getStudent/{studentId}")
	public ResponseEntity<StudentTask> getStudent2(@PathVariable int studentId) 
	{
		logger.info("Received log by student controller: " + studentId);
		return new ResponseEntity<StudentTask>(studentTaskService.getStudent(studentId), HttpStatus.OK);
	}
	
	//ResourceNotFoundException
	@GetMapping("/getStudent1/{studentId}")
	public ResponseEntity<?> getStudent1(@PathVariable int studentId)
	{
		try
		{
		return new ResponseEntity<StudentTask>(studentTaskService.getStudent1(studentId), HttpStatus.OK);
		}
		catch(ResourceNotFoundException ex1)
		{
		return new ResponseEntity<String>(ex1.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getStudentsByPages/{pageNumber}/{pageSize}")
	public ResponseEntity<Page<StudentTask>> getStudentsByPages(@PathVariable int pageNumber, @PathVariable int pageSize){
		return new ResponseEntity<Page<StudentTask>>(studentTaskService.getStudentsByPages(pageNumber,pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/getStudentsByPagesSorted/{fieldName}/{pageNumber}/{pageSize}")
	public ResponseEntity<Page<StudentTask>> getStudentsByPagesSorted(@PathVariable String fieldName,@PathVariable int pageNumber, @PathVariable int pageSize){
		return new ResponseEntity<Page<StudentTask>>(studentTaskService.getStudentsByPagesSorted(fieldName,pageNumber,pageSize),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id){
		studentTaskService.deleteStudent(id);
		return new ResponseEntity<String> ("Student record deleted which is having id " + id , HttpStatus.OK);
		//return new ResponseEntity<String> (studentTaskService.deleteStudent(id), HttpStatus.OK);
	}
	
	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<StudentTask> updateStudent(@PathVariable int id, @RequestBody StudentTask s1){
		return new ResponseEntity<StudentTask> (studentTaskService.updateStudent(id,s1),HttpStatus.OK);
	}
	
	@PostMapping("/saveStudentUsingDto")  //inserting any garbage data
	public ResponseEntity<StudentTaskDto> saveStudentUsingDto(@RequestBody StudentTaskDto studentTaskDto){
		return new ResponseEntity<StudentTaskDto> (studentTaskService.saveStudentUsingDto(studentTaskDto),HttpStatus.OK);
	}
	
	@PostMapping("/saveStudentUsingDtoWithValidation")   //inserting data with validation
	public ResponseEntity<StudentTaskDto> saveStudentUsingDtoWithValidation(@Valid @RequestBody StudentTaskDto studentTaskDto){
		return new ResponseEntity<StudentTaskDto> (studentTaskService.saveStudentUsingDto(studentTaskDto),HttpStatus.OK);
	}

}