package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.shop.controller.api.include.OrderInfoIController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		if(user_id==null) user_id = 1;//临时使用
		try {
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, page, len));
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
//		if(user_id==null) user_id = 1;//临时使用
		try {
			return this.writeJson(orderinfoService.orderinfo_info(request, user_id, orderId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
}
