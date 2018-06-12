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
import org.ehais.shop.model.HaiExpenses;
import org.ehais.shop.service.HaiExpensesService;
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

@EPermissionController(name="支出费用信息管理",value="haiExpensesController")
@Controller
@RequestMapping("/admin")
public class  HaiExpensesAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiExpensesAdminController.class);

	@Autowired
	private HaiExpensesService haiExpensesService;
	
	
	@EPermissionMethod(name="查询",intro="打开支出费用信息页面",value="haiExpensesView",relation="haiExpensesListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiExpensesView")
	public String haiExpensesView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/expenses/view";
			//return this.view(request, "/expenses/view");
			return this.view(request, "/expenses/expensesView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回支出费用信息数据",value="haiExpensesListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiExpensesListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiExpensesListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "expensesName", required = false) String expensesName) {
		try{
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_list_json(request, condition,keySubId,expensesName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增支出费用信息",value="haiExpensesAddDetail",relation="haiExpensesAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiExpensesAddDetail",method=RequestMethod.GET)
	public String haiExpensesAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/expenses/detail";
			return this.view(request, "/expenses/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交支出费用信息",value="haiExpensesAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiExpensesAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiExpensesAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("expenses") HaiExpenses expenses,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_insert_submit(request, expenses);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑支出费用信息",value="haiExpensesEditDetail",relation="haiExpensesEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiExpensesEditDetail",method=RequestMethod.POST)
	public String haiExpensesEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "expensesId", required = true) Integer expensesId
			) {
		try{
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_update(request,expensesId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑支出费用信息",value="haiExpensesEditDetail",relation="haiExpensesEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiExpensesEditDetail",method=RequestMethod.GET)
	public String haiExpensesEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "expensesId", required = true) Integer expensesId
			) {
		try{
			ReturnObject<HaiExpenses> rm = haiExpensesService.expenses_update(request,expensesId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/expenses/detail";
			return this.view(request, "/expenses/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交支出费用信息",value="haiExpensesEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiExpensesEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiExpensesEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("expenses") HaiExpenses expenses,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiExpensesService.expenses_update_submit(request,expenses));
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除支出费用信息",value="haiExpensesDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiExpensesDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiExpensesDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "expensesId", required = true) Integer expensesId
			) {
		try{
			return this.writeJson(haiExpensesService.expenses_delete(request, expensesId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("expenses", e);
			return this.errorJSON(e);
		}
	}

	


}


