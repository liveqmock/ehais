package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.HaiWithdrawDeposit;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiWithdrawDepositService extends CommonService{
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_insert_submit(HttpServletRequest request,HaiWithdrawDeposit model) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_update(HttpServletRequest request,Integer wdId) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_update_submit(HttpServletRequest request,HaiWithdrawDeposit model) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_info(HttpServletRequest request,Integer wdId) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_find(HttpServletRequest request,Integer wdId) throws Exception;
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_delete(HttpServletRequest request,Integer wdId) throws Exception;
	


	

}

