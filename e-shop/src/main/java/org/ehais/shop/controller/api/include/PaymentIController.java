package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.service.PaymentService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class PaymentIController extends CommonController{

	@Autowired
	private PaymentService paymentService;
	
	@ResponseBody
	@RequestMapping("/payment_list")
	public String payment_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_list_json(request,null, null, null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return null;
	}
	
}
