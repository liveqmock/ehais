package org.ehais.shop.controller.partner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.shop.mapper.PartnerStatisticsMapper;
import org.ehais.shop.model.PartnerStatisticsStoreCount;
import org.ehais.shop.model.PartnerStatisticsUsersCount;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ehais")
public class PartnerStatisticsController extends PartnerCommonController {

	@Autowired
	private PartnerStatisticsMapper partnerStatisticsMapper;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	
	@ResponseBody
	@RequestMapping(value="/manage/partnerStatistics",method=RequestMethod.POST)
	public String partnerStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "start_date", required = true) String start_date,
			@RequestParam(value = "end_date", required = true) String end_date){
		ReturnObject<PartnerStatisticsUsersCount> rm = new ReturnObject<PartnerStatisticsUsersCount>();
		rm.setCode(0);
		try{
			EHaiStoreExample example = new EHaiStoreExample();
			EHaiStoreExample.Criteria c = example.createCriteria();
			Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
			c.andPartnerIdEqualTo(partner_id);
			List<EHaiStore> list = haiStoreMapper.selectByExample(example);
			StringBuilder sb = new StringBuilder();
			for (EHaiStore store : list) {
				sb.append(store.getStoreId()+",");
			}
			
			String store_id_s = sb.substring(0, sb.length()-1).toString();
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			List<PartnerStatisticsUsersCount> listSUserCount = partnerStatisticsMapper.statisticsUsers(store_id_s, start_date, end_date);
			
			map.put("listSUserCount",listSUserCount);
			
			Date startDate = DateUtil.formatDate(start_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			Integer start_time = Long.valueOf(startDate.getTime() / 100000).intValue();
			Integer end_time = Long.valueOf(endDate.getTime() / 100000).intValue();
			List<PartnerStatisticsStoreCount> listSStoreCount = partnerStatisticsMapper.statisticsStore(store_id_s, start_time, end_time);
			map.put("listSStoreCount",listSStoreCount);
			
			rm.setMap(map);			
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
}
