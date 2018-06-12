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
import org.ehais.shop.model.HaiEmployee;
import org.ehais.shop.service.HaiEmployeeService;
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

@EPermissionController(name="员工信息管理",value="haiEmployeeController")
@Controller
@RequestMapping("/admin")
public class  HaiEmployeeAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiEmployeeAdminController.class);

	@Autowired
	private HaiEmployeeService haiEmployeeService;
	
	
	@EPermissionMethod(name="查询",intro="打开员工信息页面",value="haiEmployeeView",relation="haiEmployeeListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiEmployeeView")
	public String haiEmployeeView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/employee/view";
			//return this.view(request, "/employee/view");
			return this.view(request, "/employee/employeeView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回员工信息数据",value="haiEmployeeListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiEmployeeListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiEmployeeListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "employeeName", required = false) String employeeName) {
		try{
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_list_json(request, condition,keySubId,employeeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增员工信息",value="haiEmployeeAddDetail",relation="haiEmployeeAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiEmployeeAddDetail",method=RequestMethod.GET)
	public String haiEmployeeAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/employee/detail";
			return this.view(request, "/employee/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交员工信息",value="haiEmployeeAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiEmployeeAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiEmployeeAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("employee") HaiEmployee employee,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_insert_submit(request, employee);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑员工信息",value="haiEmployeeEditDetail",relation="haiEmployeeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiEmployeeEditDetail",method=RequestMethod.POST)
	public String haiEmployeeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "employeeId", required = true) Integer employeeId
			) {
		try{
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_update(request,employeeId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑员工信息",value="haiEmployeeEditDetail",relation="haiEmployeeEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiEmployeeEditDetail",method=RequestMethod.GET)
	public String haiEmployeeEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "employeeId", required = true) Integer employeeId
			) {
		try{
			ReturnObject<HaiEmployee> rm = haiEmployeeService.employee_update(request,employeeId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/employee/detail";
			return this.view(request, "/employee/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交员工信息",value="haiEmployeeEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiEmployeeEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiEmployeeEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("employee") HaiEmployee employee,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiEmployeeService.employee_update_submit(request,employee));
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除员工信息",value="haiEmployeeDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiEmployeeDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiEmployeeDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "employeeId", required = true) Integer employeeId
			) {
		try{
			return this.writeJson(haiEmployeeService.employee_delete(request, employeeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("employee", e);
			return this.errorJSON(e);
		}
	}

	


}


