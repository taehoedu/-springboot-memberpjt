package com.office.memberpjt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

	@GetMapping({"", "/"})
	public String home(HttpSession session) {
		log.info("home()");
		
		log.info("sessionID at home: {}", session.getId());
		
		return "home";

	}
	
}
