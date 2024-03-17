package com.office.memberpjt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		return "home";
		
		// taehoedu task 001
		// taehoedu task 002
		// taehoedu task 003
		// taehoedu task 004
		// taehoedu task 005
		// taehoedu task 006
		
	}
	
}