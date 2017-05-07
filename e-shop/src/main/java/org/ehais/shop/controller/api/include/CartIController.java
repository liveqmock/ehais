package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartWithBLOBs;
import org.ehais.shop.service.CartService;
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


public class  CartIController extends CommonController {

	protected static Logger log = LoggerFactory.getLogger(CartIController.class);

	@Autowired
	protected CartService cartService;
	

	
	
	@ResponseBody
	@RequestMapping("/cart_delete")
	public String cart_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recId", required = false) Long recId,
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		try{
			ReturnObject<HaiCart> rm = cartService.cart_delete(request, recId , user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
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
			return this.writeJson(service._list(store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	**/
	
}


