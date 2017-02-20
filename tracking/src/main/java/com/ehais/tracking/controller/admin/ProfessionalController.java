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

import com.ehais.tracking.entity.Professional;
import com.ehais.tracking.service.tracking.ProfessionalService;

@Controller
@RequestMapping("/admin")
public class  ProfessionalController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ProfessionalController.class);

	@Autowired
	private ProfessionalService professionalService;
	
	
	@RequestMapping("/professional_list")
	public String professional_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "professional_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/professional/list";
	}
	
	@ResponseBody
	@RequestMapping("/professional_list_json")
	public String professional_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "department_id", required = false) Integer department_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_list_json(request , department_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/professional_insert")
	public String professional_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_insert(request);
			rm.setAction("professional_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/professional/info";
	}
	
	@RequestMapping(value="/professional_insert_submit",method=RequestMethod.POST)
	public String professional_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Professional professional
			) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_insert_submit(request,professional);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "professional_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:professional_insert";
	}
	
	@RequestMapping("/professional_update")
	public String professional_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_update(request, id);
			rm.setAction("professional_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/professional/info";
	}
	
	@RequestMapping(value="/professional_update_submit",method=RequestMethod.POST)
	public String professional_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Professional professional
			) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_update_submit(request,professional);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "professional_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/professional/info";
	}
	
	
	@RequestMapping("/professional_delete")
	public String professional_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<Professional> rm = professionalService.professional_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "professional_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


