package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.enums.EStoreDistributionTypeEnum;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiStoreSetting;
import org.ehais.shop.service.HaiStoreSettingService;
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



@EPermissionController(intro="分销设置功能",value="haiStoreSettingController")
@Controller
@RequestMapping("/ehais")
public class  HaiStoreSettingAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiStoreSettingAdminController.class);

	@Autowired
	private HaiStoreSettingService haiStoreSettingService;
	
	
	@EPermissionMethod(intro="打开分销设置页面",value="haiStoreSettingView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiStoreSettingView")
	public String haiStoreSettingView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/storesetting/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回分销设置数据",value="haiStoreSettingListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiStoreSettingListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreSettingListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "storeName", required = false) String storeName) {
		try{
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_list_json(request, condition,keySubId,storeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增分销设置",value="haiStoreSettingAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreSettingAddDetail",method=RequestMethod.GET)
	public String haiStoreSettingAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/storesetting/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交分销设置",value="haiStoreSettingAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiStoreSettingAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreSettingAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("storesetting") HaiStoreSetting storesetting,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_insert_submit(request, storesetting);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑分销设置",value="haiStoreSettingEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreSettingEditDetail",method=RequestMethod.GET)
	public String haiStoreSettingEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "storeId", required = true) Integer storeId
			) {
		try{
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_update(request,storeId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/storesetting/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@EPermissionMethod(name="编辑",intro="编辑分销设置",value="haiStoreSettingEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreSettingDetail",method=RequestMethod.GET)
	public String haiStoreSettingDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiStoreSetting> rm = haiStoreSettingService.storesetting_info(request,null);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("distributionTypeMap", EStoreDistributionTypeEnum.map);
			return "/"+this.getStoreTheme(request)+"/storesetting/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交分销设置",value="haiStoreSettingEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiStoreSettingEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreSettingEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("storesetting") HaiStoreSetting storesetting,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiStoreSettingService.storesetting_update_submit(request,storesetting));
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除分销设置",value="haiStoreSettingDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreSettingDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreSettingDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "storeId", required = true) Integer storeId
			) {
		try{
			return this.writeJson(haiStoreSettingService.storesetting_delete(request, storeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("storesetting", e);
			return this.errorJSON(e);
		}
	}
	
	


}


