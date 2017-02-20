package org.ehais.project.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;




import javax.servlet.http.HttpSession;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.HaiAdminUser;
import org.ehais.project.model.Menu;
import org.ehais.project.service.HaiAdminUserService;
import org.ehais.project.service.LoginService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/")
public class AdminLoginController extends CommonController{
	
	@Autowired
	private HaiAdminUserService adminService;

	/**
	 * 登陆页
	 */
	@RequestMapping("/admin_login")
	public String login(HttpServletRequest request, ModelMap model) {
		model.addAttribute("action", "adminLogin");
		return "admin/login";
	}
	
	
	/**
	 * 登陆提交
	 */
	@RequestMapping( value="/adminLogin",method = RequestMethod.POST)
	public String loginSubmit(@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			HttpServletRequest request, ModelMap model) {
//		String ret = null;
		// 如果账号密码正确
		try{
			ReturnObject<HaiAdminUser> ro = adminService.login(userName, password);
			if(ro.getCode()!=1){
				return "redirect:admin_login";
			}
			HaiAdminUser user = ro.getModel();
			if (user == null){
				return "redirect:admin_login";
			}
			
			request.getSession().setAttribute(Constants.SESSION_ADMIN_NAME,userName);
			request.getSession().setAttribute(Constants.SESSION_ADMIN_ID,user.getAdminId());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:admin/index";

	}
	/**
	 * 退出登录
	 */
	@RequestMapping("/admin_logout")
	public String admin_logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:admin_login";
	}
	
	
	
}
