package org.ehais.shop.controller.api.include;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.OrderStoreStatistics;
import org.ehais.epublic.service.EStoreService;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class ReportStatisticsIController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(ReportStatisticsIController.class);

	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	
	@ResponseBody
	@RequestMapping(value="/orderDiningStatistics",method=RequestMethod.POST)
	public String haiOrderDiningStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "state_date", required = true) String state_date,
			@RequestParam(value = "end_date", required = true) String end_date
			) {	
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 97);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		System.out.println(store_id);
		
//		ReturnObject<OrderStoreStatistics> rm = new ReturnObject<OrderStoreStatistics>();
		ReturnObject<HaiStoreStatistics> rm = new ReturnObject<HaiStoreStatistics>();
		rm.setCode(0);
		
		
		try{
			Date startDate = DateUtil.formatDate(state_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			endDate = DateUtil.addDate(endDate, 1);
			Long start_time = startDate.getTime();
			Long end_time = endDate.getTime();
//			List<OrderStoreStatistics> list = haiOrderInfoMapper.order_dining_statistics(store_id, start_time, end_time);
			List<HaiStoreStatistics> listSS = haiStoreStatisticsMapper.order_store_statistics(store_id, state_date, end_date);
//			rm.setRows(list);
			rm.setRows(listSS);
			rm.setCode(1);
			rm.setMsg("返回成功");
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
	
}
