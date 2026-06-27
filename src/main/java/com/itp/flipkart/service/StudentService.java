package com.itp.flipkart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp.flipkart.model.Student;
import com.itp.flipkart.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	public void saveStudent(Student s1) {
		studentRepository.save(s1);
		
	}
}