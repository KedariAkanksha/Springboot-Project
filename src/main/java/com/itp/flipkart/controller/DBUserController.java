package com.itp.flipkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itp.flipkart.model.DBUser;
import com.itp.flipkart.model.Role;
import com.itp.flipkart.repository.RoleRepository;
import com.itp.flipkart.service.DBUserService;

@Controller
public class DBUserController {
	
	@Autowired
	DBUserService dbUserService;
	
	@Autowired
	RoleRepository roleRepository;   //Create a rolerepository and direct used here bcz we want to add roles 
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@RequestMapping("/saveUserForm")
	public String saveUserForm(Model model)
	{
		DBUser user=new DBUser();
		
		List<Role> roles=roleRepository.findAll();
		
		model.addAttribute("user",user);
		model.addAttribute("roles",roles);
		return "save-user-form";
	}
	
	@PostMapping("/saveUserFE")
	public String saveUserFE(@ModelAttribute DBUser user)
	{
		dbUserService.saveUser(user);
		return "redirect:/getAllProductsFE";
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@RequestMapping("/showAllUsers")
	public String showAllUsers(Model model)
	{
		List<DBUser> users=dbUserService.getAllUsers();
		model.addAttribute("users",users);
		return "show-users";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/deleteUserFE/{userid}")
	public String deleteUser(@PathVariable int userid)			
	{
		dbUserService.deleteUser(userid);
		return "redirect:/showAllUsers";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/updateUserForm/{userid}")
	public String updateUserForm(@PathVariable int userid, Model model)
	{
		DBUser user=dbUserService.getUser(userid);
		List<Role> roles=roleRepository.findAll();
		
		model.addAttribute("user",user);
		model.addAttribute("roles",roles);
		return "update-user-form";
	}
	
	@PostMapping("/updateUserFE/{userid}")
	public String updateUserFE(@PathVariable int userid,@ModelAttribute DBUser newValues)
	{
		dbUserService.updateUser(userid,newValues);
		return "redirect:/showAllUsers";


}
}
