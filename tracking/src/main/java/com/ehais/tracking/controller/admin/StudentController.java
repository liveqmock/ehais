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

import com.ehais.tracking.entity.Student;
import com.ehais.tracking.service.tracking.StudentService;




@Controller
@RequestMapping("/admin")
public class  StudentController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;
	
	
	@RequestMapping("/student_list")
	public String student_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
		try{
			modelMap.addAttribute("school_id", school_id);
			modelMap.addAttribute("action", "student_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/student/list";
	}
	
	@ResponseBody
	@RequestMapping("/student_list_json")
	public String student_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Student> rm = studentService.student_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/student_insert")
	public String student_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Student> rm = studentService.student_insert(school_id);
			rm.setAction("student_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/student/info";
	}
	
	@RequestMapping(value="/student_insert_submit",method=RequestMethod.POST)
	public String student_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Student student
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			student.setSchoolId(school_id);
			ReturnObject<Student> rm = studentService.student_insert_submit(student);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "student_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:student_insert";
	}
	
	@RequestMapping("/student_update")
	public String student_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Student> rm = studentService.student_update(school_id, keyId);
			rm.setAction("student_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/student/info";
	}
	
	@RequestMapping(value="/student_update_submit",method=RequestMethod.POST)
	public String student_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Student student
			) {
		try{
//			Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
//			student.setSchoolId(school_id);
			ReturnObject<Student> rm = studentService.student_update_submit(student);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "student_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/student/info";
	}
	
	
	@RequestMapping("/student_delete")
	public String student_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Student> rm = studentService.student_delete(school_id, keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "student_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/student_file.upd", method = RequestMethod.POST)
	public String student_file(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		try {
			
			ReturnObject<Student> rm = studentService.student_file(request, response);
		    return this.writeJson(rm);
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
}
