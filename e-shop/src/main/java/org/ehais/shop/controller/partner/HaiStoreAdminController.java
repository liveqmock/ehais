package org.ehais.shop.controller.partner;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.HaiStoreService;
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



@EPermissionController(intro="商户管理功能",value="haiStoreController")
@Controller
@RequestMapping("/ehais")
public class  HaiStoreAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiStoreAdminController.class);

	@Autowired
	private HaiStoreService haiStoreService;
	
	
	@EPermissionMethod(intro="打开商户管理页面",value="haiStoreView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiStoreView")
	public String haiStoreView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getPartnerTheme(request)+"/store/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回商户管理数据",value="haiStoreListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiStoreListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "storeName", required = false) String storeName) {
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_list_json(request, condition,keySubId,storeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增商户管理",value="haiStoreAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreAddDetail",method=RequestMethod.GET)
	public String haiStoreAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getPartnerTheme(request)+"/store/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商户管理",value="haiStoreAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiStoreAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("store") EHaiStore store,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiStore> rm = haiStoreService.store_insert_submit(request, store);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑商户管理",value="haiStoreEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreEditDetail",method=RequestMethod.GET)
	public String haiStoreEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "storeId", required = true) Integer storeId
			) {
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_update(request,storeId);
			modelMap.addAttribute("rm", rm);
			return "/admin/store/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商户管理",value="haiStoreEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiStoreEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("store") EHaiStore store,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiStoreService.store_update_submit(request,store));
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商户管理",value="haiStoreDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "storeId", required = true) Integer storeId
			) {
		try{
			return this.writeJson(haiStoreService.store_delete(request, storeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
		
}


