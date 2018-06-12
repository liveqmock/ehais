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
import org.ehais.shop.model.HaiBusinessType;
import org.ehais.shop.service.HaiBusinessTypeService;
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

@EPermissionController(name="客户类别管理",value="haiCustomerTypeController")
@Controller
@RequestMapping("/admin")
public class  HaiCustomerTypeAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiCustomerTypeAdminController.class);

	@Autowired
	private HaiBusinessTypeService haiBusinessTypeService;
	
	
	@EPermissionMethod(name="查询",intro="打开往来单位页面",value="haiBusinessTypeView",relation="haiBusinessTypeListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiCustomerTypeView")
	public String haiCustomerTypeView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/businesstype/view";
			//return this.view(request, "/businesstype/view");
			return this.view(request, "/businesstype/businesstypeView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回往来单位数据",value="haiCustomerTypeListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiCustomerTypeListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCustomerTypeListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "businessTypeName", required = false) String businessTypeName) {
		try{
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_list_json(request, condition,keySubId,businessTypeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增往来单位",value="haiCustomerTypeAddDetail",relation="haiCustomerTypeAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiCustomerTypeAddDetail",method=RequestMethod.GET)
	public String haiCustomerTypeAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/businesstype/detail";
			return this.view(request, "/businesstype/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交往来单位",value="haiCustomerTypeAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiCustomerTypeAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCustomerTypeAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("businesstype") HaiBusinessType businesstype,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_insert_submit(request, businesstype);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑往来单位",value="haiCustomerTypeEditDetail",relation="haiCustomerTypeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiCustomerTypeEditDetail",method=RequestMethod.POST)
	public String haiCustomerTypeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessTypeId", required = true) Integer businessTypeId
			) {
		try{
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_update(request,businessTypeId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑往来单位",value="haiCustomerTypeEditDetail",relation="haiCustomerTypeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiCustomerTypeEditDetail",method=RequestMethod.GET)
	public String haiCustomerTypeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessTypeId", required = true) Integer businessTypeId
			) {
		try{
			ReturnObject<HaiBusinessType> rm = haiBusinessTypeService.businesstype_update(request,businessTypeId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/businesstype/detail";
			return this.view(request, "/businesstype/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交往来单位",value="haiCustomerTypeEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiCustomerTypeEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCustomerTypeEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("businesstype") HaiBusinessType businesstype,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiBusinessTypeService.businesstype_update_submit(request,businesstype));
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除往来单位",value="haiCustomerTypeDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiCustomerTypeDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCustomerTypeDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessTypeId", required = true) Integer businessTypeId
			) {
		try{
			return this.writeJson(haiBusinessTypeService.businesstype_delete(request, businessTypeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("businesstype", e);
			return this.errorJSON(e);
		}
	}
	
	


}


