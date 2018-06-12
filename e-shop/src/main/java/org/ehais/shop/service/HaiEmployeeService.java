package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiEmployee;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiEmployeeService extends CommonService{
	public ReturnObject<HaiEmployee> employee_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiEmployee> employee_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String employeeName) throws Exception;
	public ReturnObject<HaiEmployee> employee_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiEmployee> employee_insert_submit(HttpServletRequest request,HaiEmployee model) throws Exception;
	public ReturnObject<HaiEmployee> employee_update(HttpServletRequest request,Integer employeeId) throws Exception;
	public ReturnObject<HaiEmployee> employee_update_submit(HttpServletRequest request,HaiEmployee model) throws Exception;
	public ReturnObject<HaiEmployee> employee_info(HttpServletRequest request,Integer employeeId) throws Exception;
	public ReturnObject<HaiEmployee> employee_find(HttpServletRequest request,Integer employeeId) throws Exception;
	public ReturnObject<HaiEmployee> employee_delete(HttpServletRequest request,Integer employeeId) throws Exception;
	


	

}

