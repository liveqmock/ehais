package com.ehais.huangbao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/xm")
public class XiuMiusController {

	
	@RequestMapping("/test")
	public String test(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/xiumius/test";
	}
	
}
