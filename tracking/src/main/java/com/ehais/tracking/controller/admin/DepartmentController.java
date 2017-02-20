package com.ehais.tracking.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.tracking.entity.Department;
import com.ehais.tracking.service.tracking.DepartmentService;



@Controller
@RequestMapping("/admin")
public class  DepartmentController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;
	
	
	@RequestMapping("/department_list")
	public String department_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "department_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/department/list";
	}
	
	@ResponseBody
	@RequestMapping("/department_list_json")
	public String department_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "school_id", required = false) Integer school_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Department> rm = departmentService.department_list_json(request ,school_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

	
	

	@RequestMapping("/department_insert")
	public String department_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Department> rm = departmentService.department_insert(request);
			rm.setAction("department_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/department/info";
	}
	
	@RequestMapping(value="/department_insert_submit",method=RequestMethod.POST)
	public String department_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Department department
			) {
		try{
			ReturnObject<Department> rm = departmentService.department_insert_submit(request,department);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/school_data");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:department_insert";
	}
	
	@RequestMapping("/department_update")
	public String department_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<Department> rm = departmentService.department_update(request, id);
			rm.setAction("department_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/department/info";
	}
	
	@RequestMapping(value="/department_update_submit",method=RequestMethod.POST)
	public String department_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Department department
			) {
		try{
			ReturnObject<Department> rm = departmentService.department_update_submit(request,department);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "department_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/department/info";
	}
	
	
	@RequestMapping("/department_delete")
	public String department_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<Department> rm = departmentService.department_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "department_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


