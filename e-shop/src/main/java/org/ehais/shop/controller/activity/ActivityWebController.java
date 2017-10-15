package org.ehais.shop.controller.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class ActivityWebController extends EhaisCommonController{

	@RequestMapping(value="/haiActivityApply!{aid}")
	public String haiActivityApply(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		
		
		
		
		return "/ehais/w_activity_apply";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/haiActivityApplySubmit!{aid}")
	public String haiActivityApplySubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		return null;
	}
	
	
	@RequestMapping(value="/haiActivitySign!{aid}")
	public String haiActivitySign(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		
		
		
		
		return "/ehais/w_activity_sign";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/haiActivitySignSubmit!{aid}")
	public String haiActivitySignSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		return null;
	}
	
	
	
}
