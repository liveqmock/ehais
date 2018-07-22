package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiOrderFinanceService extends CommonService{
	public ReturnObject<HaiOrderFinance> orderfinance_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String financeName) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_insert_submit(HttpServletRequest request,HaiOrderFinance model) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_update(HttpServletRequest request,Integer financeId) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_update_submit(HttpServletRequest request,HaiOrderFinance model) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_info(HttpServletRequest request,Integer financeId) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_find(HttpServletRequest request,Integer financeId) throws Exception;
	public ReturnObject<HaiOrderFinance> orderfinance_delete(HttpServletRequest request,Integer financeId) throws Exception;
	


	

}

