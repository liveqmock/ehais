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
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessWithBLOBs;
import org.ehais.shop.service.HaiBusinessService;
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

@EPermissionController(name="供应商信息管理",value="haiSupplierController")
@Controller
@RequestMapping("/admin")
public class  HaiSupplierAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiSupplierAdminController.class);

	@Autowired
	private HaiBusinessService haiBusinessService;
	
	
	@EPermissionMethod(name="查询",intro="打开供应商信息页面",value="haiSupplierView",relation="haiSupplierListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiSupplierView")
	public String haiSupplierView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBusiness> rm = haiBusinessService.business_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/supplier/view";
			//return this.view(request, "/supplier/view");
			return this.view(request, "/supplier/supplierView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回供应商信息数据",value="haiSupplierListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiSupplierListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSupplierListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "businessName", required = false) String businessName) {
		try{
			ReturnObject<HaiBusiness> rm = haiBusinessService.business_list_json(request, condition,keySubId,businessName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增供应商信息",value="haiSupplierAddDetail",relation="haiSupplierAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiSupplierAddDetail",method=RequestMethod.GET)
	public String haiSupplierAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/supplier/detail";
			return this.view(request, "/supplier/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交供应商信息",value="haiSupplierAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiSupplierAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSupplierAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("business") HaiBusinessWithBLOBs business,
			BindingResult result,
			@RequestParam(value = "linkManJson", required = true) String linkManJson
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_insert_submit(request, business,"supplier",linkManJson);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑供应商信息",value="haiSupplierEditDetail",relation="haiSupplierEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiSupplierEditDetail",method=RequestMethod.POST)
	public String haiSupplierEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "supplierId", required = true) Integer supplierId
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_update(request,supplierId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑供应商信息",value="haiSupplierEditDetail",relation="haiSupplierEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiSupplierEditDetail",method=RequestMethod.GET)
	public String haiSupplierEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "supplierId", required = true) Integer supplierId
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_update(request,supplierId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/supplier/detail";
			return this.view(request, "/supplier/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交供应商信息",value="haiSupplierEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiSupplierEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSupplierEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("business") HaiBusinessWithBLOBs business,
			BindingResult result,
			@RequestParam(value = "linkManJson", required = true) String linkManJson
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiBusinessService.business_update_submit(request,business,"supplier",linkManJson));
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除供应商信息",value="haiSupplierDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiSupplierDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSupplierDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "supplierId", required = true) Integer supplierId
			) {
		try{
			return this.writeJson(haiBusinessService.business_delete(request, supplierId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}

	


}


