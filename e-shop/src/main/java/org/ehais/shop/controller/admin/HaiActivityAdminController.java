package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiActivity;
import org.ehais.shop.service.HaiActivityService;
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



@EPermissionController(intro="活动会议管理功能",value="haiActivityController")
@Controller
@RequestMapping("/admin")
public class  HaiActivityAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiActivityAdminController.class);
	protected static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	protected static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	protected static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	protected static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	@Autowired
	private HaiActivityService haiActivityService;
	
	
	@EPermissionMethod(intro="打开活动会议管理页面",value="haiActivityView",type=PermissionProtocol.URL)
	@RequestMapping("/haiActivityView")
	public String haiActivityView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module ) {	
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_list(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getStoreTheme(request)+"/activity/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回活动会议管理数据",value="haiActivityListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiActivityListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "catId", required = false) Integer catId,
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "module", required = true) String module) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_list_json(request,module, condition,catId,activityName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增活动会议管理",value="haiActivityAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityAddDetail",method=RequestMethod.GET)
	public String haiActivityAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_insert(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getStoreTheme(request)+"/activity/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交活动会议管理",value="haiActivityAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActivityAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("activity") HaiActivity activity,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiActivity> rm = haiActivityService.activity_insert_submit(request,module, activity);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑活动会议管理",value="haiActivityEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityEditDetail",method=RequestMethod.GET)
	public String haiActivityEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_update(request,module,activityId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getStoreTheme(request)+"/activity/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交活动会议管理",value="haiActivityEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActivityEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("activity") HaiActivity activity,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiActivityService.activity_update_submit(request,module,activity));
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除活动会议管理",value="haiActivityDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			return this.writeJson(haiActivityService.activity_delete(request,module, activityId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	


	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(intro="打开活动会议管理页面",value="haiActicityCatView",type=PermissionProtocol.URL)
	@RequestMapping("/haiActicityCatView")
	public String haiActicityCatView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module ) {	
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_list(request,module);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/activity/articlecat_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回活动会议管理数据",value="haiActicityCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiActicityCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "catName", required = false) String catName,
			@RequestParam(value = "module", required = true) String module) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_list_json(request,module, condition,catName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增活动会议管理",value="haiActicityCatAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatAddDetail",method=RequestMethod.GET)
	public String haiActicityCatAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_insert(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getStoreTheme(request)+"/activity/articlecat_detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交活动会议管理",value="haiActicityCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActicityCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_insert_submit(request,module, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑活动会议管理",value="haiActicityCatEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatEditDetail",method=RequestMethod.GET)
	public String haiActicityCatEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_update(request,module,catId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getStoreTheme(request)+"/activity/articlecat_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交活动会议管理",value="haiActicityCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActicityCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiActivityService.articlecat_update_submit(request,module,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除活动会议管理",value="haiActicityCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			return this.writeJson(haiActivityService.articlecat_delete(request,module, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
}


