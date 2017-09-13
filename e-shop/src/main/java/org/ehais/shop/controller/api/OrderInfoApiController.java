package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.epublic.cache.EAdminTokenCacheManager;
import org.ehais.shop.controller.api.include.OrderInfoIController;
import org.ehais.shop.mapper.HaiDiningPrintTimeMapper;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.model.HaiDiningPrintTime;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoExample;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	private HaiDiningPrintTimeMapper haiDiningPrintTimeMapper;
	
	
	@ResponseBody
	@RequestMapping("/orderinfo_list")
	public String orderinfo_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "m_code", required = true) Integer m_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(orderinfoService.orderinfo_list(request, user_id, page, len));
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
		if(!t.equals(token)){
			rm.setMsg("token wrong");
			return this.writeJson(rm);
		}
		try{
			HaiDiningPrintTime p = haiDiningPrintTimeMapper.selectByPrimaryKey(store_id);
			if(p == null){
				p = new HaiDiningPrintTime();
				p.setStoreId(store_id);
				p.setPrintTime(Long.valueOf(System.currentTimeMillis() / 1000 ).intValue());
				haiDiningPrintTimeMapper.insert(p);
			}
			HaiOrderInfoExample example = new HaiOrderInfoExample();
			HaiOrderInfoExample.Criteria c = example.createCriteria();
			c.andStoreIdEqualTo(store_id).andPayTimeGreaterThan(p.getPrintTime());
			example.setOrderByClause("pay_time asc");
			List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
			if(list != null && list.size() > 0){
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
	
}
