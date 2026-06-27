package com.itp.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.Department;

@Repository
public interface DepartmentRepossitory extends JpaRepository<Department, Integer>{

}
