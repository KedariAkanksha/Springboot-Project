package com.itp.flipkart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyAppSecurityDAO 
{
	 //Authorisation
//	    @Bean  
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    		http.authenticationProvider(myAuthenticationProvider());      //1.Athentication
//	    	
//	        http.authorizeRequests()                                   // Authorization
//	        .requestMatchers("/getAllProductsFE","/addProductForm","/saveUserForm","/showAllUsers").hasAnyAuthority("USER","ADMIN")
//	        .requestMatchers("/deleteProductFE/**","/updateProductForm/**","/deleteUserFE/**","/updateUserForm/**").hasAuthority("ADMIN")
//	        .anyRequest().authenticated()
//	        .and()
//	        .formLogin().loginProcessingUrl("/login").successForwardUrl("/getAllProductsFE").permitAll()
//	        .and()
//	        .logout().logoutSuccessUrl("/login").permitAll()
//	        .and()
//	        .exceptionHandling().accessDeniedPage("/403")
//	        .and()
//	        .cors().and().csrf().disable();
//	        return http.build();
//	    }
	
	  @Bean  
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    		http.authenticationProvider(myAuthenticationProvider());      //1.Athentication
	    	
	        http.authorizeRequests()                                   // Authorization
	        .anyRequest().authenticated()
	        .and()
	        .formLogin().loginProcessingUrl("/login").successForwardUrl("/home").permitAll()
	        .and()
	        .logout().logoutSuccessUrl("/login").permitAll()
	        .and()
	        .exceptionHandling().accessDeniedPage("/403")
	        .and()
	        .cors().and().csrf().disable();
	        return http.build();
	    }

	    @Bean
	    public AuthenticationProvider myAuthenticationProvider() {  //2.create a myAuthenticationProvider method
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(mySetUserDetailsService());                 //set for user
		dao.setPasswordEncoder(mySetPasswordEncoder()); //set for password
		return dao;
	 }

	    @Bean
		public PasswordEncoder mySetPasswordEncoder() {
			return new BCryptPasswordEncoder();           //using bcryptencoder
		}
	    
	    @Bean
		public UserDetailsService mySetUserDetailsService() {
			return new MyUserDetailsService();                   //3.create myUserDetailsService class
	}
}