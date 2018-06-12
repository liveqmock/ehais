package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiAccount;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiAccountService extends CommonService{
	public ReturnObject<HaiAccount> account_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAccount> account_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String accountName) throws Exception;
	public ReturnObject<HaiAccount> account_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAccount> account_insert_submit(HttpServletRequest request,HaiAccount model) throws Exception;
	public ReturnObject<HaiAccount> account_update(HttpServletRequest request,Integer accountId) throws Exception;
	public ReturnObject<HaiAccount> account_update_submit(HttpServletRequest request,HaiAccount model) throws Exception;
	public ReturnObject<HaiAccount> account_info(HttpServletRequest request,Integer accountId) throws Exception;
	public ReturnObject<HaiAccount> account_find(HttpServletRequest request,Integer accountId) throws Exception;
	public ReturnObject<HaiAccount> account_delete(HttpServletRequest request,Integer accountId) throws Exception;
	


	

}

