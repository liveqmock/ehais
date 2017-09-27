package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="订单管理功能",value="haiOrderController")
@Controller
@RequestMapping("/ehais")
public class  OrderAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(OrderAdminController.class);

	@Autowired
	private OrderInfoService orderInfoService;
	
	
	@EPermissionMethod(intro="打开订单管理页面",value="haiOrderView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/ehaisOrderView")
	public String haiOrderView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiOrderInfo> rm = orderInfoService.orderinfo_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/order/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("order", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回订单管理数据",value="haiOrderListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisOrderListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus,
			@RequestParam(value = "classify", required = true) String classify,
			@RequestParam(value = "orderSn", required = false) String orderSn
			) {
		try{
			ReturnObject<HaiOrderInfoWithBLOBs> rm = orderInfoService.order_list_json(request, condition,orderStatus,orderSn,classify);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("order", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}

