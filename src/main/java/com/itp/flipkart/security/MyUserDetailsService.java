package com.itp.flipkart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itp.flipkart.model.DBUser;
import com.itp.flipkart.repository.DBUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService { 

	//for this service we need to create a DBUserRepository class
	@Autowired
	DBUserRepository dbUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DBUser dbuser = dbUserRepository.findByUsername(username);
		
		if(dbuser==null)
			throw new UsernameNotFoundException("User does not exit");
		
		return new MyUserDecorator(dbuser);               //5.create MyUserDecorator class
	}

}
