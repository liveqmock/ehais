package com.ehais.tracking.service.tracking;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Professional;





public interface ProfessionalService extends CommonService{
	public ReturnObject<Professional> professional_list(HttpServletRequest request,Map<String, Object> map) throws Exception;
	public ReturnObject<Professional> professional_list_json(HttpServletRequest request,Integer department_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Professional> professional_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<Professional> professional_insert_submit(HttpServletRequest request,Professional model) throws Exception;
	public ReturnObject<Professional> professional_update(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Professional> professional_update_submit(HttpServletRequest request,Professional model) throws Exception;
	public ReturnObject<Professional> professional_find(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Professional> professional_delete(HttpServletRequest request,Integer key) throws Exception;
	
	

}


