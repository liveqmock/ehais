package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.controller.api.include.MemberIController;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class MemberWSController extends MemberIController{

	
	@ResponseBody
	@RequestMapping("/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		try{
			ReturnObject<EHaiUsers> rm = usersService.login(request,username, password,"ws");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/register")
	public String register(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "confirmPassword", required = true) String confirmPassword) {
		try{
			ReturnObject<EHaiUsers> rm = usersService.register(request,username , password, confirmPassword,"ws");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/member")
	public String member(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<EHaiUsers> rm = memberService.member(request,null , null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/logout")
	public String logout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<EHaiUsers> rm = usersService.logout(request,"ws");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
}
