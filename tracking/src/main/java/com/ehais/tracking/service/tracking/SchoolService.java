package com.ehais.tracking.service.tracking;

import java.util.Map;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.School;



public interface SchoolService extends CommonService{
	public ReturnObject<School> school_list(Map<String,Object> map) throws Exception;
	public ReturnObject<School> school_list_json(Map<String,Object> map,Integer page,Integer len) throws Exception;
	public ReturnObject<School> school_insert() throws Exception;
	public ReturnObject<School> school_insert_submit(School model) throws Exception;
	public ReturnObject<School> school_update(Integer key) throws Exception;
	public ReturnObject<School> school_update_submit(School model) throws Exception;
	public ReturnObject<School> school_find(Integer key) throws Exception;
	public ReturnObject<School> school_delete(Integer key) throws Exception;
	
	public ReturnObject<School> school_login(String username,String password) throws Exception;

}
