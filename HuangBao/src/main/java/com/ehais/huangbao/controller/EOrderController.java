package com.ehais.huangbao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.EOrder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.huangbao.service.WXPayService;


@Controller
@RequestMapping("/weixin")
public class EOrderController extends WxCommonController {

	@Autowired
	private WXPayService wxPayService;
	
	@RequestMapping("/eorder/eorder_list")
	public String eorder_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "eorder_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/eorder/list";
	}
	
	@ResponseBody
	@RequestMapping("/eorder/eorder_list_json")
	public String eorder_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			
			ReturnObject<EOrder> ro = wxPayService.eOrderListWXID(wxid, 1, page, len);
			JSONObject json = new JSONObject(ro);
			return json.toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	
}
