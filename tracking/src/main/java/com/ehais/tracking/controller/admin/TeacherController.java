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

import com.ehais.tracking.entity.Teacher;
import com.ehais.tracking.service.tracking.TeacherService;


@Controller
@RequestMapping("/admin")
public class  TeacherController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private TeacherService teacherService;
	
	
	@RequestMapping("/teacher_list")
	public String teacher_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
		try{
			modelMap.addAttribute("wxid", school_id);
			modelMap.addAttribute("action", "teacher_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/teacher/list";
	}
	
	@ResponseBody
	@RequestMapping("/teacher_list_json")
	public String teacher_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Teacher> rm = teacherService.teacher_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/teacher_insert")
	public String teacher_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
			ReturnObject<Teacher> rm = teacherService.teacher_insert(school_id);
			rm.setAction("teacher_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/teacher/info";
	}
	
	@RequestMapping(value="/teacher_insert_submit",method=RequestMethod.POST)
	public String teacher_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Teacher teacher
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
			teacher.setSchoolId(school_id);
			ReturnObject<Teacher> rm = teacherService.teacher_insert_submit(teacher);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "teacher_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:teacher_insert";
	}
	
	@RequestMapping("/teacher_update")
	public String teacher_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
			ReturnObject<Teacher> rm = teacherService.teacher_update(school_id, keyId);
			rm.setAction("teacher_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/teacher/info";
	}
	
	@RequestMapping(value="/teacher_update_submit",method=RequestMethod.POST)
	public String teacher_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Teacher teacher
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
			teacher.setSchoolId(school_id);
			ReturnObject<Teacher> rm = teacherService.teacher_update_submit(teacher);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "teacher_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/teacher/info";
	}
	
	
	@RequestMapping("/teacher_delete")
	public String teacher_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer school_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID);
			ReturnObject<Teacher> rm = teacherService.teacher_delete(school_id, keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "teacher_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/teacher_file.upd", method = RequestMethod.POST)
	public String teacher_file(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		try {
			
			ReturnObject<Teacher> rm = teacherService.teacher_file(request, response);
		    return this.writeJson(rm);
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "/teacher_file.upd", method = RequestMethod.POST)
	public String teacher_file(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		try {
			
			String jsonStr = UploadUtils.upload_file(request, response);
			JSONObject json = new JSONObject(jsonStr);
			InputStream is = new FileInputStream(json.getString("msg"));
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) continue;
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	                  XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	                  if (xssfRow != null) {
	                      XSSFCell no = xssfRow.getCell(0);
	                      XSSFCell name = xssfRow.getCell(1);
	                      XSSFCell age = xssfRow.getCell(2);
	                      XSSFCell score = xssfRow.getCell(3);
	//	                      list.add(student);
	                  }
	              }
			}
			 
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}*/
}

