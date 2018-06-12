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
import org.ehais.shop.model.HaiIncome;
import org.ehais.shop.service.HaiIncomeService;
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

@EPermissionController(name="收入信息管理",value="haiIncomeController")
@Controller
@RequestMapping("/admin")
public class  HaiIncomeAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiIncomeAdminController.class);

	@Autowired
	private HaiIncomeService haiIncomeService;
	
	
	@EPermissionMethod(name="查询",intro="打开收入信息页面",value="haiIncomeView",relation="haiIncomeListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiIncomeView")
	public String haiIncomeView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiIncome> rm = haiIncomeService.income_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/income/view";
			//return this.view(request, "/income/view");
			return this.view(request, "/income/incomeView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回收入信息数据",value="haiIncomeListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiIncomeListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiIncomeListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "incomeName", required = false) String incomeName) {
		try{
			ReturnObject<HaiIncome> rm = haiIncomeService.income_list_json(request, condition,keySubId,incomeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增收入信息",value="haiIncomeAddDetail",relation="haiIncomeAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiIncomeAddDetail",method=RequestMethod.GET)
	public String haiIncomeAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiIncome> rm = haiIncomeService.income_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/income/detail";
			return this.view(request, "/income/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交收入信息",value="haiIncomeAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiIncomeAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiIncomeAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("income") HaiIncome income,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiIncome> rm = haiIncomeService.income_insert_submit(request, income);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑收入信息",value="haiIncomeEditDetail",relation="haiIncomeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiIncomeEditDetail",method=RequestMethod.POST)
	public String haiIncomeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "incomeId", required = true) Integer incomeId
			) {
		try{
			ReturnObject<HaiIncome> rm = haiIncomeService.income_update(request,incomeId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑收入信息",value="haiIncomeEditDetail",relation="haiIncomeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiIncomeEditDetail",method=RequestMethod.GET)
	public String haiIncomeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "incomeId", required = true) Integer incomeId
			) {
		try{
			ReturnObject<HaiIncome> rm = haiIncomeService.income_update(request,incomeId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/income/detail";
			return this.view(request, "/income/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交收入信息",value="haiIncomeEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiIncomeEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiIncomeEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("income") HaiIncome income,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiIncomeService.income_update_submit(request,income));
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除收入信息",value="haiIncomeDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiIncomeDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiIncomeDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "incomeId", required = true) Integer incomeId
			) {
		try{
			return this.writeJson(haiIncomeService.income_delete(request, incomeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("income", e);
			return this.errorJSON(e);
		}
	}

	


}


