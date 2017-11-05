package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.project.HaiBegOff;
import org.ehais.shop.service.ProjectBegOffService;
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

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="请假管理",value="projectBegOffController")
@Controller
@RequestMapping("/admin")
public class  ProjectBegOffAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ProjectBegOffAdminController.class);

	@Autowired
	private ProjectBegOffService projectBegOffService;
	
	/*
	@EPermissionMethod(name="查询",intro="打开请假页面",value="projectBegOffView",relation="projectBegOffListJson",type=PermissionProtocol.URL)
	@RequestMapping("/projectBegOffView")
	public String projectBegOffView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/begoff/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回请假数据",value="projectBegOffListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/projectBegOffListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String projectBegOffListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "begOffName", required = false) String begOffName) {
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_list_json(request, condition,keySubId,begOffName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJSON(e);
		}
	}
	*/
	
	
	@EPermissionMethod(name="新增",intro="新增请假",value="projectBegOffAddDetail",relation="projectBegOffAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/projectBegOffAddDetail",method=RequestMethod.GET)
	public String projectBegOffAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/begoff/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交请假",value="projectBegOffAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/projectBegOffAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String projectBegOffAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("begoff") HaiBegOff begoff,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_insert_submit(request, begoff);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑请假",value="projectBegOffEditDetail",relation="projectBegOffEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/projectBegOffEditDetail",method=RequestMethod.GET)
	public String projectBegOffEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "begOffId", required = true) Integer begOffId
			) {
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_update(request,begOffId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/begoff/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交请假",value="projectBegOffEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/projectBegOffEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String projectBegOffEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("begoff") HaiBegOff begoff,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(projectBegOffService.begoff_update_submit(request,begoff));
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除请假",value="projectBegOffDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/projectBegOffDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String projectBegOffDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "begOffId", required = true) Integer begOffId
			) {
		try{
			return this.writeJson(projectBegOffService.begoff_delete(request, begOffId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJSON(e);
		}
	}
	
	


}


