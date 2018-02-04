package org.ehais.shop.controller.ehais;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.enums.EShippingStatusEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
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



@EPermissionController(intro="订单管理功能",value="haiOrderController")
@Controller
@RequestMapping("/ehais")
public class  OrderAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(OrderAdminController.class);

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	
	
	@EPermissionMethod(intro="打开订单管理页面",value="ehaisOrderView",relation="ehaisOrderListJson",type=PermissionProtocol.URL)
	@RequestMapping("/manage/ehaisOrderView")
	public String haiOrderView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiOrderInfo> rm = orderInfoService.orderinfo_list(request);
			modelMap.addAttribute("rm", rm);
			Date date = new Date();
			String startDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String endDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("startDate", startDate);
			modelMap.addAttribute("endDate", endDate);
			
			
			String adminClassify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
			if(StringUtils.isNotBlank(adminClassify) && adminClassify.equals(EAdminClassifyEnum.partner)){
				Long adminId = (Long) request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
				EHaiAdminUserWithBLOBs adminUser = eHaiAdminUserMapper.selectByPrimaryKey(adminId);
				List<EHaiStore> listStore = eStoreService.partnerStore(adminUser.getPartnerId());
				modelMap.addAttribute("listStore", listStore);
			}
			
			
			return "/"+this.getStoreTheme(request)+"/order/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("order", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回订单管理数据",value="ehaisOrderListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisOrderListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus,
			@RequestParam(value = "classify", required = true) String classify,
			@RequestParam(value = "orderSn", required = false) String orderSn,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate
			) {
		try{
			ReturnObject<HaiOrderInfoWithBLOBs> rm = orderInfoService.order_list_json(request, condition,orderStatus,orderSn,classify, startDate, endDate);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("order", e);
			return this.errorJSON(e);
		}
	}
	
	
	/**
	 * 获取订单信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/manage/orderinfoStore")
	public String orderinfoStore(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId){
		try {
			return this.writeJson(orderInfoService.orderinfo_find(request, orderId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/manage/orderShippingSave")
	public String orderShippingSave(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId,
			@RequestParam(value = "shippingId", required = true) Integer shippingId,
			@RequestParam(value = "shippingName", required = true) String shippingName,
			@RequestParam(value = "shippingNumber", required = true) String shippingNumber){
		try {
			
			ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
			rm.setCode(0);
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			
			HaiOrderInfoExample oexa = new HaiOrderInfoExample();
			oexa.createCriteria().andStoreIdEqualTo(store_id).andOrderIdEqualTo(orderId);
			List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(oexa);
			if(list==null || list.size()==0){
				rm.setMsg("无此订单");
				return this.writeJson(rm);
			}
			HaiOrderInfoWithBLOBs model = list.get(0);
			
			model.setShippingId(shippingId);
			model.setShippingName(shippingName);
			model.setShippingNumber(shippingNumber);
			model.setShippingStatus(EShippingStatusEnum.shipments);
			
			haiOrderInfoMapper.updateByPrimaryKeySelective(model);
			
			rm.setCode(1);
			rm.setMsg("保存成功");
			return this.writeJson(rm);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
}


