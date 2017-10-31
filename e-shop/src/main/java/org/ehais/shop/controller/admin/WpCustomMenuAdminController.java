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
import org.ehais.shop.model.weixin.WpCustomMenu;
import org.ehais.shop.service.WpCustomMenuService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@EPermissionModuleGroup(name="微信模组")

@EPermissionController(name="菜单管理",intro="微信菜单管理功能",value="wpCustomMenuController")
@Controller
@RequestMapping("/admin")
public class  WpCustomMenuAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(WpCustomMenuAdminController.class);

	@Autowired
	private WpCustomMenuService wpCustomMenuService;
	
	private String weixin_menu_type = ResourceUtil.getProValue("weixin_menu_type");
	
	@EPermissionMethod(name="查询",intro="打开微信菜单管理页面",value="wpCustomMenuView",relation="wpCustomMenuListJson",type=PermissionProtocol.URL)
	@RequestMapping("/wpCustomMenuView")
	public String wpCustomMenuView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<WpCustomMenu> rm = wpCustomMenuService.custommenu_list(request);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("weixin_menu_type", weixin_menu_type);
			return "/"+this.getAdminProjectFolder(request)+"/custommenu/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回微信菜单管理数据",value="wpCustomMenuListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/wpCustomMenuListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpCustomMenuListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<WpCustomMenu> rm = wpCustomMenuService.custommenu_list_json(request, condition,keySubId,title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增微信菜单管理",value="wpCustomMenuAddDetail",relation="wpCustomMenuAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpCustomMenuAddDetail",method=RequestMethod.GET)
	public String wpCustomMenuAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<WpCustomMenu> rm = wpCustomMenuService.custommenu_insert(request);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("weixin_menu_type", JSONObject.fromObject(weixin_menu_type));
			
			return "/"+this.getAdminProjectFolder(request)+"/custommenu/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交微信菜单管理",value="wpCustomMenuAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/wpCustomMenuAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpCustomMenuAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("custommenu") WpCustomMenu custommenu,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<WpCustomMenu> rm = wpCustomMenuService.custommenu_insert_submit(request, custommenu);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑微信菜单管理",value="wpCustomMenuEditDetail",relation="wpCustomMenuEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpCustomMenuEditDetail",method=RequestMethod.GET)
	public String wpCustomMenuEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<WpCustomMenu> rm = wpCustomMenuService.custommenu_update(request,id);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("weixin_menu_type", JSONObject.fromObject(weixin_menu_type));
			return "/"+this.getAdminProjectFolder(request)+"/custommenu/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交微信菜单管理",value="wpCustomMenuEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/wpCustomMenuEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpCustomMenuEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("custommenu") WpCustomMenu custommenu,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wpCustomMenuService.custommenu_update_submit(request,custommenu));
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除微信菜单管理",value="wpCustomMenuDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpCustomMenuDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpCustomMenuDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			return this.writeJson(wpCustomMenuService.custommenu_delete(request, id));
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="同步",intro="同步微信菜单管理",value="wpCustomMenuDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wpCustomMenuSend",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wpCustomMenuSend(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			return this.writeJson(wpCustomMenuService.CreateMenu(request));
		}catch(Exception e){
			e.printStackTrace();
			log.error("custommenu", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


