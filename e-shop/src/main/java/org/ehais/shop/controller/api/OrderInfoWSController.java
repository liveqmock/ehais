package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.shop.controller.api.include.OrderInfoIController;
import org.ehais.tools.EConditionObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class OrderInfoWSController extends OrderInfoIController{


	@ResponseBody
	@RequestMapping("/orderinfo_list")
	public String orderinfo_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "order_status", required = false) Integer order_status,
			@RequestParam(value = "pay_status", required = false) Integer pay_status,
			@RequestParam(value = "shipping_status", required = false) Integer shipping_status,
			@RequestParam(value = "order_sn", required = false) String order_sn,
			@ModelAttribute EConditionObject condition
			){
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try {
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, order_status,pay_status,shipping_status,condition,order_sn));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/orderinfo_info")
	public String orderinfo_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId){
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try {
			return this.writeJson(orderinfoService.orderinfo_info(request, user_id, orderId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	//删除订单信息
	@ResponseBody
	@RequestMapping("/orderinfo_disvalid")
	public String orderinfo_disvalid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId){
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try {
			return this.writeJson(orderinfoService.orderinfo_disvalid(request, user_id, orderId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
}
