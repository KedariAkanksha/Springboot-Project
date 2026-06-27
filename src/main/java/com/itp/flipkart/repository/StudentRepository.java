package com.itp.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{

}
