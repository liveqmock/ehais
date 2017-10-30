package org.ehais.weixin.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.OrderPayModel;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.service.order.OrderPayWeiXinService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class OrderPayWeiXinController extends CommonController{

	@Autowired
	private OrderPayWeiXinService orderPayWeiXinService;
	
	
//	@ResponseBody
//	@RequestMapping("/order_pay_submit")
//	public String order_pay_submit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute OrderPayModel orderPayModel){
//		
//		orderPayModel.setTrade_type("JSAPI");
//		
//		try {
//			ReturnObject<WeiXinWCPay> rm = orderPayWeiXinService.order_pay_submit(request, orderPayModel);
//			return this.writeJson(rm);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return ""; 
//	}
	
	

	
	
}
