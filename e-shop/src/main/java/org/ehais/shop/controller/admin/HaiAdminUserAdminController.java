package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.HaiAdminUserService;
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



@EPermissionController(intro="管理员功能",value="haiAdminUserController")
@Controller
@RequestMapping("/admin")
public class  HaiAdminUserAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiAdminUserAdminController.class);

	@Autowired
	private HaiAdminUserService haiAdminUserService;
	
	
	@EPermissionMethod(intro="打开管理员页面",value="haiAdminUserView",type=PermissionProtocol.URL)
	@RequestMapping("/haiAdminUserView")
	public String haiAdminUserView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiAdminUser> rm = haiAdminUserService.adminuser_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/adminuser/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回管理员数据",value="haiAdminUserListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiAdminUserListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdminUserListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Long keySubId,
			@RequestParam(value = "userName", required = false) String userName) {
		try{
			ReturnObject<EHaiAdminUser> rm = haiAdminUserService.adminuser_list_json(request, condition,keySubId,userName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增管理员",value="haiAdminUserAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdminUserAddDetail",method=RequestMethod.GET)
	public String haiAdminUserAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiAdminUserWithBLOBs> rm = haiAdminUserService.adminuser_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/adminuser/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交管理员",value="haiAdminUserAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdminUserAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdminUserAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("adminuser") EHaiAdminUserWithBLOBs adminuser,
			BindingResult result,
			@RequestParam(value = "roleId", required = true) String roleId
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiAdminUserWithBLOBs> rm = haiAdminUserService.adminuser_insert_submit(request, adminuser,roleId);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑管理员",value="haiAdminUserEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdminUserEditDetail",method=RequestMethod.GET)
	public String haiAdminUserEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adminId", required = true) Long adminId
			) {
		try{
			ReturnObject<EHaiAdminUserWithBLOBs> rm = haiAdminUserService.adminuser_update(request,adminId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/adminuser/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交管理员",value="haiAdminUserEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdminUserEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdminUserEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("adminuser") EHaiAdminUserWithBLOBs adminuser,
			BindingResult result,
			@RequestParam(value = "roleId", required = true) String roleId
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAdminUserService.adminuser_update_submit(request,adminuser,roleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除管理员",value="haiAdminUserDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdminUserDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdminUserDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adminId", required = true) Long adminId
			) {
		try{
			return this.writeJson(haiAdminUserService.adminuser_delete(request, adminId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("adminuser", e);
			return this.errorJSON(e);
		}
	}
	
	


}


