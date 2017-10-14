package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.WpPublicService;
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



@EPermissionController(intro="微信公众号管理功能",value="wpPublicController")
@Controller
@RequestMapping("/admin")
public class  WpPublicAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(WpPublicAdminController.class);

	@Autowired
	private WpPublicService wpPublicService;
	
	
	@EPermissionMethod(intro="打开微信公众号管理页面",value="wpPublicView",type=PermissionProtocol.URL)
	@RequestMapping("/wpPublicView")
	public String wpPublicView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<WpPublic> rm = wpPublicService.public_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/public/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回微信公众号管理数据",value="wpPublicListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/wpPublicListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpPublicListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "publicName", required = false) String publicName) {
		try{
			ReturnObject<WpPublic> rm = wpPublicService.public_list_json(request, condition,keySubId,publicName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增微信公众号管理",value="wpPublicAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpPublicAddDetail",method=RequestMethod.GET)
	public String wpPublicAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<WpPublicWithBLOBs> rm = wpPublicService.public_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/public/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交微信公众号管理",value="wpPublicAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/wpPublicAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpPublicAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("wpPublic") WpPublicWithBLOBs wpPublic,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<WpPublicWithBLOBs> rm = wpPublicService.public_insert_submit(request, wpPublic);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑微信公众号管理",value="wpPublicEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpPublicEditDetail",method=RequestMethod.GET)
	public String wpPublicEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<WpPublicWithBLOBs> rm = wpPublicService.public_update(request,id);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/public/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交微信公众号管理",value="wpPublicEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/wpPublicEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpPublicEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("wpPublic") WpPublicWithBLOBs wpPublic,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wpPublicService.public_update_submit(request,wpPublic));
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除微信公众号管理",value="wpPublicDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpPublicDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpPublicDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			return this.writeJson(wpPublicService.public_delete(request, id));
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJSON(e);
		}
	}
	
	

	@EPermissionMethod(name="编辑",intro="编辑微信公众号管理",value="wpPublicEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpPublicDetail",method=RequestMethod.GET)
	public String wpPublicDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<WpPublicWithBLOBs> rm = wpPublicService.public_detail(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/public/public_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="编辑提交微信公众号管理",value="wpPublicEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/wpPublicDetailSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpPublicDetailSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("wpPublic") WpPublicWithBLOBs wpPublic,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wpPublicService.public_detail_submit(request,wpPublic));
		}catch(Exception e){
			e.printStackTrace();
			log.error("public", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


