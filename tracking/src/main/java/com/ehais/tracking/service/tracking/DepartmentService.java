package com.ehais.tracking.service.tracking;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Department;





public interface DepartmentService extends CommonService{
	public ReturnObject<Department> department_list(HttpServletRequest request,Map<String,Object> map) throws Exception;
	public ReturnObject<Department> department_list_json(HttpServletRequest request,Integer school_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Department> department_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<Department> department_insert_submit(HttpServletRequest request,Department model) throws Exception;
	public ReturnObject<Department> department_update(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Department> department_update_submit(HttpServletRequest request,Department model) throws Exception;
	public ReturnObject<Department> department_find(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Department> department_delete(HttpServletRequest request,Integer key) throws Exception;
	
	

}


