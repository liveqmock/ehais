package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.controller.api.include.UserIController;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class UserApiController extends UserIController {

	
	@ResponseBody
	@RequestMapping("/user_info")
	public String user_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code) {

		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_find(request, user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/user_info_edit")
	public String user_info_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "token", required = true) String token,
			@Valid @ModelAttribute("user") EHaiUsers user,
			BindingResult result) {

		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_info_edit(request,user, user_id,token);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	/**
	 * 绑定用户的手机号码等信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param user_id
	 * @param token
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user_mobile_bind")
	public String user_mobile_bind(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "token", required = true) String token,
			@Valid @ModelAttribute("user") EHaiUsers user,
			BindingResult result) {

		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_info_edit(request,user, user_id,token);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
}
