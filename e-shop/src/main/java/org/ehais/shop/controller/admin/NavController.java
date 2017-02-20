package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.service.NavService;
import org.ehais.tools.ReturnObject;
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

@Controller
@RequestMapping("/admin")
public class  NavController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(NavController.class);

	@Autowired
	private NavService navService;
	
	
	@RequestMapping("/nav_list")
	public String nav_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "nav_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return "/admin/nav/list";
	}
	
	@ResponseBody
	@RequestMapping("/nav_list_json")
	public String nav_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return null;
	}
	
	

	@RequestMapping("/nav_insert")
	public String nav_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_insert(request);
			rm.setAction("nav_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return "/admin/nav/info";
	}
	
	@RequestMapping(value="/nav_insert_submit",method=RequestMethod.POST)
	public String nav_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiNav nav
			) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_insert_submit(request,nav);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "nav_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return "redirect:nav_insert";
	}
	
	@RequestMapping("/nav_update")
	public String nav_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_update(request, id);
			rm.setAction("nav_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return "/admin/nav/info";
	}
	
	@RequestMapping(value="/nav_update_submit",method=RequestMethod.POST)
	public String nav_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiNav nav
			) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_update_submit(request,nav);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "nav_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return "/admin/nav/info";
	}
	
	
	@RequestMapping("/nav_delete")
	public String nav_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiNav> rm = navService.nav_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "nav_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
		}
		return null;
	}
	
	/**
	

	@ResponseBody
	@RequestMapping("/controller{store_id}")
	public String controller(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@RequestParam(value = "code", required = true) String code){
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	22///////////////////////////////////////////////////	
	@ResponseBody
	@RequestMapping("/_list")
	public String _list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(service._list(request,store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	**/
	
}


