package org.ehais.shop.controller.ehais;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.shop.service.AdminUserService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ehais")
public class EhaisAdminController extends CommonController{

	@Autowired
	private AdminUserService adminUserService;
	
	/**
	 * 验证码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/AuthImage")
	public void AuthImage(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws IOException {	
		
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
        
	}
	
	
	@RequestMapping("/adminlogin")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			modelMap.addAttribute("submit", "admin_login_submit");
			modelMap.addAttribute("redirect", "manage/main");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/login";
	}
	
	@ResponseBody
	@RequestMapping("/admin_login_submit")
	public String login_submit(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "verificationcode", required = true) String verificationcode
			) {		
		try{
			ReturnObject<EHaiAdminUser> rm = adminUserService.hai_login_submit(request , username, password , verificationcode);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	@RequestMapping("/manage/logout")
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
		return "redirect:../adminlogin";
	}
	
	@RequestMapping("/manage/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Date date = new Date();
			String statisticsStartDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String statisticsEndDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("statisticsStartDate", statisticsStartDate);
			modelMap.addAttribute("statisticsEndDate", statisticsEndDate);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/"+this.getStoreTheme(request)+"/main";
	}
	
}
