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
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.shop.service.HaiWarehouseService;
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

@EPermissionController(name="仓库信息管理",value="haiWarehouseController")
@Controller
@RequestMapping("/admin")
public class  HaiWarehouseAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiWarehouseAdminController.class);

	@Autowired
	private HaiWarehouseService haiWarehouseService;
	
	
	@EPermissionMethod(name="查询",intro="打开仓库信息页面",value="haiWarehouseView",relation="haiWarehouseListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiWarehouseView")
	public String haiWarehouseView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/warehouse/view";
			//return this.view(request, "/warehouse/view");
			return this.view(request, "/warehouse/warehouseView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回仓库信息数据",value="haiWarehouseListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiWarehouseListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "warehouseName", required = false) String warehouseName) {
		try{
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_list_json(request, condition,keySubId,warehouseName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增仓库信息",value="haiWarehouseAddDetail",relation="haiWarehouseAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiWarehouseAddDetail",method=RequestMethod.GET)
	public String haiWarehouseAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/warehouse/detail";
			return this.view(request, "/warehouse/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交仓库信息",value="haiWarehouseAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWarehouseAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("warehouse") HaiWarehouse warehouse,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_insert_submit(request, warehouse);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑仓库信息",value="haiWarehouseEditDetail",relation="haiWarehouseEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiWarehouseEditDetail",method=RequestMethod.POST)
	public String haiWarehouseEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "warehouseId", required = true) Integer warehouseId
			) {
		try{
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_update(request,warehouseId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑仓库信息",value="haiWarehouseEditDetail",relation="haiWarehouseEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiWarehouseEditDetail",method=RequestMethod.GET)
	public String haiWarehouseEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "warehouseId", required = true) Integer warehouseId
			) {
		try{
			ReturnObject<HaiWarehouse> rm = haiWarehouseService.warehouse_update(request,warehouseId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/warehouse/detail";
			return this.view(request, "/warehouse/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交仓库信息",value="haiWarehouseEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWarehouseEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("warehouse") HaiWarehouse warehouse,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiWarehouseService.warehouse_update_submit(request,warehouse));
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除仓库信息",value="haiWarehouseDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiWarehouseDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "warehouseId", required = true) Integer warehouseId
			) {
		try{
			return this.writeJson(haiWarehouseService.warehouse_delete(request, warehouseId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}

	


}


