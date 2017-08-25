package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.shop.controller.api.include.CartIController;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartWithBLOBs;
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
public class CartWSController extends CartIController{

	//网页接口是获取session
	@ResponseBody
	@RequestMapping("/cart_list")
	public String cart_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		if(user_id==null) user_id = 1;//临时使用
		try{
			ReturnObject<HaiCart> rm = cartService.cart_list(request, null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/cart_quantity")
	public String cart_quantity(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		try{
			ReturnObject<HaiCart> rm = cartService.cart_quantity(request, null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/cart_add_submit",method=RequestMethod.POST)
	public String cart_add_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute HaiCartWithBLOBs cart
			@RequestParam(value = "goods_id", required = true) Long goods_id,
			@RequestParam(value = "store_id", required = false) Integer store_id,
			@RequestParam(value = "quantity", required = true) Integer quantity,
			@RequestParam(value = "parent_user_id", required = true) Long parent_user_id,
			@RequestParam(value = "agency_id", required = true) Integer agency_id,
			@RequestParam(value = "article_id", required = true) Integer article_id
			) {
		try{
			ReturnObject<HaiCartWithBLOBs> rm = cartService.cart_add_submit(request,goods_id,store_id,quantity,null,null,parent_user_id,agency_id,article_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/cart_edit_submit",method=RequestMethod.POST)
	public String cart_edit_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recId", required = true) Long recId,
			@RequestParam(value = "goods_id", required = true) Long goods_id,
			@RequestParam(value = "store_id", required = false) Integer store_id,
			@RequestParam(value = "quantity", required = true) Integer quantity
			) {
		try{
			ReturnObject<HaiCartWithBLOBs> rm = cartService.cart_edit_quantity(request, recId,  goods_id,store_id,quantity, null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	
}
