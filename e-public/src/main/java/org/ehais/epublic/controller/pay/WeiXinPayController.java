package org.ehais.epublic.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin/pay")
public class WeiXinPayController extends CommonController {

	@Autowired
	private WeiXinPayService weiXinPayService;
	
	/**支付回调函数
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/notify_url-{store_id}")
	public String notify_url(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id
			) {
		try{
			String inputLine = null;
			String notityPayXml = "";
			while ((inputLine = request.getReader().readLine()) != null) {
				notityPayXml += inputLine;
			}
			log.info("notityPayXml:"+notityPayXml);
			if(notityPayXml != null && !notityPayXml.equals("")){
				WeiXinNotifyPay notifyPay = WeiXinUtil.toNotifyPayXml(notityPayXml);
				ReturnObject<WeiXinNotifyPay> rm = weiXinPayService.WeiXinNotifyPay(request, notifyPay,store_id);
				WeiXinNotifyPay wnp = new WeiXinNotifyPay();
				wnp.setReturn_code(rm.getModel().getReturn_code());
				wnp.setReturn_msg(rm.getModel().getReturn_msg());
				
				String xml = WeiXinUtil.fromNotifyPayXml(wnp);
				
				System.out.println("notifyPay:"+xml);
				
				return xml;
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
}
