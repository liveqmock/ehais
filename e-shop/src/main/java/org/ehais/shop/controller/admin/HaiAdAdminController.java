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
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdPosition;
import org.ehais.shop.service.HaiAdService;
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

@EPermissionController(intro="广告管理功能",value="haiAdAdminController")
@Controller
@RequestMapping("/admin")
public class  HaiAdAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiAdAdminController.class);

	@Autowired
	private HaiAdService haiAdService;
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
	
	@EPermissionMethod(intro="打开广告管理页面",value="haiAdView",type=PermissionProtocol.URL)
	@RequestMapping("/haiAdView")
	public String haiAdView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiAd> rm = haiAdService.ad_list(request);
			modelMap.addAttribute("rm", rm);
			return "/admin/ad/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回广告管理数据",value="haiAdListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiAdListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "positionId", required = false) Integer positionId,
			@RequestParam(value = "adName", required = false) String adName) {
		try{
			ReturnObject<HaiAd> rm = haiAdService.ad_list_json(request, condition,positionId,adName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增广告管理",value="haiAdAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdAddDetail",method=RequestMethod.GET)
	public String haiAdAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAd> rm = haiAdService.ad_insert(request);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/admin/ad/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交广告管理",value="haiAdAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("ad") HaiAd ad,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiAd> rm = haiAdService.ad_insert_submit(request, ad);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑广告管理",value="haiAdEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdEditDetail",method=RequestMethod.GET)
	public String haiAdEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adId", required = true) Integer adId
			) {
		try{
			ReturnObject<HaiAd> rm = haiAdService.ad_update(request,adId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/admin/ad/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交广告管理",value="haiAdEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("ad") HaiAd ad,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAdService.ad_update_submit(request,ad));
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除广告管理",value="haiAdDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adId", required = true) Integer adId
			) {
		try{
			return this.writeJson(haiAdService.ad_delete(request, adId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
			return this.errorJSON(e);
		}
	}
	
	


	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(intro="打开广告管理页面",value="haiAdPositionView",type=PermissionProtocol.URL)
	@RequestMapping("/haiAdPositionView")
	public String haiAdPositionView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiAdPosition> rm = haiAdService.adposition_list(request);
			modelMap.addAttribute("rm", rm);
			return "/admin/ad/adposition_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回广告管理数据",value="haiAdPositionListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiAdPositionListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdPositionListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "positionName", required = false) String positionName) {
		try{
			ReturnObject<HaiAdPosition> rm = haiAdService.adposition_list_json(request, condition,positionName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增广告管理",value="haiAdPositionAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdPositionAddDetail",method=RequestMethod.GET)
	public String haiAdPositionAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAdPosition> rm = haiAdService.adposition_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/admin/ad/adposition_detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交广告管理",value="haiAdPositionAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdPositionAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdPositionAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("adposition") HaiAdPosition adposition,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiAdPosition> rm = haiAdService.adposition_insert_submit(request, adposition);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑广告管理",value="haiAdPositionEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdPositionEditDetail",method=RequestMethod.POST)
	public String haiAdPositionEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "positionId", required = true) Integer positionId
			) {
		try{
			ReturnObject<HaiAdPosition> rm = haiAdService.adposition_update(request,positionId);
			return this.writeJson(rm);
//			modelMap.addAttribute("rm", rm);
//			return "/admin/ad/adposition_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交广告管理",value="haiAdPositionEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAdPositionEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdPositionEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("adposition") HaiAdPosition adposition,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAdService.adposition_update_submit(request,adposition));
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除广告管理",value="haiAdPositionDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiAdPositionDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAdPositionDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "positionId", required = true) Integer positionId
			) {
		try{
			return this.writeJson(haiAdService.adposition_delete(request, positionId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
			return this.errorJSON(e);
		}
	}
	
}


