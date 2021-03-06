package org.ehais.shop.controller.ehais;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.ehais.epublic.model.OrderStoreStatistics;
import org.ehais.epublic.model.OrderGoodsDaySaleStatistics;
import org.ehais.epublic.model.OrderGoodsStatistics;
import org.ehais.epublic.service.EStoreService;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
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
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	
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
	
	/**
	 * 统计菜品销售走势，适用于所有菜品的统计
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage/allOrderGoodsReport")
	public String orderGoodsReport(ModelMap modelMap,
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
			
			
			return "/"+this.getStoreTheme(request)+"/report/all_order_goods_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/manage/AllOrderGoodsStatistics",method=RequestMethod.POST)
	public String AllOrderGoodsStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "state_date", required = true) String state_date,
			@RequestParam(value = "end_date", required = true) String end_date,
			@RequestParam(value = "store_id", required = false) Integer store_id
			) {	
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		rm.setCode(0);
		try{
			if(store_id == null || store_id == 0)store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			Date startDate = DateUtil.formatDate(state_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			endDate = DateUtil.addDate(endDate, 1);
			Long start_time = startDate.getTime();
			Long end_time = endDate.getTime();
			
			List<HaiGoods> all_goods = haiGoodsMapper.select_goods_name(store_id);
			
			List<Long> order_id_list = haiOrderInfoMapper.order_id_paytime_record(store_id, start_time, end_time);
			if(order_id_list == null || order_id_list.size() == 0){
				rm.setRows(all_goods);
				rm.setCode(1);
				return this.writeJson(rm);
			}
			
			String orderIdJoin = StringUtils.join(order_id_list.toArray(), ",");
			
			List<OrderGoodsStatistics> goodsStatistics = haiOrderInfoMapper.order_goods_statistics(orderIdJoin);
			
			
			for (OrderGoodsStatistics go : goodsStatistics) {
				for (HaiGoods g : all_goods) {
					if(g.getGoodsId().longValue() == go.getGoodsId().longValue()){
						g.setSaleCount(go.getQuantity());
						continue;
					}
				}
			}
			
			rm.setRows(all_goods);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
	
	/**
	 * 某商品的销售量统计页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage/oneOrderGoodsReport")
	public String oneOrderGoodsReport(ModelMap modelMap,
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
			
			
			
			return "/"+this.getStoreTheme(request)+"/report/one_order_goods_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	/**
	 * 获取某商户的所有分类与商品信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param store_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/manage/loadCategoryGoodsStatistics",method=RequestMethod.POST)
	public String loadCategoryGoodsStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = false) Integer store_id
			) {	
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		
		if(store_id == null || store_id == 0)store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			HaiGoodsExample gexp = new HaiGoodsExample();
			gexp.createCriteria().andStoreIdEqualTo(store_id);
			List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(gexp);
			HaiCategoryExample cexp = new HaiCategoryExample();
			cexp.createCriteria().andStoreIdEqualTo(store_id);
			cexp.setOrderByClause("sort_order asc");
			List<HaiCategory> listCategory = haiCategoryMapper.selectByExample(cexp);
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("listGoods", listGoods);
			map.put("listCategory", listCategory);
			
			rm.setMap(map);
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	/**
	 * 查看某商户某商品某时间段的销售量
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param state_date
	 * @param end_date
	 * @param store_id
	 * @param goods_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/manage/OneOrderGoodsStatistics",method=RequestMethod.POST)
	public String OneOrderGoodsStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "state_date", required = true) String state_date,
			@RequestParam(value = "end_date", required = true) String end_date,
			@RequestParam(value = "store_id", required = false) Integer store_id,
			@RequestParam(value = "goods_id", required = false) Long goods_id
			) {	
		ReturnObject<OrderGoodsDaySaleStatistics> rm = new ReturnObject<OrderGoodsDaySaleStatistics>();
		rm.setCode(0);
		try{
			if(store_id == null || store_id == 0)store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			Date startDate = DateUtil.formatDate(state_date, DateUtil.FORMATSTR_3);
			Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
			endDate = DateUtil.addDate(endDate, 1);
			Long start_time = startDate.getTime();
			Long end_time = endDate.getTime();
			
			List<OrderGoodsDaySaleStatistics> order_goods_day_sale_list = haiOrderInfoMapper.order_goods_day_sale_statistics(store_id, start_time, end_time, goods_id);
			int d = DateUtil.diffDate(startDate, endDate);
			List<OrderGoodsDaySaleStatistics> day_sale = new ArrayList<OrderGoodsDaySaleStatistics>();
			boolean f = true;
			for(int i = 0 ; i < d ; i++){
				f = true;
				Date cDate = DateUtil.addDate(startDate, i);
				for (OrderGoodsDaySaleStatistics og : order_goods_day_sale_list) {
					if(DateUtil.compare_date(cDate, og.getSaleDate()) == 0){
						day_sale.add(og);
						f = false;
						break;
					}
				}
				if(f){
					OrderGoodsDaySaleStatistics ogs = new OrderGoodsDaySaleStatistics();
					ogs.setQuantity(0);
					ogs.setSaleDate(cDate);
					day_sale.add(ogs);
				}
			}
			rm.setRows(day_sale);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
	
}
