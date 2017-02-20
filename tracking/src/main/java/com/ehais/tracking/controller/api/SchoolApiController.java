package com.ehais.tracking.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.tracking.entity.Department;
import com.ehais.tracking.entity.Grades;
import com.ehais.tracking.entity.Professional;
import com.ehais.tracking.entity.School;
import com.ehais.tracking.service.tracking.DepartmentService;
import com.ehais.tracking.service.tracking.GradesService;
import com.ehais.tracking.service.tracking.ProfessionalService;
import com.ehais.tracking.service.tracking.SchoolService;


@Controller
@RequestMapping("/api")
public class SchoolApiController extends CommonController{

	@Autowired
	private SchoolService schoolService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProfessionalService professionalService;
	@Autowired
	private GradesService gradesService;
	
	
	@ResponseBody
	@RequestMapping("/school")
	public String school(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<School> rm = schoolService.school_list(null);
			rm.setCode(1);
			rm.setMsg("success");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/department")
	public String department(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "schoolId", required = true) Integer schoolId) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schoolId", schoolId);
			ReturnObject<Department> rm = departmentService.department_list(request,map);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/professional")
	public String professional(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "departmentId", required = true) Integer departmentId) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("departmentId", departmentId);
			ReturnObject<Professional> rm = professionalService.professional_list(request,map);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/grades")
	public String grades(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "professionalId", required = true) Integer professionalId) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("professionalId", professionalId);
			ReturnObject<Grades> rm = gradesService.grades_list(request,map);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
