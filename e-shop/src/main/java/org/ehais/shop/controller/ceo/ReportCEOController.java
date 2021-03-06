package org.ehais.shop.controller.ceo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiPartnerMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.HaiStoreStatisticsExample;
import org.ehais.epublic.model.OrderStoreStatistics;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.HaiStoreService;
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

@EPermissionModuleGroup(name="模组")

@EPermissionController(intro="基础数据",value="ReportCEOController")
@Controller
@RequestMapping("/ceo")
public class ReportCEOController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(ReportCEOController.class);

	@Autowired
	private HaiStoreService haiStoreService;
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	
	
	@EPermissionMethod(intro="打开商户管理页面",value="income",type=PermissionProtocol.URL)
	@RequestMapping("/income")
	public String income(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
			List<HaiPartner> partnerList = 	haiPartnerMapper.selectByExample(null);
			modelMap.addAttribute("partnerList", partnerList);
			
			return "/ceo/report/income";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/IncomeStatistics",method=RequestMethod.POST)
	public String IncomeStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "state_date", required = true) String state_date,
			@RequestParam(value = "end_date", required = true) String end_date,
			@RequestParam(value = "store_id", required = false) Integer store_id
			) {	
		ReturnObject<OrderStoreStatistics> rm = new ReturnObject<OrderStoreStatistics>();
		rm.setCode(0);
		try{
			if(store_id == null || store_id == 0)store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			Date startDate = DateUtil.formatDate(state_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			endDate = DateUtil.addDate(endDate, 1);
			Long start_time = startDate.getTime();
			Long end_time = endDate.getTime();
			List<OrderStoreStatistics> list = haiOrderInfoMapper.order_dining_statistics(store_id, start_time, end_time);
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/partnerStoreList",method=RequestMethod.POST)
	public String partnerStoreList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "partnerId", required = false) Integer partnerId){
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		try{
			EHaiStoreExample exp = new EHaiStoreExample();
			exp.createCriteria().andPartnerIdEqualTo(partnerId);
			List<EHaiStore> list = eHaiStoreMapper.selectByExample(exp);
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJSON( e);
		}
		return this.writeJson(rm);
	}
	
	
	@EPermissionMethod(intro="打开商户管理页面",value="storeStatistics",relation="storeStatisticsJSON",type=PermissionProtocol.URL)
	@RequestMapping("/storeStatistics")
	public String storeStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Date date = new Date();
			date = DateUtil.addDate(date, -1);
			modelMap.addAttribute("date", DateUtil.formatDate(date, DateUtil.FORMATSTR_3));
			return "/ceo/report/storeStatistics";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/storeStatisticsJSON",method=RequestMethod.POST)
	public String storeStatisticsJSON(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "date", required = false) String date){
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		try{
			
			List<HaiPartner> list = haiPartnerMapper.selectByExample(null);
			rm.setRows(list);
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			List<EHaiStore> listStore = eHaiStoreMapper.selectByExample(null);
			map.put("listStore", listStore);
			
			Date cdate = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			HaiStoreStatisticsExample exp = new HaiStoreStatisticsExample();
			exp.createCriteria().andStatisticsDateEqualTo(cdate);
			
			List<HaiStoreStatistics> listStoreStatistics = haiStoreStatisticsMapper.selectByExample(exp);
			map.put("listStoreStatistics", listStoreStatistics);
			
			rm.setTotal(list.size() + listStore.size());
			rm.setMap(map);
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJSON( e);
		}
		return this.writeJson(rm);
	}
	
	
}
