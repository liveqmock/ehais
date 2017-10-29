package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.ThinkRole;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.ThinkRoleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="角色功能",value="thinkRoleController")
@Controller
@RequestMapping("/admin")
public class  ThinkRoleAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ThinkRoleAdminController.class);

	@Autowired
	private ThinkRoleService thinkRoleService;
	
	
	@EPermissionMethod(intro="打开角色页面",value="thinkRoleView",type=PermissionProtocol.URL)
	@RequestMapping("/thinkRoleView")
	public String thinkRoleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回角色数据",value="thinkRoleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/thinkRoleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "name", required = false) String name) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_list_json(request, condition,keySubId,name);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增角色",value="thinkRoleAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleAddDetail",method=RequestMethod.GET)
	public String thinkRoleAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交角色",value="thinkRoleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/thinkRoleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("role") ThinkRole role,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<ThinkRole> rm = thinkRoleService.role_insert_submit(request, role);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑角色",value="thinkRoleEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleEditDetail",method=RequestMethod.GET)
	public String thinkRoleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId
			) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_update(request,roleId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交角色",value="thinkRoleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/thinkRoleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("role") ThinkRole role,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(thinkRoleService.role_update_submit(request,role));
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除角色",value="thinkRoleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId
			) {
		try{
			return this.writeJson(thinkRoleService.role_delete(request, roleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	


}


