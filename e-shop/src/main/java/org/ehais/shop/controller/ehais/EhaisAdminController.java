package org.ehais.shop.controller.ehais;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.IpUtil;
import org.ehais.util.SignUtil;
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
	private EHaiAdminUserService adminUserService;
//	private AdminUserService adminUserService;
	@Autowired
	protected EWPPublicService eWPPublicService;
	
	
	
	
	@RequestMapping("/adminlogin")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		log.info("登录IP:"+IpUtil.getIpAddr(request));
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
			ReturnObject<EHaiAdminUser> rm = adminUserService.hai_login_submit(request , username, password , verificationcode ,false);
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
			session.removeAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
			session.removeAttribute(EConstants.SESSION_ADMIN_PROJECT_FOLDER);
			session.removeAttribute(EConstants.SESSION_ROLE_ID_ARRAY);
			session.removeAttribute(EConstants.SESSION_PARTNER_ID);
			session.removeAttribute(EConstants.SESSION_PARTNER_NAME);
			session.removeAttribute(EConstants.SESSION_PARTNER_THEME);
			session.removeAttribute(EConstants.SESSION_STORE_THEME);
			
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
			String startDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String endDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("startDate", startDate);
			modelMap.addAttribute("endDate", endDate);
			
			String adminClasssify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
			if(StringUtils.isNotBlank(adminClasssify) && adminClasssify.equals(EAdminClassifyEnum.shop)){
				Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
				WpPublic wp = eWPPublicService.getWpPublic(store_id);
				String cid = SignUtil.setCid(store_id, 0, 0l, 0l, wp.getToken());
				//软文链接
				String articleLink = request.getScheme()+"://"+request.getServerName()+"/w_article!"+cid;
				modelMap.addAttribute("articleLink", articleLink);
				//商城链接
				String shopLink = request.getScheme()+"://"+request.getServerName()+"/w_shop!"+cid;
				modelMap.addAttribute("shopLink", shopLink);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/"+this.getStoreTheme(request)+"/main";
	}
	
}
