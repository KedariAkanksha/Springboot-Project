package com.itp.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.StudentTask;

@Repository
public interface StudentTaskRepository extends JpaRepository<StudentTask, Integer> {

}
