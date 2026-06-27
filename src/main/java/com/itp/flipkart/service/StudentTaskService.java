package com.itp.flipkart.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itp.flipkart.dto.StudentTaskDto;
import com.itp.flipkart.exception.ResourceNotFoundException;
import com.itp.flipkart.model.StudentTask;
import com.itp.flipkart.repository.StudentTaskRepository;

@Service
public class StudentTaskService {

	@Autowired
	StudentTaskRepository studentTaskRepository;
	
	@Autowired
	ModelMapper modelMapper; 
	
	//private static final Logger logger=Logger.getLogger(StudentTaskService.class);
	private static final Logger logger =LoggerFactory.getLogger(StudentTaskService.class);

	
	public StudentTask saveStudent(StudentTask s1) {
		
		return studentTaskRepository.save(s1);
	}
	public  List<StudentTask> getAllStudents() {
		
		return studentTaskRepository.findAll();
	}
	public List<StudentTask> saveStudents(List<StudentTask> studentList) {
		
		return studentTaskRepository.saveAll(studentList);
	}
	public StudentTask getStudent(int studentId) 
	{
	    logger.info("Received log from student service: " + studentId);
		Optional<StudentTask> student=studentTaskRepository.findById(studentId);	
		return student.orElse(null);
	}
	
	public StudentTask getStudent1(int studentId)  throws ResourceNotFoundException{
		if(studentTaskRepository.existsById(studentId))
		{
			Optional<StudentTask> optional=studentTaskRepository.findById(studentId);	
			return optional.get();
		}
		throw new ResourceNotFoundException("Student with ID  "+ studentId + " does not exist");
	}
	
	public Page<StudentTask> getStudentsByPages(int pageNumber, int pageSize) {
		return studentTaskRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}
	
	public Page<StudentTask> getStudentsByPagesSorted(String fieldName, int pageNumber,int pageSize) {
		return studentTaskRepository.findAll(PageRequest.of(pageNumber, pageSize).withSort(Sort.by(Sort.Direction.DESC,fieldName)));
	}
	public void deleteStudent(int id) {
		if(studentTaskRepository.existsById(id)) {
			studentTaskRepository.deleteById(id);
			return; 
		}
		throw new ResourceNotFoundException("Student with ID " + id + "is not exited");
		
	}
	public StudentTask updateStudent(int id, StudentTask s1) {
		if(studentTaskRepository.existsById(id)) {
			Optional<StudentTask> optional = studentTaskRepository.findById(id);
			StudentTask studentFromDB = optional.get();
			studentFromDB.setStudentName(s1.getStudentName());
			studentFromDB.setCity(s1.getCity());
			studentFromDB.setCollegeName(s1.getCollegeName());
			studentFromDB.setCourse(s1.getCourse());
			studentFromDB.setEmail(s1.getEmail());
			studentFromDB.setFees(s1.getFees());
			studentFromDB.setMobileNumber(s1.getMobileNumber());
			
			return studentTaskRepository.save(studentFromDB);
		}
		throw new ResourceNotFoundException("Student with this Id is not exited: " + id);
	}
	
	public StudentTaskDto saveStudentUsingDto(StudentTaskDto studentTaskDto) {
		logger.info("Request Received to save Student {}",studentTaskDto.getStudentName());
		StudentTask student = modelMapper.map(studentTaskDto, StudentTask.class);
		StudentTask studentSavedToDB = studentTaskRepository.save(student);
		logger.info("Student save sucessfully {}",studentTaskDto.getStudentName());
		return modelMapper.map(studentSavedToDB,StudentTaskDto.class);
		
		}
	

}
