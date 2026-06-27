package com.itp.flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itp.flipkart.model.DBUser;

@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Integer>{ //4.create DBUserRepositroy class

	
	public DBUser findByUsername(String s);
	
//	//@Query("select DBUser where userId =: id ")
//	public DBUser getUserById(Integer id);
}
