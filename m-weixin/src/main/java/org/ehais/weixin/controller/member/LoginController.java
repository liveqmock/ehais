package org.ehais.weixin.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class LoginController extends CommonController{
	
	@Autowired
	private LoginService loginService;

	/**
	 * 登陆页
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model) {
		model.addAttribute("action", "memberLogin");
		return "member/login";

	}
	
	
	/**
	 * 登陆提交
	 */
	@RequestMapping( value="/memberLogin",method = RequestMethod.POST)
	public String loginSubmit(ModelMap modelMap,@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			HttpServletRequest request, ModelMap model) {
//		String ret = null;
		// 如果账号密码正确
		try{
			ReturnObject<EHaiUsers> ro = loginService.login(request,userName, password);
//			if(ro.getCode()!=1){
//				return "redirect:login";
//			}
//			HaiUsers user = ro.getModel();
//			if (user == null){
//				return "redirect:login";
//			}
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "/member/index");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:member/index";

	}
	/**
	 * 退出登录
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:login";
	}
	
	
	@RequestMapping("/register")
	public String register(HttpServletRequest request, ModelMap model) {
		return "member/register";

	}
	
	
	@RequestMapping( value="/registerSubmit",method = RequestMethod.POST)
	public String registerSubmit(HttpServletRequest request, 
			ModelMap modelMap,
			HttpSession session,
			@RequestParam("userName") String userName,
			@RequestParam("email") String email,
			@RequestParam("password") String password,			
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("verificationCode") String verificationCode
			) {
		String code = session.getAttribute("code").toString();
		try {
			ReturnObject<EHaiUsers> ro = loginService.register(userName,email, password, confirmPassword, verificationCode,code);
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "login");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.errorJump(modelMap);
		
//		return "redirect:login";

	}
	
}
