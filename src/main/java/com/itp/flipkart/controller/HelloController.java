package com.itp.flipkart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/test1")
	public String test1() {
		return "Welcome to my second springboot application";
	}

}
