package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
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

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="结帐方式信息管理",value="haiPaymentController")
@Controller
@RequestMapping("/admin")
public class  HaiPaymentAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiPaymentAdminController.class);

	@Autowired
	private HaiPaymentService haiPaymentService;
	
	
	@EPermissionMethod(name="查询",intro="打开结帐方式信息页面",value="haiPaymentView",relation="haiPaymentListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiPaymentView")
	public String haiPaymentView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/payment/view";
			//return this.view(request, "/payment/view");
			return this.view(request, "/payment/paymentView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回结帐方式信息数据",value="haiPaymentListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiPaymentListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPaymentListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "paymentName", required = false) String paymentName) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_list_json(request, condition,keySubId,paymentName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增结帐方式信息",value="haiPaymentAddDetail",relation="haiPaymentAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiPaymentAddDetail",method=RequestMethod.GET)
	public String haiPaymentAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/payment/detail";
			return this.view(request, "/payment/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交结帐方式信息",value="haiPaymentAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPaymentAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
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
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑结帐方式信息",value="haiPaymentEditDetail",relation="haiPaymentEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiPaymentEditDetail",method=RequestMethod.POST)
	public String haiPaymentEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = true) Integer payId
			) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_update(request,payId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑结帐方式信息",value="haiPaymentEditDetail",relation="haiPaymentEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiPaymentEditDetail",method=RequestMethod.GET)
	public String haiPaymentEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "payId", required = true) Integer payId
			) {
		try{
			ReturnObject<HaiPayment> rm = haiPaymentService.payment_update(request,payId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/payment/detail";
			return this.view(request, "/payment/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("payment", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交结帐方式信息",value="haiPaymentEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPaymentEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
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
	@EPermissionMethod(name="删除",intro="删除结帐方式信息",value="haiPaymentDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiPaymentDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
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


