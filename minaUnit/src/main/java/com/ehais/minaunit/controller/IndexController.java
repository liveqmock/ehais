package com.ehais.minaunit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {

	
	@RequestMapping("/index")
	public String index(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response){
		
		return "index";
	}
	
}
