package com.ehais.tracking.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ehais.tracking.entity.HaiAdminUser;
import com.ehais.tracking.entity.Leader;
import com.ehais.tracking.entity.School;
import com.ehais.tracking.entity.Student;
import com.ehais.tracking.entity.Teacher;
import com.ehais.tracking.service.tracking.HaiAdminUserService;
import com.ehais.tracking.service.tracking.LeaderService;
import com.ehais.tracking.service.tracking.SchoolService;
import com.ehais.tracking.service.tracking.StudentService;
import com.ehais.tracking.service.tracking.TeacherService;

@Controller
@RequestMapping("/")
public class LoginContorller extends CommonController {

	private static Logger log = LoggerFactory.getLogger(LoginContorller.class);

	@Autowired
	private HaiAdminUserService haiAdminUserService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private LeaderService leaderService;


	@RequestMapping("/login")
	public String admin_login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAdminUser> rm = haiAdminUserService.admin_user_insert();
			rm.setAction("member_login_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/login";
	}
	
	
	
	@RequestMapping(value="/member_login_submit",method=RequestMethod.POST)
	public String member_login_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "login_type", required = true) String login_type
			) {
		try{
			if(login_type.equals("admin")){
				ReturnObject<HaiAdminUser> rm = haiAdminUserService.admin_login(username,password);
				if(rm.getCode() == 1){
					HaiAdminUser admin = rm.getModel();
					request.getSession().setAttribute(EConstants.SESSION_ADMIN_ID,admin.getAdminId());
					request.getSession().setAttribute(EConstants.SESSION_ADMIN_NAME,username);
					request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE,login_type);
				}
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
			}else if(login_type.equals("student")){
				ReturnObject<Student> rm = studentService.student_login(username,password);
				if(rm.getCode() == 1){
					Student student = rm.getModel();
					request.getSession().setAttribute(EConstants.SESSION_USER_ID,student.getId());
					request.getSession().setAttribute(EConstants.SESSION_USER_NAME,username);
					request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE,login_type);
					request.getSession().setAttribute(EConstants.SESSION_SCHOOL_ID,student.getSchoolId());
				}
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
			}else if(login_type.equals("teacher")){
				ReturnObject<Teacher> rm = teacherService.teacher_login(username,password);
				if(rm.getCode() == 1){
					Teacher teacher = rm.getModel();
					request.getSession().setAttribute(EConstants.SESSION_USER_ID,teacher.getId());
					request.getSession().setAttribute(EConstants.SESSION_USER_NAME,username);
					request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE,login_type);
					request.getSession().setAttribute(EConstants.SESSION_SCHOOL_ID,teacher.getSchoolId());
				}
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
			}else if(login_type.equals("school")){
				ReturnObject<School> rm = schoolService.school_login(username,password);
				if(rm.getCode() == 1){
					School school = rm.getModel();
					request.getSession().setAttribute(EConstants.SESSION_USER_ID,school.getId());
					request.getSession().setAttribute(EConstants.SESSION_USER_NAME,username);
					request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE,login_type);
				}
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
			}else if(login_type.equals("leader")){
				ReturnObject<Leader> rm = leaderService.leader_login(request,username,password);
				if(rm.getCode() == 1){
					Leader leader = rm.getModel();
					request.getSession().setAttribute(EConstants.SESSION_USER_ID,leader.getLeaderId());
					request.getSession().setAttribute(EConstants.SESSION_USER_NAME,username);
					request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE,login_type);
				}
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "/admin/index");
			}else{
				return this.ReturnJump(modelMap, 0, "正在建设中，请用管理员帐号登录...", "");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/login/member";
	}
	
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
			request.getSession().removeAttribute(EConstants.SESSION_ROLE_TYPE);
			return this.ReturnJump(modelMap, 1, "退出成功", "/login");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/login/member";
	}
	
	
}
