package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiWithdrawDeposit;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.HaiWithdrawDepositService;
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

@EPermissionController(name="商家提现管理",value="haiWithdrawDepositController")
@Controller
@RequestMapping("/admin")
public class  HaiWithdrawDepositAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiWithdrawDepositAdminController.class);

	@Autowired
	private HaiWithdrawDepositService haiWithdrawDepositService;
	
	
	@EPermissionMethod(name="查询",intro="打开招商代理页面",value="haiWithdrawDepositView",relation="haiWithdrawDepositListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiWithdrawDepositView")
	public String haiWithdrawDepositView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiWithdrawDeposit> rm = haiWithdrawDepositService.withdrawdeposit_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/withdrawdeposit/view";
			return this.view(request, "/withdrawdeposit/view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回招商代理数据",value="haiWithdrawDepositListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiWithdrawDepositListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWithdrawDepositListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<HaiWithdrawDeposit> rm = haiWithdrawDepositService.withdrawdeposit_list_json(request, condition,keySubId,title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增招商代理",value="haiWithdrawDepositAddDetail",relation="haiWithdrawDepositAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWithdrawDepositAddDetail",method=RequestMethod.GET)
	public String haiWithdrawDepositAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiWithdrawDeposit> rm = haiWithdrawDepositService.withdrawdeposit_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/withdrawdeposit/detail";
			return this.view(request, "/withdrawdeposit/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交招商代理",value="haiWithdrawDepositAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWithdrawDepositAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWithdrawDepositAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("withdrawdeposit") HaiWithdrawDeposit withdrawdeposit,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiWithdrawDeposit> rm = haiWithdrawDepositService.withdrawdeposit_insert_submit(request, withdrawdeposit);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑招商代理",value="haiWithdrawDepositEditDetail",relation="haiWithdrawDepositEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWithdrawDepositEditDetail",method=RequestMethod.GET)
	public String haiWithdrawDepositEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wdId", required = true) Integer wdId
			) {
		try{
			ReturnObject<HaiWithdrawDeposit> rm = haiWithdrawDepositService.withdrawdeposit_update(request,wdId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/withdrawdeposit/detail";
			return this.view(request, "/withdrawdeposit/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交招商代理",value="haiWithdrawDepositEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiWithdrawDepositEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWithdrawDepositEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("withdrawdeposit") HaiWithdrawDeposit withdrawdeposit,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiWithdrawDepositService.withdrawdeposit_update_submit(request,withdrawdeposit));
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除招商代理",value="haiWithdrawDepositDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiWithdrawDepositDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiWithdrawDepositDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wdId", required = true) Integer wdId
			) {
		try{
			return this.writeJson(haiWithdrawDepositService.withdrawdeposit_delete(request, wdId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("withdrawdeposit", e);
			return this.errorJSON(e);
		}
	}
	
	


}


