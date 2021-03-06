package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.service.PaymentService;
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
public class  PaymentController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;
	
	
	@RequestMapping("/payment_list")
	public String payment_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "payment_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return "/admin/payment/list";
	}
	
	@ResponseBody
	@RequestMapping("/payment_list_json")
	public String payment_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return null;
	}
	
	

	@RequestMapping("/payment_insert")
	public String payment_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_insert(request);
			rm.setAction("payment_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return "/admin/payment/info";
	}
	
	@RequestMapping(value="/payment_insert_submit",method=RequestMethod.POST)
	public String payment_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiPayment payment
			) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_insert_submit(request,payment);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "payment_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return "redirect:payment_insert";
	}
	
	@RequestMapping("/payment_update")
	public String payment_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = true) Integer payId
			) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_update(request, payId);
			rm.setAction("payment_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return "/admin/payment/info";
	}
	
	@RequestMapping(value="/payment_update_submit",method=RequestMethod.POST)
	public String payment_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiPayment payment
			) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_update_submit(request,payment);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "payment_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
		}
		return "/admin/payment/info";
	}
	
	
	@RequestMapping("/payment_delete")
	public String payment_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = false) Integer payId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiPayment> rm = paymentService.payment_delete(request, payId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "payment_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
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


