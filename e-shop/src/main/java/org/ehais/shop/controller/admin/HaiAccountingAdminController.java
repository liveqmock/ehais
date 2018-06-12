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
import org.ehais.shop.model.HaiAccounting;
import org.ehais.shop.service.HaiAccountingService;
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

@EPermissionController(name="会计科目信息管理",value="haiAccountingController")
@Controller
@RequestMapping("/admin")
public class  HaiAccountingAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiAccountingAdminController.class);

	@Autowired
	private HaiAccountingService haiAccountingService;
	
	
	@EPermissionMethod(name="查询",intro="打开会计科目信息页面",value="haiAccountingView",relation="haiAccountingListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiAccountingView")
	public String haiAccountingView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/accounting/view";
			//return this.view(request, "/accounting/view");
			return this.view(request, "/accounting/accountingView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回会计科目信息数据",value="haiAccountingListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiAccountingListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountingListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "accountingName", required = false) String accountingName) {
		try{
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_list_json(request, condition,keySubId,accountingName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增会计科目信息",value="haiAccountingAddDetail",relation="haiAccountingAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiAccountingAddDetail",method=RequestMethod.GET)
	public String haiAccountingAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/accounting/detail";
			return this.view(request, "/accounting/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交会计科目信息",value="haiAccountingAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAccountingAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountingAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("accounting") HaiAccounting accounting,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_insert_submit(request, accounting);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑会计科目信息",value="haiAccountingEditDetail",relation="haiAccountingEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiAccountingEditDetail",method=RequestMethod.POST)
	public String haiAccountingEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountingId", required = true) Integer accountingId
			) {
		try{
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_update(request,accountingId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑会计科目信息",value="haiAccountingEditDetail",relation="haiAccountingEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiAccountingEditDetail",method=RequestMethod.GET)
	public String haiAccountingEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountingId", required = true) Integer accountingId
			) {
		try{
			ReturnObject<HaiAccounting> rm = haiAccountingService.accounting_update(request,accountingId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/accounting/detail";
			return this.view(request, "/accounting/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交会计科目信息",value="haiAccountingEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiAccountingEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountingEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("accounting") HaiAccounting accounting,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAccountingService.accounting_update_submit(request,accounting));
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除会计科目信息",value="haiAccountingDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiAccountingDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAccountingDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "accountingId", required = true) Integer accountingId
			) {
		try{
			return this.writeJson(haiAccountingService.accounting_delete(request, accountingId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("accounting", e);
			return this.errorJSON(e);
		}
	}

	


}


