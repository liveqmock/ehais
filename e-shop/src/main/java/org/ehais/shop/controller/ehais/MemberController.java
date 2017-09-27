package org.ehais.shop.controller.ehais;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MemberController extends CommonController{

	
	@RequestMapping("/w_member")
	public String member(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/member";
	}
	
	@RequestMapping("/w_member_order")
	public String member_order(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/member_order";
	}
	
	
	@RequestMapping("/w_member_fans")
	public String member_fans(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/member_fans";
	}
	
	
	
}
