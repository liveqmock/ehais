package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.epublic.cache.EAdminTokenCacheManager;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.api.include.OrderInfoIController;
import org.ehais.shop.mapper.HaiDiningPrintTimeMapper;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiDiningPrintTime;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class OrderInfoApiController extends OrderInfoIController{

	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private HaiDiningPrintTimeMapper haiDiningPrintTimeMapper;
	
	
	@ResponseBody
	@RequestMapping("/orderinfo_list")
	public String orderinfo_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code,
			@RequestParam(value = "order_status", required = false) Integer order_status,
			@RequestParam(value = "pay_status", required = false) Integer pay_status,
			@RequestParam(value = "shipping_status", required = false) Integer shipping_status,
			@RequestParam(value = "order_sn", required = false) String order_sn,
			@ModelAttribute EConditionObject condition
			){
		
		try {
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, order_status,pay_status,shipping_status,null,condition,order_sn));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/orderinfo_info")
	public String orderinfo_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code,
			@RequestParam(value = "orderId", required = true) Long orderId){
		
		try {
			return this.writeJson(orderinfoService.orderinfo_info(request, user_id, orderId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/dining_order_list",method=RequestMethod.POST)
	public String dining_order_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "token", required = true) String token){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		String t = EAdminTokenCacheManager.getInstance().getAdminToken(store_id);
		if(StringUtils.isBlank(t) || !t.equals(token)){
			rm.setMsg("token wrong");
			return this.writeJson(rm);
		}
		
		try{
//			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			
			
			HaiDiningPrintTime p = haiDiningPrintTimeMapper.selectByPrimaryKey(store_id);
			if(p == null){
				p = new HaiDiningPrintTime();
				p.setStoreId(store_id);
				p.setPrintTime(System.currentTimeMillis());
				haiDiningPrintTimeMapper.insert(p);
			}
			HaiOrderInfoExample example = new HaiOrderInfoExample();
			HaiOrderInfoExample.Criteria c = example.createCriteria();
			c.andStoreIdEqualTo(store_id).andPayTimeGreaterThan(p.getPrintTime());
			example.setOrderByClause("pay_time asc");
			List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
			
			if(list != null && list.size() > 0){
				List<Long> orderIds = new ArrayList<Long>();
				for (HaiOrderInfoWithBLOBs haiOrderInfoWithBLOBs : list) {
					orderIds.add(haiOrderInfoWithBLOBs.getOrderId());
					Map<String,Object> mapSign = SignUtil.getDiningId(haiOrderInfoWithBLOBs.getSid(), wp.getToken());
					haiOrderInfoWithBLOBs.setZipcode(mapSign.get("tableNo").toString());
				}
				HaiOrderGoodsExample gExp = new HaiOrderGoodsExample();
				gExp.createCriteria().andOrderIdIn(orderIds);
				List<HaiOrderGoods> listGoods = haiOrderGoodsMapper.selectByExampleWithBLOBs(gExp);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("listGoods", listGoods);
				rm.setMap(map);
				
				HaiOrderInfo o = list.get(list.size() - 1);
				p.setPrintTime(o.getPayTime());
				haiDiningPrintTimeMapper.updateByPrimaryKey(p);
				
			}
			rm.setCode(1);
			rm.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
	
	
	/**
	 * 传递时间的打印
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param paytime
	 * @param store_id
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dining_order_list_print",method=RequestMethod.POST)
	public String dining_order_list_print(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "paytime", required = true) Long paytime,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "token", required = true) String token){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		String t = EAdminTokenCacheManager.getInstance().getAdminToken(store_id);
		if(StringUtils.isBlank(t) || !t.equals(token)){
			rm.setMsg("token wrong");
			return this.writeJson(rm);
		}
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			HaiOrderInfoExample example = new HaiOrderInfoExample();
			HaiOrderInfoExample.Criteria c = example.createCriteria();
			c.andStoreIdEqualTo(store_id).andPayTimeGreaterThan(paytime);//////
			example.setOrderByClause("pay_time asc");
			List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
			
			if(list != null && list.size() > 0){
				List<Long> orderIds = new ArrayList<Long>();
				for (HaiOrderInfoWithBLOBs haiOrderInfoWithBLOBs : list) {
					orderIds.add(haiOrderInfoWithBLOBs.getOrderId());
					Map<String,Object> mapSign = SignUtil.getDiningId(haiOrderInfoWithBLOBs.getSid(), wp.getToken());
					haiOrderInfoWithBLOBs.setZipcode(mapSign.get("tableNo").toString());
				}
				HaiOrderGoodsExample gExp = new HaiOrderGoodsExample();
				gExp.createCriteria().andOrderIdIn(orderIds);
				List<HaiOrderGoods> listGoods = haiOrderGoodsMapper.selectByExampleWithBLOBs(gExp);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("listGoods", listGoods);
				rm.setMap(map);
				
				
			}
			rm.setCode(1);
			rm.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
	
	
}
