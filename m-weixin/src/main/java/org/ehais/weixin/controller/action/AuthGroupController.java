package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpAuthGroup;
import org.ehais.weixin.model.WpAuthGroupWithBLOBs;
import org.ehais.weixin.service.action.AuthGroupService;
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
@RequestMapping("/member")
public class  AuthGroupController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(AuthGroupController.class);

	@Autowired
	private AuthGroupService authgroupService;
	
	
	@RequestMapping("/authgroup_list")
	public String authgroup_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "authgroup_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "/action/authgroup/list";
	}
	
	@ResponseBody
	@RequestMapping("/authgroup_list_json")
	public String authgroup_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<WpAuthGroup> rm = authgroupService.authgroup_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return null;
	}
	
	

	@RequestMapping("/authgroup_insert")
	public String authgroup_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<WpAuthGroupWithBLOBs> rm = authgroupService.authgroup_insert(request);
			rm.setAction("authgroup_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "/action/authgroup/info";
	}
	
	@RequestMapping(value="/authgroup_insert_submit",method=RequestMethod.POST)
	public String authgroup_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpAuthGroupWithBLOBs authgroup
			) {
		try{
			ReturnObject<WpAuthGroupWithBLOBs> rm = authgroupService.authgroup_insert_submit(request,authgroup);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "authgroup_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "redirect:authgroup_insert";
	}
	
	@RequestMapping("/authgroup_update")
	public String authgroup_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<WpAuthGroupWithBLOBs> rm = authgroupService.authgroup_update(request, id);
			rm.setAction("authgroup_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "/action/authgroup/info";
	}
	
	@RequestMapping(value="/authgroup_update_submit",method=RequestMethod.POST)
	public String authgroup_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpAuthGroupWithBLOBs authgroup
			) {
		try{
			ReturnObject<WpAuthGroupWithBLOBs> rm = authgroupService.authgroup_update_submit(request,authgroup);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "authgroup_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "/action/authgroup/info";
	}
	
	
	@RequestMapping("/authgroup_delete")
	public String authgroup_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<WpAuthGroup> rm = authgroupService.authgroup_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "authgroup_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/authgroup_wx_create",method=RequestMethod.POST)
	public String authgroup_wx_create(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<WpAuthGroupWithBLOBs> rm = authgroupService.authgroup_wx_create(request,id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("authgroup", e);
		}
		return "null";
	}
	
	
}


