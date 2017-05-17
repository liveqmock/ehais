package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bi")
public class BIController {

	@RequestMapping("/a")
	public String a(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/bi/a";
	}
	
	@RequestMapping("/b")
	public String b(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/bi/b";
	}
	
	
	@RequestMapping("/c")
	public String c(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/bi/c";
	}
	
	
	@RequestMapping("/d")
	public String d(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/bi/d";
	}
	
	@RequestMapping("/e")
	public String e(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/bi/e";
	}
	
}
