package org.ehais.shop.controller.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.controller.admin.ELoginAdminController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class FinanceController extends CommonController{

	
	private static Logger log = LoggerFactory.getLogger(ELoginAdminController.class);

	@Autowired
	private EHaiAdminUserService eHaiAdminUserService;
	
	@RequestMapping("/finance")
	public String finance(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
			rm.setAction("admin_login_submit_ajax");
			
			modelMap.addAttribute("redirect", "admin/index");
			
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/finance/index";
	}
	
	
	@RequestMapping("/admin/welcome")
	public String welcome(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
			rm.setAction("admin_login_submit_ajax");
			
			modelMap.addAttribute("redirect", "admin/index");
			
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/finance/welcome";
	}
	
	
	@RequestMapping("/finance_login")
	public String finance_login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
//			rm.setAction("admin_login_submit");
			rm.setAction("admin_login_submit_ajax");
			
			modelMap.addAttribute("redirect", "admin/index");
			
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/finance/main/login";
	}
	
	
}
