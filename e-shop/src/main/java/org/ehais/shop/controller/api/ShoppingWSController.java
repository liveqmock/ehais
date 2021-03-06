package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.shop.controller.api.include.ShoppingIController;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ws")
public class ShoppingWSController extends ShoppingIController {


	@ResponseBody
	@RequestMapping("/checkout")
	public String checkout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recIds", required = true) String recIds) {
		try{
			ReturnObject<Object> rm = shoppingService.checkout(request,  recIds , null);
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
			@RequestParam(value = "pay_id", required = true) Integer pay_id,
			@RequestParam(value = "ship_id", required = true) Integer ship_id,
			@RequestParam(value = "address_id", required = true) Long address_id,
			@RequestParam(value = "message", required = true) String message,
			@RequestParam(value = "recIds", required = true) String recIds) {
		try{
			ReturnObject<HaiOrderInfo> rm = shoppingService.done( request,
					recIds,
					pay_id,
					ship_id,
					address_id,
					null,
					message
					);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	/**
	 * 微信提交订单并支付
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param pay_id
	 * @param ship_id
	 * @param address_id
	 * @param message
	 * @param recIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxdone")
	public String wxdone(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cid", required = true) String cid,
			@RequestParam(value = "pay_id", required = true) Integer pay_id,
			@RequestParam(value = "ship_id", required = true) Integer ship_id,
			@RequestParam(value = "address_id", required = true) Long address_id,
			@RequestParam(value = "message", required = true) String message,
			@RequestParam(value = "recIds", required = true) String recIds) {
		try{
			OrderDoneParam param = new OrderDoneParam();
			param.setPay_id(pay_id);
			param.setShipping_id(ship_id);
			param.setAddress_id(address_id);
			param.setRec_id_data(recIds);
			param.setPostscript(message);
			ReturnObject<OrderDoneParam> rm = shoppingService.WeixinOrderDone( request,
					param,
					cid
					);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
}
