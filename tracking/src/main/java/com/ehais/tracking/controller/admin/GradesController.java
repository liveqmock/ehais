package com.ehais.tracking.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
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

import com.ehais.tracking.entity.Grades;
import com.ehais.tracking.service.tracking.GradesService;



@Controller
@RequestMapping("/admin")
public class  GradesController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(GradesController.class);

	@Autowired
	private GradesService gradesService;
	
	
	@RequestMapping("/grades_list")
	public String grades_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "grades_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/grades/list";
	}
	
	@ResponseBody
	@RequestMapping("/grades_list_json")
	public String grades_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "professional_id", required = false) Integer professional_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_list_json(request, professional_id , page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/grades_insert")
	public String grades_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_insert(request);
			rm.setAction("grades_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/grades/info";
	}
	
	@RequestMapping(value="/grades_insert_submit",method=RequestMethod.POST)
	public String grades_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Grades grades
			) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_insert_submit(request,grades);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "grades_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:grades_insert";
	}
	
	@RequestMapping("/grades_update")
	public String grades_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_update(request, id);
			rm.setAction("grades_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/grades/info";
	}
	
	@RequestMapping(value="/grades_update_submit",method=RequestMethod.POST)
	public String grades_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Grades grades
			) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_update_submit(request,grades);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "grades_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/grades/info";
	}
	
	
	@RequestMapping("/grades_delete")
	public String grades_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<Grades> rm = gradesService.grades_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "grades_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


