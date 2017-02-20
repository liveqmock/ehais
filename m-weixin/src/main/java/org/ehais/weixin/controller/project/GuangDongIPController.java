package org.ehais.weixin.controller.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 广东省知识产权公共服务中心
 * @author gzepro
 *
 */
@Controller
@RequestMapping("/gdip")
public class GuangDongIPController extends WxCommonController{
	private static Logger log = LoggerFactory.getLogger(GuangDongIPController.class);
	private Integer wxid = 8;
	@RequestMapping("/user_search_setting.html")
	public String user_search_setting(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("search_setting_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("title", "用户智能检索");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/user_search_setting";
	}
	
	
	
	

	@RequestMapping("/city_sort.html")
	public String city_sort(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "21地市排行榜");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "city_sort");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/eArticleList";
	}
	
	
	@RequestMapping("/arena.html")
	public String arena(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "专利擂台");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "arena");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/eArticleList";
	}
	
	@RequestMapping("/hot_facus.html")
	public String HotFocus(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "热门关注");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "hot_facus");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/eArticleList";
	}
	
	@RequestMapping("/new.html")
	public String new_patent(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "最新专利");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "new");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/eArticleList";
	}
	
	
	@RequestMapping("/snatch.html")
	public String snatch(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "专利抢看");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "snatch");
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/snatch";
	}
	
	
	@RequestMapping("/upgrade_member.html")
	public String upgrade_member(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "升级会员");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "snatch");
			
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/upgrade_member";
	}
	
	@RequestMapping("/recharge.html")
	public String recharge(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "我要充值");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "snatch");
			
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/recharge";
	}
	
	
	@RequestMapping("/suggest.html")
	public String suggest(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "意见反馈");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "suggest");
			
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/suggest";
	}
	
	@RequestMapping("/company_login.html")
	public String company_login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "企业登录");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "suggest");
			
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/company_login";
	}
	
	
	@RequestMapping("/user_bind.html")
	public String user_bind(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "用户绑定");
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cat_code", "suggest");
			
			ReturnObject<Object> rm = new ReturnObject<Object>();
			rm.setAction("gdip_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gdip/user_bind";
	}
	
	
	
}
