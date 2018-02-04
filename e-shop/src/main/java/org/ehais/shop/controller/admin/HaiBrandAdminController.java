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
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.service.HaiBrandService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
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

@EPermissionController(name="品牌管理管理",value="haiBrandController")
@Controller
@RequestMapping("/admin")
public class  HaiBrandAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiBrandAdminController.class);

	@Autowired
	private HaiBrandService haiBrandService;
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
	@EPermissionMethod(name="查询",intro="打开品牌管理页面",value="haiBrandView",relation="haiBrandListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiBrandView")
	public String haiBrandView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBrand> rm = haiBrandService.brand_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/brand/view";
			return this.view(request, "/brand/view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回品牌管理数据",value="haiBrandListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiBrandListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBrandListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "brandName", required = false) String brandName) {
		try{
			ReturnObject<HaiBrand> rm = haiBrandService.brand_list_json(request, condition,keySubId,brandName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增品牌管理",value="haiBrandAddDetail",relation="haiBrandAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiBrandAddDetail",method=RequestMethod.GET)
	public String haiBrandAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBrand> rm = haiBrandService.brand_insert(request);
			modelMap.addAttribute("rm", rm);
			
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			
			//return "/"+this.getAdminProjectFolder(request)+"/brand/detail";
			return this.view(request, "/brand/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交品牌管理",value="haiBrandAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiBrandAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBrandAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("brand") HaiBrand brand,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiBrand> rm = haiBrandService.brand_insert_submit(request, brand);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑品牌管理",value="haiBrandEditDetail",relation="haiBrandEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiBrandEditDetail",method=RequestMethod.GET)
	public String haiBrandEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId", required = true) Integer brandId
			) {
		try{
			ReturnObject<HaiBrand> rm = haiBrandService.brand_update(request,brandId);
			modelMap.addAttribute("rm", rm);
			
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			
			//return "/"+this.getAdminProjectFolder(request)+"/brand/detail";
			return this.view(request, "/brand/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交品牌管理",value="haiBrandEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiBrandEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBrandEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("brand") HaiBrand brand,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiBrandService.brand_update_submit(request,brand));
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除品牌管理",value="haiBrandDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiBrandDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBrandDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId", required = true) Integer brandId
			) {
		try{
			return this.writeJson(haiBrandService.brand_delete(request, brandId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
			return this.errorJSON(e);
		}
	}
	
	


}


