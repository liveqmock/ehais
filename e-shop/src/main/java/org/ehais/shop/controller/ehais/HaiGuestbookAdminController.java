package org.ehais.shop.controller.ehais;



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
import org.ehais.shop.model.HaiGuestbook;
import org.ehais.shop.model.HaiGuestbookWithBLOBs;
import org.ehais.shop.service.HaiGuestbookService;
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

@EPermissionController(name="招商代理管理",value="haiGuestbookController")
@Controller
@RequestMapping("/ehais/manage")
public class  HaiGuestbookAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiGuestbookAdminController.class);

	@Autowired
	private HaiGuestbookService haiGuestbookService;
	
	
	@EPermissionMethod(name="查询",intro="打开招商代理页面",value="haiGuestbookView",relation="haiGuestbookListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiGuestbookView")
	public String haiGuestbookView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGuestbook> rm = haiGuestbookService.guestbook_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/guestbook/view";
//			return this.view(request, "/guestbook/view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回招商代理数据",value="haiGuestbookListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiGuestbookListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGuestbookListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<HaiGuestbook> rm = haiGuestbookService.guestbook_list_json(request, condition,keySubId,title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增招商代理",value="haiGuestbookAddDetail",relation="haiGuestbookAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiGuestbookAddDetail",method=RequestMethod.GET)
	public String haiGuestbookAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGuestbookWithBLOBs> rm = haiGuestbookService.guestbook_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/guestbook/detail";
//			return this.view(request, "/guestbook/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交招商代理",value="haiGuestbookAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGuestbookAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGuestbookAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("guestbook") HaiGuestbookWithBLOBs guestbook,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiGuestbookWithBLOBs> rm = haiGuestbookService.guestbook_insert_submit(request, guestbook);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑招商代理",value="haiGuestbookEditDetail",relation="haiGuestbookEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiGuestbookEditDetail",method=RequestMethod.GET)
	public String haiGuestbookEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "guestbookId", required = true) Integer guestbookId
			) {
		try{
			ReturnObject<HaiGuestbookWithBLOBs> rm = haiGuestbookService.guestbook_update(request,guestbookId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/guestbook/detail";
//			return this.view(request, "/guestbook/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交招商代理",value="haiGuestbookEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGuestbookEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGuestbookEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("guestbook") HaiGuestbookWithBLOBs guestbook,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiGuestbookService.guestbook_update_submit(request,guestbook));
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除招商代理",value="haiGuestbookDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiGuestbookDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGuestbookDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "guestbookId", required = true) Integer guestbookId
			) {
		try{
			return this.writeJson(haiGuestbookService.guestbook_delete(request, guestbookId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("guestbook", e);
			return this.errorJSON(e);
		}
	}
	
	


}


