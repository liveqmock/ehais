package com.ehais.tracking.service.tracking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Teacher;


public interface TeacherService extends CommonService{
	public ReturnObject<Teacher> teacher_list(Integer school_id) throws Exception;
	public ReturnObject<Teacher> teacher_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<Teacher> teacher_insert(Integer school_id) throws Exception;
	public ReturnObject<Teacher> teacher_insert_submit(Teacher model) throws Exception;
	public ReturnObject<Teacher> teacher_update(Integer school_id,Integer key) throws Exception;
	public ReturnObject<Teacher> teacher_update_submit(Teacher model) throws Exception;
	public ReturnObject<Teacher> teacher_find(Integer school_id,Integer key) throws Exception;
	public ReturnObject<Teacher> teacher_delete(Integer school_id,Integer key) throws Exception;
	
	public ReturnObject<Teacher> teacher_login(String username,String password) throws Exception;

	
	public ReturnObject<Teacher> teacher_file(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
