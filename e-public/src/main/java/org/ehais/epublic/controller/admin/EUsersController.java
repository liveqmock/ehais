package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.service.EUsersService;
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
public class  EUsersController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EUsersController.class);

	@Autowired
	private EUsersService eUsersService;
	
	
	@RequestMapping("/users_list")
	public String users_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "users_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return "/admin/users/list";
	}
	
	@ResponseBody
	@RequestMapping("/users_list_json")
	public String users_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	

	@RequestMapping("/users_insert")
	public String users_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_insert(request);
			rm.setAction("users_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return "/admin/users/info";
	}
	
	@RequestMapping(value="/users_insert_submit",method=RequestMethod.POST)
	public String users_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiUsers users
			) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_insert_submit(request,users);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "users_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return "redirect:users_insert";
	}
	
	@RequestMapping("/users_update")
	public String users_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userId", required = true) Long userId
			) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_update(request, userId);
			rm.setAction("users_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return "/admin/users/info";
	}
	
	@RequestMapping(value="/users_update_submit",method=RequestMethod.POST)
	public String users_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiUsers users
			) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_update_submit(request,users);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "users_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return "/admin/users/info";
	}
	
	
	@RequestMapping("/users_delete")
	public String users_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_delete(request, userId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "users_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
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


