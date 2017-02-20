package com.ehais.tracking.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehais.tracking.entity.Questionnaire;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping("/admin_loginx")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/login";
	}
	
}
