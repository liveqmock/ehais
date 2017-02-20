package org.ehais.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.project.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private IndexService indexService;

	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		indexService.IndexDate();
		return "/web/index";
	}
	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		return "/admin/main";
	}
	
}
