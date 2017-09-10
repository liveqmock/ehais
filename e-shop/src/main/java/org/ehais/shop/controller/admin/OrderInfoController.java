package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  OrderInfoController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(OrderInfoController.class);

	@Autowired
	private OrderInfoService orderinfoService;
	
	
	@RequestMapping("/orderinfo_list")
	public String orderinfo_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "orderinfo_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return "/admin/orderinfo/list";
	}
	
	@ResponseBody
	@RequestMapping("/orderinfo_list_json")
	public String orderinfo_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return null;
	}
	
	

	@RequestMapping("/orderinfo_insert")
	public String orderinfo_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_insert(request);
			rm.setAction("orderinfo_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return "/admin/orderinfo/info";
	}
	
	@RequestMapping(value="/orderinfo_insert_submit",method=RequestMethod.POST)
	public String orderinfo_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiOrderInfoWithBLOBs orderinfo
			) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_insert_submit(request,orderinfo);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "orderinfo_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return "redirect:orderinfo_insert";
	}
	
	@RequestMapping("/orderinfo_update")
	public String orderinfo_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId
			) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_update(request, orderId);
			rm.setAction("orderinfo_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return "/admin/orderinfo/info";
	}
	
	@RequestMapping(value="/orderinfo_update_submit",method=RequestMethod.POST)
	public String orderinfo_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiOrderInfoWithBLOBs orderinfo
			) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_update_submit(request,orderinfo);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "orderinfo_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return "/admin/orderinfo/info";
	}
	
	
	@RequestMapping("/orderinfo_delete")
	public String orderinfo_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = false) Long orderId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiOrderInfo> rm = orderinfoService.orderinfo_delete(request, orderId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "orderinfo_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
		}
		return null;
	}
	
	/**
	

	@ResponseBody
	@RequestMapping("/controller{store_id}")
	public String controller(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@RequestParam(value = "code", required = true) String code){
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	22///////////////////////////////////////////////////	
	@ResponseBody
	@RequestMapping("/_list")
	public String _list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(service._list(request,store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	**/
	
}


