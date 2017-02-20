package org.ehais.weixin.controller.project;

import org.ehais.weixin.controller.WxCommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixin")
public class SchoolController extends WxCommonController {

//	@Autowired
//	private SchoolService schoolService;
//	
//	@RequestMapping("/school_net_pay")
//	public String net_pay(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "wxid", required = true) Integer wxid,
//			@RequestParam(value = "code", required = false) String code,
//			@RequestParam(value = "openid", required = false) String openid
//			){
//		try{
//			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));	
//			modelMap.addAttribute("wxid", wxid);
//			modelMap.addAttribute("action", "school_paySubmit");
//			modelMap.addAttribute("order_sys_code", "school_net_pay");
//		}catch(Exception e){
//			e.printStackTrace();
//		}	
//		return "project/school/net_pay";
//	}
//	
//	
//	@ResponseBody
//	@RequestMapping(value="/school_paySubmit",method = RequestMethod.POST)
//	public String paySubmit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "wxid", required = true) Integer wxid,
//			@RequestParam(value = "openid", required = true) String openid,
//			@RequestParam(value = "amount", required = true) String amount,
//			@RequestParam(value = "realname", required = false) String realname,
//			@RequestParam(value = "remark", required = false) String remark,
//			@RequestParam(value = "conId", required = true) Integer conId) {		
//		try{
//			ReturnObject<WeiXinWCPay> ro = schoolService.UnifiedOrder(
//					wxid,
//					openid, 
//					amount, 
//					realname, 
//					"", 
//					remark,
//					conId, 
//					"JSAPI",
//					this.getWpPublic(wxid),
//					request);
//			JSONObject json = JSONObject.fromObject(ro);
//			return json.toString();
//		}catch(Exception e){
//			e.printStackTrace();
//		}	
//		
//		return null;
//	}
//	
//	@RequestMapping("/school_net_pay_record")
//	public String net_pay_record(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "wxid", required = true) Integer wxid,
//			@RequestParam(value = "code", required = false) String code,
//			@RequestParam(value = "openid", required = false) String openid,
//			@RequestParam(value = "order_sys_code", required = false) String order_sys_code){
//		try{
//			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));			
//			modelMap.addAttribute("wxid", wxid);
//			modelMap.addAttribute("action", "school_net_pay_record_json");
//			modelMap.addAttribute("order_sys_code", "school_net_pay");
//		}catch(Exception e){
//			e.printStackTrace();
//		}	
//		return "project/school/net_pay_record";
//	}
//
//	@ResponseBody
//	@RequestMapping("/school_net_pay_record_json")
//	public String net_pay_record_json(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "wxid", required = true) Integer wxid,
//			@RequestParam(value = "openid", required = true) String openid,
//			@RequestParam(value = "order_sys_code", required = false) String order_sys_code,
//			@RequestParam(value = "page", required = true) Integer page,
//			@RequestParam(value = "len", required = true) Integer len){
//		try{
//			
//			ReturnObject<EOrder> ro = schoolService.eOrderList(openid, order_sys_code, 1, page, len);
//			JSONObject json = JSONObject.fromObject(ro);
//			return json.toString();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}	
//		return null;
//	}
	
}
