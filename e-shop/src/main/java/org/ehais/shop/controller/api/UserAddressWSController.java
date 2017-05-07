package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.shop.controller.api.include.UserAddressIController;
import org.ehais.shop.model.HaiUserAddress;
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
public class UserAddressWSController extends UserAddressIController{
	

	@ResponseBody
	@RequestMapping("/useraddress_lists")
	public String useraddress_lists(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		if(user_id==null) user_id = 1L;//临时使用
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_lists(request,user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/useraddress_info")
	public String useraddress_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "addressId", required = true) Long addressId) {
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		if(user_id==null) user_id = 1;//临时使用
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_info(request,addressId,user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	

	@ResponseBody
	@RequestMapping(value="/useraddress_add_submit",method=RequestMethod.POST)
	public String useraddress_add_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiUserAddress useraddress
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_add_submit(request,useraddress,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/useraddress_edit_submit",method=RequestMethod.POST)
	public String useraddress_edit_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiUserAddress useraddress
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_edit_submit(request,useraddress,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/useraddress_delete_sumbit")
	public String useraddress_delete_sumbit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "addressId", required = true) Long addressId
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_delete_sumbit(request, addressId,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	
	
}
