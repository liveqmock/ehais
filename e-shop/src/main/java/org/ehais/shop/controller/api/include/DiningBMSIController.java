package org.ehais.shop.controller.api.include;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class DiningBMSIController extends CommonController {
	private static Logger log = LoggerFactory.getLogger(DiningBMSIController.class);
	
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	
	@ResponseBody
	@RequestMapping(value="/haiDiningStatisticsJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 97);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		System.out.println(store_id);
		
		
		try{
			Date date = new Date();
//			date = DateUtil.addDate(date, -3);
			String pay_date = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			
			
			HaiStoreStatistics sst = haiStoreStatisticsMapper.order_store_statistics_amount(store_id, pay_date, pay_date);
			
			ReturnObject<HaiStoreStatistics> rm = new ReturnObject<HaiStoreStatistics>();
			rm.setModel(sst);
			Map<String, Object> map = new HashMap<String, Object>();
			EHaiStore store = eHaiStoreMapper.selectByPrimaryKey(store_id);
			if(store == null)store = new EHaiStore();
			map.put("balance", (store.getBalance()==null?0:store.getBalance()) * (store.getThousandsRate()==null?0:store.getThousandsRate()) / 1000);
			
			rm.setMap(map);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	
}
