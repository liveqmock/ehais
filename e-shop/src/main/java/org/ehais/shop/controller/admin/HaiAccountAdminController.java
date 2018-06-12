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
import org.ehais.shop.model.HaiAccount;
import org.ehais.shop.service.HaiAccountService;
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

@EPermissionController(name="账户信息管理",value="haiAccountController")
@Controller
@RequestMapping("/admin")
public class  HaiAccountAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiAccountAdminController.class);

	@Autowired
	private HaiAccountService haiAccountService;
	
	
	@EPermissionMethod(name="查询",intro="打开账户信息页面",value="haiAccountView",relation="haiAccountListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiAccountView")
	public String haiAccountView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiAccount> rm = haiAccountService.account_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/account/view";
			//return this.view(request, "/account/view");
			return this.view(request, "/account/accountView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回账户信息数据",value="haiAccountListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiAccountListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "accountName", required = false) String accountName) {
		try{
			ReturnObject<HaiAccount> rm = haiAccountService.account_list_json(request, condition,keySubId,accountName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增账户信息",value="haiAccountAddDetail",relation="haiAccountAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiAccountAddDetail",method=RequestMethod.GET)
	public String haiAccountAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAccount> rm = haiAccountService.account_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/account/detail";
			return this.view(request, "/account/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交账户信息",value="haiAccountAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAccountAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("account") HaiAccount account,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiAccount> rm = haiAccountService.account_insert_submit(request, account);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑账户信息",value="haiAccountEditDetail",relation="haiAccountEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiAccountEditDetail",method=RequestMethod.POST)
	public String haiAccountEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountId", required = true) Integer accountId
			) {
		try{
			ReturnObject<HaiAccount> rm = haiAccountService.account_update(request,accountId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑账户信息",value="haiAccountEditDetail",relation="haiAccountEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiAccountEditDetail",method=RequestMethod.GET)
	public String haiAccountEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountId", required = true) Integer accountId
			) {
		try{
			ReturnObject<HaiAccount> rm = haiAccountService.account_update(request,accountId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/account/detail";
			return this.view(request, "/account/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交账户信息",value="haiAccountEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAccountEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("account") HaiAccount account,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAccountService.account_update_submit(request,account));
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除账户信息",value="haiAccountDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiAccountDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountId", required = true) Integer accountId
			) {
		try{
			return this.writeJson(haiAccountService.account_delete(request, accountId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("account", e);
			return this.errorJSON(e);
		}
	}

	


}


