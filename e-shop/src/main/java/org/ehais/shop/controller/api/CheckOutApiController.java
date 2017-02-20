package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.shop.controller.api.include.CheckOutIController;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class CheckOutApiController extends CheckOutIController {

	@ResponseBody
	@RequestMapping("/checkout")
	public String checkout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "recIds", required = true) String recIds) {
		try{
			ReturnObject<Object> rm = checkOutService.checkout(request,  recIds , user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/done")
	public String done(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "pay_id", required = true) Short pay_id,
			@RequestParam(value = "ship_id", required = true) Short ship_id,
			@RequestParam(value = "address_id", required = true) Long address_id,
			@RequestParam(value = "message", required = true) String message,
			@RequestParam(value = "recIds", required = true) String recIds) {
		try{
			ReturnObject<HaiOrderInfo> rm = checkOutService.done( request,
					recIds,
					pay_id,
					ship_id,
					address_id,
					user_id,
					message
					);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	
}
