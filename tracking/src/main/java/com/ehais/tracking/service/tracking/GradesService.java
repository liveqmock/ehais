package com.ehais.tracking.service.tracking;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Grades;





public interface GradesService extends CommonService{
	public ReturnObject<Grades> grades_list(HttpServletRequest request,Map<String, Object> map) throws Exception;
	public ReturnObject<Grades> grades_list_json(HttpServletRequest request,Integer professional_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Grades> grades_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<Grades> grades_insert_submit(HttpServletRequest request,Grades model) throws Exception;
	public ReturnObject<Grades> grades_update(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Grades> grades_update_submit(HttpServletRequest request,Grades model) throws Exception;
	public ReturnObject<Grades> grades_find(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Grades> grades_delete(HttpServletRequest request,Integer key) throws Exception;
	
	

}


