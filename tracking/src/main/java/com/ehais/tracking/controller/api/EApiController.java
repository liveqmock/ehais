package com.ehais.tracking.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/api")
public class EApiController extends CommonController{

	@ResponseBody
	@RequestMapping("/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try{
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setCode(1);
			rm.setMsg("success");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
