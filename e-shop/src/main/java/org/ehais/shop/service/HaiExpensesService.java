package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiExpenses;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiExpensesService extends CommonService{
	public ReturnObject<HaiExpenses> expenses_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiExpenses> expenses_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String expensesName) throws Exception;
	public ReturnObject<HaiExpenses> expenses_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiExpenses> expenses_insert_submit(HttpServletRequest request,HaiExpenses model) throws Exception;
	public ReturnObject<HaiExpenses> expenses_update(HttpServletRequest request,Integer expensesId) throws Exception;
	public ReturnObject<HaiExpenses> expenses_update_submit(HttpServletRequest request,HaiExpenses model) throws Exception;
	public ReturnObject<HaiExpenses> expenses_info(HttpServletRequest request,Integer expensesId) throws Exception;
	public ReturnObject<HaiExpenses> expenses_find(HttpServletRequest request,Integer expensesId) throws Exception;
	public ReturnObject<HaiExpenses> expenses_delete(HttpServletRequest request,Integer expensesId) throws Exception;
	


	

}

