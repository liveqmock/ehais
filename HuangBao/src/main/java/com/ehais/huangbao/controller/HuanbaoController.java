package com.ehais.huangbao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.EOrder;
import org.ehais.weixin.model.WeiXinWCPay;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.huangbao.model.HaiContribution;
import com.ehais.huangbao.service.HuanBaoService;

@Controller
@RequestMapping("/weixin")
public class HuanbaoController extends WxCommonController{
	private static Logger log = LoggerFactory.getLogger(HuanbaoController.class);

	@Autowired
	private HuanBaoService huanBaoService;
	
//	@RequestMapping("/huanbao_gopay")
//	public String goPay(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
//		String url = WeiXinUtil.authorize_snsapi(
//				EConstants.weixin_appid, 
//				"snsapi_base", 
//				request.getScheme()+"://"+request.getServerName()+"/weixin/huanbao_pay"
//				);
//		log.info("redirect:"+url);
//		return "redirect:"+url;
//	}
	
	@RequestMapping("/contribution")
	public String Contribution(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "cat_code", required = false) String cat_code) {		
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "contribution_json");
			modelMap.addAttribute("cat_code", cat_code);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/contribution_list";
	}
	
	
	@ResponseBody
	@RequestMapping("/contribution_json")
	public String Contribution_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {		
		try{
			ReturnObject<HaiContribution> rm = huanBaoService.Contribution_List(wxid, cat_code, page, len);
			return this.writeJson(rm);			
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	
	@RequestMapping("/contribution_detail")
	public String Contribution_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "extend", required = true) Integer extend) {		
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("openid", openid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("title", "环保公益基金捐款");
			ReturnObject<HaiContribution> rm = huanBaoService.Contribution_Detail(wxid, extend);
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/contribution_detail";
	}
	
	
	@RequestMapping("/huanbao_pay")
	public String huanbao_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid) {		
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("order_sys_code", "huanbao_pay");
			
			modelMap.addAttribute("title", "环保公益基金捐款");
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	
	/**公益捐款
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param code
	 * @param openid
	 * @return
	 */
	@RequestMapping("/huanbao_hongyi_pay")
	public String huanbao_hongyi_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid) {		
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("order_sys_code", "");
			modelMap.addAttribute("title", "环保公益活动捐款");
			
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("huanbao_djbh_pay", ResourceUtil.getProValue("huanbao_djbh_pay"));
			payMap.put("huanbao_hslbh_pay", ResourceUtil.getProValue("huanbao_hslbh_pay"));
			payMap.put("huanbao_hgbh_pay", ResourceUtil.getProValue("huanbao_hgbh_pay"));
			
			
			modelMap.addAttribute("payMap", payMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	
	/**专项目捐款
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param code
	 * @param openid
	 * @return
	 */
	@RequestMapping("/huanbao_zxm_pay")
	public String huanbao_zxm_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid) {		
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("order_sys_code", "");
			modelMap.addAttribute("title", "专项目基金捐款");
			
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("huanbao_xj_pay", ResourceUtil.getProValue("huanbao_xj_pay"));
			payMap.put("huanbao_zb_pay", ResourceUtil.getProValue("huanbao_zb_pay"));
			payMap.put("huanbao_hjwq_pay", ResourceUtil.getProValue("huanbao_hjwq_pay"));
			payMap.put("huanbao_stwh_pay", ResourceUtil.getProValue("huanbao_stwh_pay"));
			
			
			modelMap.addAttribute("payMap", payMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	/**省环保志愿者活动周捐款
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param code
	 * @param openid
	 * @return
	 */
	@RequestMapping("/huanbao_zygz_pay")
	public String huanbao_zygz_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid) {		
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("order_sys_code", "huanbao_zygz_pay");
			
			modelMap.addAttribute("title", "省环保志愿者活动周捐款");
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	
	/**省环保志愿者活动周捐款
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param code
	 * @param openid
	 * @return
	 */
	@RequestMapping("/huanbao_green_chou_pay")
	public String huanbao_green_chou_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid) {		
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_paySubmit");
			modelMap.addAttribute("order_sys_code", "huanbao_zygz_pay");
			
			modelMap.addAttribute("title", "环保绿筹");
			
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("huanbao_wwpczs_pay", ResourceUtil.getProValue("huanbao_wwpczs_pay"));
			
			
			modelMap.addAttribute("payMap", payMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "project/huanbao/pay";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/huanbao_paySubmit",method = RequestMethod.POST)
	public String paySubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "realname", required = false) String realname,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "conId", required = true) Integer conId) {		
		try{
			ReturnObject<WeiXinWCPay> ro = huanBaoService.UnifiedOrder(
					wxid,
					openid, 
					amount, 
					realname, 
					mobile, 
					remark,
					conId, 
					"JSAPI",
					this.getWpPublic(wxid),
					request);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return null;
	}
	
	
	@RequestMapping("/huanbao_pay_record")
	public String net_pay_record(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = false) String openid,
			@RequestParam(value = "order_sys_code", required = false) String order_sys_code){
		try{
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));			
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "huanbao_pay_record_json");
			modelMap.addAttribute("order_sys_code", "");
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "project/huanbao/pay_record";
	}
	
	
	@ResponseBody
	@RequestMapping("/huanbao_pay_record_json")
	public String net_pay_record_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "order_sys_code", required = false) String order_sys_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		try{
			
			ReturnObject<EOrder> ro = huanBaoService.eOrderListWXID(wxid, 1, page, len);
			JSONObject json = new JSONObject(ro);
			return json.toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	
}
