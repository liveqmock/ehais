package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/api")
public class UserAddressApiController extends UserAddressIController{

	
	@ResponseBody
	@RequestMapping("/useraddress_lists")
	public String useraddress_lists(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code) {
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
			@RequestParam(value = "addressId", required = true) Long addressId,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code) {
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
			@RequestParam(value = "user_id", required = true) Long user_id,
			@ModelAttribute HaiUserAddress useraddress
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_add_submit(request,useraddress,user_id);
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
			@RequestParam(value = "user_id", required = true) Long user_id,
			@ModelAttribute HaiUserAddress useraddress
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_edit_submit(request,useraddress,user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/useraddress_delete_sumbit")
	public String useraddress_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "addressId", required = true) Long addressId,
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		try{
			ReturnObject<HaiUserAddress> rm = useraddressService.useraddress_delete_sumbit(request, addressId,user_id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("useraddress", e);
		}
		return null;
	}
	
	
	
	
	
}
