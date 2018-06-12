package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiIncome;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiIncomeService extends CommonService{
	public ReturnObject<HaiIncome> income_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiIncome> income_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String incomeName) throws Exception;
	public ReturnObject<HaiIncome> income_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiIncome> income_insert_submit(HttpServletRequest request,HaiIncome model) throws Exception;
	public ReturnObject<HaiIncome> income_update(HttpServletRequest request,Integer incomeId) throws Exception;
	public ReturnObject<HaiIncome> income_update_submit(HttpServletRequest request,HaiIncome model) throws Exception;
	public ReturnObject<HaiIncome> income_info(HttpServletRequest request,Integer incomeId) throws Exception;
	public ReturnObject<HaiIncome> income_find(HttpServletRequest request,Integer incomeId) throws Exception;
	public ReturnObject<HaiIncome> income_delete(HttpServletRequest request,Integer incomeId) throws Exception;
	


	

}

