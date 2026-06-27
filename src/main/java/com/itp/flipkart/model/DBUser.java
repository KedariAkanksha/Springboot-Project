package com.itp.flipkart.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class DBUser {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long userid;
       private String username;
	   private String password;
	    
	   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	   @JoinTable(
	           name = "users_roles",
	           joinColumns = @JoinColumn(name = "fkuserid"),
	           inverseJoinColumns = @JoinColumn(name = "fkroleid")
	           )
	   private List<Role> roles = new ArrayList<>();
	   
	   LocalDate accExpiredDate;
	   
	   int accountLockedStatus; 	  //1 - Account non locked 	0 - Account locked
		
	   LocalDate credExpiryDate;
		
	   int accountEnabledStatus;	 //1 - Account Enabled 	0 - Account Disabled
		


}
