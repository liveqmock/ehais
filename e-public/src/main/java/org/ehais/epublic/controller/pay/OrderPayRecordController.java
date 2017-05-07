package org.ehais.epublic.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiOrderPayRecordAndUsers;
import org.ehais.epublic.service.OrderPayRecordService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  OrderPayRecordController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(OrderPayRecordController.class);

	@Autowired
	private OrderPayRecordService orderpayrecordService;
	
	
	@RequestMapping("/orderpayrecord_list")
	public String orderpayrecord_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "orderpayrecord_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderpayrecord", e);
		}
		return "/admin/orderpayrecord/list";
	}
	
	@ResponseBody
	@RequestMapping("/orderpayrecord_list_json")
	public String orderpayrecord_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "tableId", required = false) Integer tableId,
			@RequestParam(value = "tableName", required = true) String tableName) {
		try{
			ReturnObject<HaiOrderPayRecordAndUsers> rm = orderpayrecordService.orderpayrecord_list_json(request,null, condition, tableId,tableName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderpayrecord", e);
		}
		return null;
	}
	
	

	
}


