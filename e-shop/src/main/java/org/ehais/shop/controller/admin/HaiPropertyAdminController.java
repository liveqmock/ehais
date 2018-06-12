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
import org.ehais.shop.model.HaiProperty;
import org.ehais.shop.service.HaiPropertyService;
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

@EPermissionController(name="固定资产信息管理",value="haiPropertyController")
@Controller
@RequestMapping("/admin")
public class  HaiPropertyAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiPropertyAdminController.class);

	@Autowired
	private HaiPropertyService haiPropertyService;
	
	
	@EPermissionMethod(name="查询",intro="打开固定资产信息页面",value="haiPropertyView",relation="haiPropertyListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiPropertyView")
	public String haiPropertyView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiProperty> rm = haiPropertyService.property_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/property/view";
			//return this.view(request, "/property/view");
			return this.view(request, "/property/propertyView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回固定资产信息数据",value="haiPropertyListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiPropertyListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPropertyListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "propertyName", required = false) String propertyName) {
		try{
			ReturnObject<HaiProperty> rm = haiPropertyService.property_list_json(request, condition,keySubId,propertyName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增固定资产信息",value="haiPropertyAddDetail",relation="haiPropertyAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiPropertyAddDetail",method=RequestMethod.GET)
	public String haiPropertyAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiProperty> rm = haiPropertyService.property_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/property/detail";
			return this.view(request, "/property/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交固定资产信息",value="haiPropertyAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPropertyAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPropertyAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("property") HaiProperty property,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiProperty> rm = haiPropertyService.property_insert_submit(request, property);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑固定资产信息",value="haiPropertyEditDetail",relation="haiPropertyEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiPropertyEditDetail",method=RequestMethod.POST)
	public String haiPropertyEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "propertyId", required = true) Integer propertyId
			) {
		try{
			ReturnObject<HaiProperty> rm = haiPropertyService.property_update(request,propertyId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑固定资产信息",value="haiPropertyEditDetail",relation="haiPropertyEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiPropertyEditDetail",method=RequestMethod.GET)
	public String haiPropertyEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "propertyId", required = true) Integer propertyId
			) {
		try{
			ReturnObject<HaiProperty> rm = haiPropertyService.property_update(request,propertyId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/property/detail";
			return this.view(request, "/property/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交固定资产信息",value="haiPropertyEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPropertyEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPropertyEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("property") HaiProperty property,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiPropertyService.property_update_submit(request,property));
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除固定资产信息",value="haiPropertyDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiPropertyDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPropertyDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "propertyId", required = true) Integer propertyId
			) {
		try{
			return this.writeJson(haiPropertyService.property_delete(request, propertyId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("property", e);
			return this.errorJSON(e);
		}
	}

	


}


