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
import org.ehais.shop.model.HaiLabour;
import org.ehais.shop.service.HaiLabourService;
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

@EPermissionController(name="劳务信息管理",value="haiLabourController")
@Controller
@RequestMapping("/admin")
public class  HaiLabourAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiLabourAdminController.class);

	@Autowired
	private HaiLabourService haiLabourService;
	
	
	@EPermissionMethod(name="查询",intro="打开劳务信息页面",value="haiLabourView",relation="haiLabourListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiLabourView")
	public String haiLabourView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiLabour> rm = haiLabourService.labour_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/labour/view";
			//return this.view(request, "/labour/view");
			return this.view(request, "/labour/labourView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回劳务信息数据",value="haiLabourListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiLabourListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiLabourListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "labourName", required = false) String labourName) {
		try{
			ReturnObject<HaiLabour> rm = haiLabourService.labour_list_json(request, condition,keySubId,labourName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增劳务信息",value="haiLabourAddDetail",relation="haiLabourAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiLabourAddDetail",method=RequestMethod.GET)
	public String haiLabourAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiLabour> rm = haiLabourService.labour_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/labour/detail";
			return this.view(request, "/labour/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交劳务信息",value="haiLabourAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiLabourAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiLabourAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("labour") HaiLabour labour,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiLabour> rm = haiLabourService.labour_insert_submit(request, labour);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑劳务信息",value="haiLabourEditDetail",relation="haiLabourEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiLabourEditDetail",method=RequestMethod.POST)
	public String haiLabourEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "labourId", required = true) Integer labourId
			) {
		try{
			ReturnObject<HaiLabour> rm = haiLabourService.labour_update(request,labourId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑劳务信息",value="haiLabourEditDetail",relation="haiLabourEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiLabourEditDetail",method=RequestMethod.GET)
	public String haiLabourEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "labourId", required = true) Integer labourId
			) {
		try{
			ReturnObject<HaiLabour> rm = haiLabourService.labour_update(request,labourId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/labour/detail";
			return this.view(request, "/labour/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交劳务信息",value="haiLabourEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiLabourEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiLabourEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("labour") HaiLabour labour,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiLabourService.labour_update_submit(request,labour));
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除劳务信息",value="haiLabourDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiLabourDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiLabourDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "labourId", required = true) Integer labourId
			) {
		try{
			return this.writeJson(haiLabourService.labour_delete(request, labourId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("labour", e);
			return this.errorJSON(e);
		}
	}

	


}


