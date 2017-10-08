package org.ehais.shop.controller.dining;



import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.tp.TpDiningOrder;
import org.ehais.shop.service.TpDiningOrderService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@EPermissionController(intro="订单功能",value="tpDiningOrderController")
@Controller
@RequestMapping("/dining")
public class  TpDiningOrderController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(TpDiningOrderController.class);

	@Autowired
	private TpDiningOrderService tpDiningOrderService;
	
	
	@EPermissionMethod(intro="打开订单页面",value="tpDiningOrderView",type=PermissionProtocol.URL)
	@RequestMapping("/tpDiningOrderView")
	public String tpDiningOrderView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<TpDiningOrder> rm = tpDiningOrderService.diningorder_list(request);
			modelMap.addAttribute("rm", rm);
			
			Date date = new Date();
			String startDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String endDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("startDate", startDate);
			modelMap.addAttribute("endDate", endDate);
			
			
			return "/dining/order/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回订单数据",value="tpDiningOrderListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/tpDiningOrderListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningOrderListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "orderSn", required = false) String orderSn,
			@RequestParam(value = "goodsDesc", required = false) String goodsDesc,
			@RequestParam(value = "consignee", required = false) String consignee,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus
			
			) {
		try{
			ReturnObject<TpDiningOrder> rm = tpDiningOrderService.diningorder_list_json(request, condition,orderSn,goodsDesc,consignee,mobile,address,orderStatus);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增订单",value="tpDiningOrderAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningOrderAddDetail",method=RequestMethod.GET)
	public String tpDiningOrderAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<TpDiningOrder> rm = tpDiningOrderService.diningorder_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/dining/order/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交订单",value="tpDiningOrderAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningOrderAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningOrderAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("diningorder") TpDiningOrder diningorder,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<TpDiningOrder> rm = tpDiningOrderService.diningorder_insert_submit(request, diningorder);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑订单",value="tpDiningOrderEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningOrderEditDetail",method=RequestMethod.GET)
	public String tpDiningOrderEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId
			) {
		try{
			ReturnObject<TpDiningOrder> rm = tpDiningOrderService.diningorder_update(request,orderId);
			modelMap.addAttribute("rm", rm);
			return "/dining/order/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交订单",value="tpDiningOrderEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningOrderEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningOrderEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId,
			@Valid @ModelAttribute("diningorder") TpDiningOrder diningorder,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(tpDiningOrderService.diningorder_update_submit(request,diningorder));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除订单",value="tpDiningOrderDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningOrderDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningOrderDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(tpDiningOrderService.diningorder_delete(request, orderId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningorder", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


