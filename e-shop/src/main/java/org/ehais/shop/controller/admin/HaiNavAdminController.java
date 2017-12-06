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
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.service.HaiNavService;
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

@EPermissionController(name="导航管理管理",value="haiNavController")
@Controller
@RequestMapping("/admin")
public class  HaiNavAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiNavAdminController.class);

	@Autowired
	private HaiNavService haiNavService;
	
	
	@EPermissionMethod(name="查询",intro="打开导航管理页面",value="haiNavView",relation="haiNavListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiNavView")
	public String haiNavView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiNav> rm = haiNavService.nav_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/nav/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回导航管理数据",value="haiNavListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiNavListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiNavListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "name", required = false) String name) {
		try{
			ReturnObject<HaiNav> rm = haiNavService.nav_list_json(request, condition,keySubId,name);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增导航管理",value="haiNavAddDetail",relation="haiNavAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiNavAddDetail",method=RequestMethod.GET)
	public String haiNavAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiNav> rm = haiNavService.nav_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/nav/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交导航管理",value="haiNavAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiNavAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiNavAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("nav") HaiNav nav,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiNav> rm = haiNavService.nav_insert_submit(request, nav);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑导航管理",value="haiNavEditDetail",relation="haiNavEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiNavEditDetail",method=RequestMethod.GET)
	public String haiNavEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<HaiNav> rm = haiNavService.nav_update(request,id);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/nav/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交导航管理",value="haiNavEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiNavEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiNavEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("nav") HaiNav nav,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiNavService.nav_update_submit(request,nav));
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除导航管理",value="haiNavDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiNavDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiNavDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			return this.writeJson(haiNavService.nav_delete(request, id));
		}catch(Exception e){
			e.printStackTrace();
			log.error("nav", e);
			return this.errorJSON(e);
		}
	}
	
	


}


