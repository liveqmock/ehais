package org.ehais.weixin.controller.wxdev;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.weixin.WXConstants;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wxDev")
public class WXDevController extends WxCommonController{
	
	
	@RequestMapping("/gowx")
	public String gowx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "order_sys_code", required = true) String order_sys_code,
			@RequestParam(value = "extend", required = false) String extend
			){
		try{
			//整理要回调给自己的地址
			String url = request.getScheme()+"://"+request.getServerName()+"/wxDev/getOpenid?wxid="+wxid+"&order_sys_code="+order_sys_code+"&extend="+extend;
			url = URLEncoder.encode(url, "utf-8");
			//整理请求获取openid的微信链接
			//String wxurl = WeiXinUtil.authorize_snsapi(EConstants.weixin_appid, "snsapi_base", url);
			String wxurl = WeiXinUtil.authorize_snsapi(eWPPublicService.getWpPublic(wxid).getAppid(), "snsapi_base", url);
			//滚去请求，即response.sendR***(wxurl);
			return "redirect:"+wxurl;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getOpenid")
	public String getOpenid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "order_sys_code", required = true) String order_sys_code,
			@RequestParam(value = "extend", required = false) String extend
			){
		try{
			//根据code获取openid信息，一样的请求方式
//			OpenidInfo open = WeiXinUtil.getOpenid(code, EConstants.weixin_appid, EConstants.weixin_appsecret);
			OpenidInfo open = WeiXinUtil.getOpenid(code, eWPPublicService.getWpPublic(wxid).getAppid(), eWPPublicService.getWpPublic(wxid).getSecret());
			String openid = open.getOpenid();
			//整合要给别人的参数，get的组合方式，这里写法不一样而已
			modelMap.addAttribute("openid", openid);
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("order_sys_code", order_sys_code);
			modelMap.addAttribute("extend", extend);
			//让链接滚回去
			return "redirect:"+request.getScheme()+"://"+request.getServerName()+"/wxDev/pay";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	/**
	 * 接收另一应用发送过来的带openid的信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param openid
	 * @param order_sys_code
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "order_sys_code", required = true) String order_sys_code,
			@RequestParam(value = "extend", required = false) String extend
			){
		try{
			
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("openid", openid);
			modelMap.addAttribute("order_sys_code", order_sys_code);
			modelMap.addAttribute("extend", extend);
//			String param = "id="+id+"&openid="+openid+"&order_sys_code="+order_sys_code;
//			if(order_sys_code.equals("school_net_pay")){//高校网费支付
//				return "redirect:/debug/weixin/school_net_pay";
//			}else if(order_sys_code.equals("huanbao_pay")){
//				return "redirect:/debug/weixin/huanbao_pay";
//			}else if(order_sys_code.equals("school_net_pay_record")){
//				return "redirect:/debug/weixin/school_net_pay_record";
//			}
			
			return "redirect:/weixin/"+order_sys_code;
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "/wxdev/pay";
	}
	
}
