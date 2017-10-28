package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//各自有各自的登录特色，费事把登录的文件也统一
@Controller
@RequestMapping("/")
public class LoginAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(LoginAdminController.class);

	protected String project_folder = ResourceUtil.getProValue("setting.project_folder");
	
	@Autowired
	private EHaiAdminUserService eHaiAdminUserService;

	@RequestMapping("/adminlogin")
	public String admin_login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
			rm.setAction("admin_login_submit_ajax");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("redirect", "admin/index");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/"+this.getAdminProjectFolder(request, project_folder)+"/main/login";
	}

	@RequestMapping(value = "/adminloginsubmit", method = RequestMethod.POST)
	public String admin_login_submit(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "password", required = true) String verificationcode
			) {
		try {
			ReturnObject<EHaiAdminUser> rm = eHaiAdminUserService.login_admin(request, username, password,verificationcode);

			// 根据获取的用户名和密码封装成Token
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// 是否记住用户
			token.setRememberMe(true);
			// 获取当前的subject
			Subject subject = SecurityUtils.getSubject();

			subject.login(token);  
			
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/index";
	}

	@RequestMapping("/adminlogout")
	public String admin_logout(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			ReturnObject<EHaiAdminUser> rm = eHaiAdminUserService.logout_admin(request);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/adminlogin");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/adminlogin";
	}

}
