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

import com.ehais.tracking.entity.School;
import com.ehais.tracking.service.tracking.SchoolService;




@Controller
@RequestMapping("/admin")
public class  SchoolController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(SchoolController.class);

	@Autowired
	private SchoolService schoolService;
	
	
	@RequestMapping("/school_list")
	public String school_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("action", "school_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/school/list";
	}
	
	
	@ResponseBody
	@RequestMapping("/school_list_json")
	public String school_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<School> rm = schoolService.school_list_json(null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/school_insert")
	public String school_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<School> rm = schoolService.school_insert();
			rm.setAction("school_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/school/info";
	}
	
	@RequestMapping(value="/school_insert_submit",method=RequestMethod.POST)
	public String school_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute School school
			) {
		try{
			ReturnObject<School> rm = schoolService.school_insert_submit(school);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "school_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:school_insert";
	}
	
	@RequestMapping("/school_update")
	public String school_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			ReturnObject<School> rm = schoolService.school_update( keyId);
			rm.setAction("school_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/school/info";
	}
	
	@RequestMapping(value="/school_update_submit",method=RequestMethod.POST)
	public String school_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute School school
			) {
		try{
			ReturnObject<School> rm = schoolService.school_update_submit(school);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "school_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/school/info";
	}
	
	
	@RequestMapping("/school_delete")
	public String school_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<School> rm = schoolService.school_delete( keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "school_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/school_data")
	public String school_data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("school_id", user_id);
			modelMap.addAttribute("action", "school_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/school/data";
	}
	
	
}

