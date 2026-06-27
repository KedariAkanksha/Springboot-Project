package com.itp.flipkart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp.flipkart.dto.DepartmentDTO;
import com.itp.flipkart.exception.ResourceNotFoundException;
import com.itp.flipkart.model.Department;
import com.itp.flipkart.repository.DepartmentRepossitory;

import jakarta.validation.Valid;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepossitory departmentRepossitory;

	@Autowired
	ModelMapper modelMapper;
	
	
	 public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO){
	        Department department = modelMapper.map(departmentDTO, Department.class);
	            department.getEmployees().forEach(employee -> {
	                employee.setDepartment(department);                                   //link child-parent

	                employee.getAddresses().forEach
	                (address -> address.setEmployee(employee));                          //link grandchild-parent
	            });
	        Department savedDepartment = departmentRepossitory.save(department);
	        DepartmentDTO savedDepartmentDTO =  modelMapper.map(savedDepartment, DepartmentDTO.class);
	        return savedDepartmentDTO;
	    }

	public List<DepartmentDTO> saveDepartments(@Valid List<DepartmentDTO> departmentDtos) {
		List<Department> departments = departmentDtos.stream()
				.map(departmentDTO -> modelMapper.map(departmentDTO, Department.class))
				.toList();
		 departments.forEach(department -> {
	            department.getEmployees().forEach(employee -> {
	                employee.setDepartment(department);

	                employee.getAddresses().forEach(address -> address.setEmployee(employee));
	            });
	        });
	        List<Department> savedDepartments = departmentRepossitory.saveAll(departments);
	        List<DepartmentDTO> savedDepartmentDTOS =  savedDepartments
	                .stream()
	                .map(department -> modelMapper.map(department, DepartmentDTO.class))
	                .toList();
	        return savedDepartmentDTOS;
	}

	 public DepartmentDTO getDepartment(Integer departmentID) throws ResourceNotFoundException {
	        if(departmentRepossitory.existsById(departmentID)){
	            Department department =  departmentRepossitory.findById(departmentID).get();
	            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
	            return departmentDTO;
	        }
	        throw new ResourceNotFoundException("Resource not available in database");
	    }
	
	 public List<DepartmentDTO> getDepartments(){
	        List<Department> databaseDepartments =  departmentRepossitory.findAll();
	        List<DepartmentDTO> databaseDepartmentDTOS = databaseDepartments
	                .stream()
	                .map(department -> modelMapper.map(department, DepartmentDTO.class))
	                .toList();
	        return databaseDepartmentDTOS;
	    }
	 
	 public Map<String, Integer> getDepartmentEmployeeCount() {
		    Map<String, Integer> departmentEmployeeCount = new HashMap<>();
		    List<Department> databaseDepartments = departmentRepossitory.findAll();
		    
		    databaseDepartments.forEach(department -> {
		        int employeeCount = (department.getEmployees() != null) ? department.getEmployees().size() : 0;
		        
		        departmentEmployeeCount.put(department.getDepartmentName(), employeeCount);
		    });
		    
		    return departmentEmployeeCount;
		}
}
