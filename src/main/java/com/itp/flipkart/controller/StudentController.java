package com.itp.flipkart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itp.flipkart.model.Student;
import com.itp.flipkart.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	StudentService studentService;

	@RequestMapping("/saveStudent")
	public String saveStudent() {
		Student s1 = Student.builder().sname("Ben").dname("Arts").per(98.5).build();

		studentService.saveStudent(s1);
		return "Record Saved";

	}

}
