package org.ehais.shop.controller.api.include;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiWithdrawDeposit;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class WithdrawDepositIController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(WithdrawDepositIController.class);
	
	
	@ResponseBody
	@RequestMapping(value="/withdrawDepositJSON",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String withdrawDepositJSON(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 97);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		System.out.println(store_id);
		
		
		try{
			Date date = new Date();
//			date = DateUtil.addDate(date, -3);
			String pay_date = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			
			
			ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
			
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	
}
