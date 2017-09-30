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
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.service.HaiPaymentService;
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



@EPermissionController(intro="支付管理功能",value="haiPaymentController")
@Controller
@RequestMapping("/ehais")
public class  HaiPaymentAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiPaymentAdminController.class);

	@Autowired
	private HaiPaymentService haiPaymentService;
	
	
	@EPermissionMethod(intro="打开支付管理页面",value="haiPaymentView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiPaymentView")
	public String haiPaymentView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/payment/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回支付管理数据",value="haiPaymentListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiPaymentListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPaymentListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "payName", required = false) String payName) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_list_json(request, condition,keySubId,payName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增支付管理",value="haiPaymentAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiPaymentAddDetail",method=RequestMethod.GET)
	public String haiPaymentAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/payment/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交支付管理",value="haiPaymentAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiPaymentAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPaymentAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("payment") HaiPayment payment,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_insert_submit(request, payment);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑支付管理",value="haiPaymentEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiPaymentEditDetail",method=RequestMethod.GET)
	public String haiPaymentEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = true) Integer payId
			) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_update(request,payId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/payment/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交支付管理",value="haiPaymentEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiPaymentEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPaymentEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("payment") HaiPayment payment,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiPaymentService.payment_update_submit(request,payment));
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除支付管理",value="haiPaymentDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiPaymentDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPaymentDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = true) Integer payId
			) {
		try{
			return this.writeJson(haiPaymentService.payment_delete(request, payId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJSON(e);
		}
	}
	
	


	
}


