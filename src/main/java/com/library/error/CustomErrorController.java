package com.library.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController implements ErrorController {

	//Snippet for error page is referred from https://www.baeldung.com/spring-boot-custom-error-page
	
	  @GetMapping("/error")
	  @ResponseBody
	  public String handleError(HttpServletRequest request) {
	  
	      return "Error";
	  }
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
