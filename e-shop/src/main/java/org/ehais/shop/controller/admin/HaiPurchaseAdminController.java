package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiPurchase;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.shop.service.HaiPurchaseService;
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



@EPermissionController(intro="进货管理功能",value="haiPurchaseController")
@Controller
@RequestMapping("/admin")
public class  HaiPurchaseAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiPurchaseAdminController.class);

	@Autowired
	private HaiPurchaseService haiPurchaseService;
	
	
	@EPermissionMethod(intro="打开进货管理页面",value="haiPurchaseView",type=PermissionProtocol.URL)
	@RequestMapping("/haiPurchaseView")
	public String haiPurchaseView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiPurchase> rm = haiPurchaseService.purchase_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回进货管理数据",value="haiPurchaseListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiPurchaseListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPurchaseListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "warehouseId", required = false) Integer warehouseId,
			@RequestParam(value = "purchaseNo", required = false) String purchaseNo) {
		try{
			ReturnObject<HaiPurchase> rm = haiPurchaseService.purchase_list_json(request, condition,warehouseId,purchaseNo);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增进货管理",value="haiPurchaseAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPurchaseAddDetail",method=RequestMethod.GET)
	public String haiPurchaseAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiPurchase> rm = haiPurchaseService.purchase_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交进货管理",value="haiPurchaseAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPurchaseAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPurchaseAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("purchase") HaiPurchase purchase,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiPurchase> rm = haiPurchaseService.purchase_insert_submit(request, purchase);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑进货管理",value="haiPurchaseEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPurchaseEditDetail",method=RequestMethod.GET)
	public String haiPurchaseEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "purchaseId", required = true) Integer purchaseId
			) {
		try{
			ReturnObject<HaiPurchase> rm = haiPurchaseService.purchase_update(request,purchaseId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交进货管理",value="haiPurchaseEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPurchaseEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPurchaseEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("purchase") HaiPurchase purchase,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiPurchaseService.purchase_update_submit(request,purchase));
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除进货管理",value="haiPurchaseDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPurchaseDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPurchaseDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "purchaseId", required = true) Integer purchaseId
			) {
		try{
			return this.writeJson(haiPurchaseService.purchase_delete(request, purchaseId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("purchase", e);
			return this.errorJSON(e);
		}
	}
	
	


	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(intro="打开进货管理页面",value="haiWarehouseView",type=PermissionProtocol.URL)
	@RequestMapping("/haiWarehouseView")
	public String haiWarehouseView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiWarehouse> rm = haiPurchaseService.warehouse_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/warehouse_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回进货管理数据",value="haiWarehouseListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiWarehouseListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "warehouseName", required = false) String warehouseName) {
		try{
			ReturnObject<HaiWarehouse> rm = haiPurchaseService.warehouse_list_json(request, condition,warehouseName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增进货管理",value="haiWarehouseAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWarehouseAddDetail",method=RequestMethod.GET)
	public String haiWarehouseAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiWarehouse> rm = haiPurchaseService.warehouse_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/warehouse_detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交进货管理",value="haiWarehouseAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWarehouseAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("warehouse") HaiWarehouse warehouse,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiWarehouse> rm = haiPurchaseService.warehouse_insert_submit(request, warehouse);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑进货管理",value="haiWarehouseEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWarehouseEditDetail",method=RequestMethod.GET)
	public String haiWarehouseEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "warehouseId", required = true) Integer warehouseId
			) {
		try{
			ReturnObject<HaiWarehouse> rm = haiPurchaseService.warehouse_update(request,warehouseId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/purchase/warehouse_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交进货管理",value="haiWarehouseEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWarehouseEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("warehouse") HaiWarehouse warehouse,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiPurchaseService.warehouse_update_submit(request,warehouse));
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除进货管理",value="haiWarehouseDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWarehouseDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWarehouseDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "warehouseId", required = true) Integer warehouseId
			) {
		try{
			return this.writeJson(haiPurchaseService.warehouse_delete(request, warehouseId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("warehouse", e);
			return this.errorJSON(e);
		}
	}
	
}


