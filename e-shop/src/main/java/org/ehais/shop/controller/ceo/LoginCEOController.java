package org.ehais.shop.controller.ceo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.annotation.EPermissionController;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@EPermissionController(intro="基础数据",value="LoginCEOController")
@Controller
@RequestMapping("/")
public class LoginCEOController extends CommonController{

	@Autowired
	private EHaiAdminUserService adminUserService;
	@Autowired
	protected EWPPublicService eWPPublicService;
	
	
	@RequestMapping("/ceologin")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		log.info("登录IP:"+IpUtil.getIpAddr(request));
		try{
			modelMap.addAttribute("submit", "ceo_login_submit");
			modelMap.addAttribute("redirect", "ceo/main");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/login";
	}
	
	
	@ResponseBody
	@RequestMapping("/ceo_login_submit")
	public String login_submit(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "verificationcode", required = true) String verificationcode
			) {		
		try{
			ReturnObject<EHaiAdminUser> rm = adminUserService.hai_login_submit(request , username, password , verificationcode ,true,true);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	
	@RequestMapping("/ceo/logout")
	public String logout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			HttpSession session = request.getSession(true);
			session.removeAttribute(EConstants.SESSION_ADMIN_ID);
			session.removeAttribute(EConstants.SESSION_ADMIN_NAME);
			session.removeAttribute(EConstants.SESSION_STORE_ID);
			session.removeAttribute(EConstants.SESSION_STORE_NAME);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:../ceologin";
	}
	
	
}
