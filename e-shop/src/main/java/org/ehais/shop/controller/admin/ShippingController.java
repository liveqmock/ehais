package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.service.ShippingService;
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
public class  ShippingController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ShippingController.class);

	@Autowired
	private ShippingService shippingService;
	
	
	@RequestMapping("/shipping_list")
	public String shipping_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "shipping_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return "/admin/shipping/list";
	}
	
	@ResponseBody
	@RequestMapping("/shipping_list_json")
	public String shipping_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiShipping> rm = shippingService.shipping_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return null;
	}
	
	

	@RequestMapping("/shipping_insert")
	public String shipping_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = shippingService.shipping_insert(request);
			rm.setAction("shipping_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return "/admin/shipping/info";
	}
	
	@RequestMapping(value="/shipping_insert_submit",method=RequestMethod.POST)
	public String shipping_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiShippingWithBLOBs shipping
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = shippingService.shipping_insert_submit(request,shipping);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "shipping_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return "redirect:shipping_insert";
	}
	
	@RequestMapping("/shipping_update")
	public String shipping_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "shippingId", required = true) Integer shippingId
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = shippingService.shipping_update(request, shippingId);
			rm.setAction("shipping_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return "/admin/shipping/info";
	}
	
	@RequestMapping(value="/shipping_update_submit",method=RequestMethod.POST)
	public String shipping_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiShippingWithBLOBs shipping
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = shippingService.shipping_update_submit(request,shipping);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "shipping_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
		}
		return "/admin/shipping/info";
	}
	
	
	@RequestMapping("/shipping_delete")
	public String shipping_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "shippingId", required = false) Integer shippingId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiShipping> rm = shippingService.shipping_delete(request, shippingId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "shipping_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
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


