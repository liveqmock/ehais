package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.shop.service.AdminUserService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class AdminUserIController extends CommonController{

	@Autowired
	private AdminUserService adminUserService;
	
	@ResponseBody
	@RequestMapping("/admin_login")
	public String admin_login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password){
		try{
			ReturnObject<EHaiAdminUser> rm = adminUserService.admin_login(request,username, password);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("admin_login api", e);
		}
		return null;
	}
	
}
