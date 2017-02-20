package com.ehais.tracking.service.tracking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinUserInfo;

import com.ehais.tracking.entity.Student;



public interface StudentService extends CommonService{
	public ReturnObject<Student> student_list(Integer school_id) throws Exception;
	public ReturnObject<Student> student_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<Student> student_insert(Integer school_id) throws Exception;
	public ReturnObject<Student> student_insert_submit(Student model) throws Exception;
	public ReturnObject<Student> student_update(Integer school_id,Integer key) throws Exception;
	public ReturnObject<Student> student_update_submit(Student model) throws Exception;
	public ReturnObject<Student> student_find(Integer school_id,Integer key) throws Exception;
	public ReturnObject<Student> student_delete(Integer school_id,Integer key) throws Exception;
	
	
	public ReturnObject<Student> student_login(String username,String password) throws Exception;
	
	public ReturnObject<Student> student_file(HttpServletRequest request, HttpServletResponse response)throws Exception;
	
	/**
	 * 微信用户保存在学生表
	 * @param request
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<Student> weixin_student_save(HttpServletRequest request, WeiXinUserInfo userInfo)throws Exception;
	
	/**
	 * 
	 * @param request
	 * @param wxid
	 * @param id
	 * @param openid
	 * @param school
	 * @param department
	 * @param professional
	 * @param grades
	 * @param name
	 * @param studentId
	 * @param mobile
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<Student> student_bind_save(HttpServletRequest request,Integer wxid,Integer qid,String openid,Integer school,Integer department,Integer professional,Integer grades,String name,String studentId,String mobile,String email)throws Exception;
	
	
}
