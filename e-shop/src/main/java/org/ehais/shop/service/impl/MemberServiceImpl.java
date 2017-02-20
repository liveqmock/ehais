package org.ehais.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.model.OrderStatus;
import org.ehais.shop.service.MemberService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl extends CommonServiceImpl implements MemberService{

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	
	@Override
	public ReturnObject<EHaiUsers> member(
			HttpServletRequest request, Long user_id,String token) {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		if(user_id == null) user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		EHaiUsers model = eHaiUsersMapper.selectByPrimaryKey(user_id);
		model.setPassword(null);
		rm.setModel(model);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderStatus> list = haiOrderInfoMapper.statistics_member_order(user_id);
		map.put("order_status", list);
		
		rm.setMap(map);
		rm.setCode(1);
		return rm;
	}
	
}
