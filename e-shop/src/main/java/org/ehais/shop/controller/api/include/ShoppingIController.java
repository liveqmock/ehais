package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.shop.service.ShoppingService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class ShoppingIController extends CommonController{

	@Autowired
	private ShoppingService shoppingService;
	
	@ResponseBody
	@RequestMapping("/order_done")
	public String order_done(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("order_done") OrderDoneParam order_done) {
		try{
			ReturnObject<HaiShipping> rm = shoppingService.shipping_list(request);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return null;
	}
	
	
}
