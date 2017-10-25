package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiOrderDistributionMapper;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiOrderDistribution;
import org.ehais.shop.model.HaiOrderDistributionExample;
import org.ehais.shop.model.HaiOrderUserGoodsDistribution;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class UserDistributionWSController extends CommonController{

	@Autowired
	private HaiOrderDistributionMapper haiOrderDistributionMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	private String defaultimg = ResourceUtil.getProValue("defaultimg");
	
	@ResponseBody
	@RequestMapping("/distribution_list")
	public String distribution_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition){
		ReturnObject<HaiOrderDistribution> rm = new ReturnObject<HaiOrderDistribution>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		List<HaiOrderUserGoodsDistribution> list = new ArrayList<HaiOrderUserGoodsDistribution>();
		try{
			HaiOrderDistributionExample exp = new HaiOrderDistributionExample();
			exp.createCriteria()
			.andDistributionUserIdEqualTo(user_id)
			.andCurrentMoneyIsNotNull()
			.andCurrentMoneyNotEqualTo(0);
			exp.setOrderByClause("add_time desc");
			exp.setLimitStart(condition.getStart());
			exp.setLimitEnd(condition.getRows());
			
			List<HaiOrderDistribution> distList = haiOrderDistributionMapper.selectByExample(exp);
			long total = haiOrderDistributionMapper.countByExample(exp);
			List<Long> userIds = new ArrayList<Long>();
			List<Long> goodsIds = new ArrayList<Long>();
			
			for (HaiOrderDistribution od : distList) {
				userIds.add(od.getUserId());
				goodsIds.add(od.getGoodsId());
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			if(userIds.size() > 0){
				EHaiUsersExample uxp = new EHaiUsersExample();
				uxp.createCriteria().andUserIdIn(userIds);
				List<EHaiUsers> userList = eHaiUsersMapper.selectByExample(uxp);
				map.put("userList", userList);
			}
			if(goodsIds.size() > 0){
				HaiGoodsExample gxp = new HaiGoodsExample();
				gxp.createCriteria().andGoodsIdIn(goodsIds);
				List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(gxp);
				map.put("goodsList", goodsList);
			}
			
			rm.setRows(distList);
			rm.setTotal(total);
			rm.setMap(map);
			rm.setAction(defaultimg);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
}
