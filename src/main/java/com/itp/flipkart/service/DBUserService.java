package com.itp.flipkart.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itp.flipkart.model.DBUser;
import com.itp.flipkart.repository.DBUserRepository;

@Service
public class DBUserService {
	
	@Autowired
	DBUserRepository dbUserRepository;

	public void saveUser(DBUser user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));   //for getting this encoder have to add bean
		user.setAccountEnabledStatus(1);
		user.setAccountLockedStatus(1);
		user.setCredExpiryDate(LocalDate.now().plusMonths(4));
		user.setAccExpiredDate(LocalDate.now().plusYears(1));
		dbUserRepository.save(user);
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public List<DBUser> getAllUsers() {
		return dbUserRepository.findAll();
	}
	public void deleteUser(int userid) {
		dbUserRepository.deleteById(userid);
	}
	public DBUser getUser(int userid) {
		return dbUserRepository.findById(userid).get();
	}
	
	
	public void updateUser(int userid, DBUser newValues) {
		DBUser userFromDatabase= getUser(userid);
		userFromDatabase.setAccountEnabledStatus(newValues.getAccountEnabledStatus());
		userFromDatabase.setAccExpiredDate(newValues.getAccExpiredDate());
		userFromDatabase.setAccountLockedStatus(newValues.getAccountLockedStatus());
		userFromDatabase.setCredExpiryDate(newValues.getCredExpiryDate());
		
		userFromDatabase.setUsername(newValues.getUsername());
		
		if(newValues.getPassword().length()>0)
		userFromDatabase.setPassword(passwordEncoder().encode(newValues.getPassword()));
		
		userFromDatabase.setRoles(newValues.getRoles());
		dbUserRepository.save(userFromDatabase);
		
	}


}
