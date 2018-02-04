package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.service.ShippingService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class ShippingIController extends CommonController{

	@Autowired
	private ShippingService shippingService;
	
	@ResponseBody
	@RequestMapping("/shipping_list")
	public String shipping_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<HaiShipping> rm = shippingService.shipping_list_json(request,null, null, null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return null;
	}
	
	
}
