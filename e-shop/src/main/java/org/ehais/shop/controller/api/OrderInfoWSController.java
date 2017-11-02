package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EShippingStatusEnum;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.shop.controller.api.include.OrderInfoIController;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, order_status,pay_status,shipping_status,null,condition,order_sn));
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
	
	
	
	@ResponseBody
	@RequestMapping(value="/new_order_tip",method=RequestMethod.POST)
	public String new_order_tip(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "paytime", required = true) Integer paytime){
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(store_id == null || store_id == 0){
			rm.setMsg("请登录");
			return this.writeJson(rm);
		}
		try {
			HaiOrderInfoExample exam = new HaiOrderInfoExample();
			exam.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andPayTimeGreaterThan(paytime)
			.andOrderStatusEqualTo(EOrderStatusEnum.success);
			exam.setLimitStart(0);
			exam.setLimitEnd(1);
			exam.setOrderByClause("pay_time desc");
			List<HaiOrderInfo> list = haiOrderInfoMapper.selectByExample(exam);
			rm.setRows(list);
			long c = haiOrderInfoMapper.countByExample(exam);
			rm.setTotal(c);
			rm.setCode(1);
			rm.setMsg("success");
			return this.writeJson(rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/comment_wait_list")
	public String comment_wait_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition
			){
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try {
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, EOrderStatusEnum.finish,EPayStatusEnum.success,EShippingStatusEnum.receiving,false,condition,null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
}
