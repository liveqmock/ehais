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
	
	
	@ResponseBody
	@RequestMapping("/order_pay_submit")
	public String order_pay_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute OrderPayModel orderPayModel){
		
		orderPayModel.setTrade_type("JSAPI");
		
		try {
			ReturnObject<WeiXinWCPay> rm = orderPayWeiXinService.order_pay_submit(request, orderPayModel);
			return this.writeJson(rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ""; 
	}
	
	
	@ResponseBody
	@RequestMapping("/weixin/pay/notify_order_pay")
	public String notify_order_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		
		String inputLine;
		String notityXml = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			log.info("notityPayBackXml:"+notityXml);
			request.getReader().close();
			
			
			if(notityXml==null || notityXml.equals("")){
				WeiXinNotifyPay notifyPay = new WeiXinNotifyPay();
				notifyPay.setReturn_code("FAIL");
				notifyPay.setReturn_msg("接收数据为空");				
				String xml = WeiXinUtil.fromNotifyPayXml(notifyPay);
				
				System.out.println("notifyPay:"+xml);
				
				return xml;
			}
			
			
	        
			WeiXinNotifyPay notifyPay = WeiXinUtil.toNotifyPayXml(notityXml);
			
			if(notifyPay != null){
				//自己的逻辑处理
				ReturnObject<WeiXinNotifyPay> rm = orderPayWeiXinService.notify_order_pay(request, notifyPay);
				WeiXinNotifyPay wnp = new WeiXinNotifyPay();
				if(rm.getCode()!=0){
					wnp.setReturn_code("FAIL");
					wnp.setReturn_msg(rm.getMsg());
				}else{
					wnp.setReturn_code("SUCCESS");
					wnp.setReturn_msg("");
				}
				
				
				
				String xml = WeiXinUtil.fromNotifyPayXml(wnp);
				
				System.out.println("notifyPay:"+xml);
				
				return xml;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/index";
	}
	
	
}
