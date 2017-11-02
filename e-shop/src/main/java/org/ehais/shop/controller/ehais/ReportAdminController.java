package org.ehais.shop.controller.ehais;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.OrderDiningStatistics;
import org.ehais.epublic.service.EStoreService;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ehais")
public class ReportAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(OrderAdminController.class);

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	
	@RequestMapping("/manage/diningReport")
	public String haiOrderView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{			
//			ReturnObject<HaiOrderInfo> rm = orderInfoService.orderinfo_list(request);
//			modelMap.addAttribute("rm", rm);
			
			//下面是代理查帐使用的情况
			String adminClassify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
			if(StringUtils.isNotBlank(adminClassify) && adminClassify.equals(EAdminClassifyEnum.partner)){
				Long adminId = (Long) request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
				EHaiAdminUserWithBLOBs adminUser = eHaiAdminUserMapper.selectByPrimaryKey(adminId);
				List<EHaiStore> listStore = eStoreService.partnerStore(adminUser.getPartnerId());
				modelMap.addAttribute("listStore", listStore);
			}
			
			
			return "/"+this.getStoreTheme(request)+"/report/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/manage/OrderDiningStatistics",method=RequestMethod.POST)
	public String haiOrderDiningStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "state_date", required = true) String state_date,
			@RequestParam(value = "end_date", required = true) String end_date,
			@RequestParam(value = "store_id", required = false) Integer store_id
			) {	
		ReturnObject<OrderDiningStatistics> rm = new ReturnObject<OrderDiningStatistics>();
		rm.setCode(0);
		try{
			if(store_id == null || store_id == 0)store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			Date startDate = DateUtil.formatDate(state_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			endDate = DateUtil.addDate(endDate, 1);
			Integer start_time = Long.valueOf(startDate.getTime() / 1000).intValue();
			Integer end_time = Long.valueOf(endDate.getTime() / 1000).intValue();
			List<OrderDiningStatistics> list = haiOrderInfoMapper.order_dining_statistics(store_id, start_time, end_time);
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
}
