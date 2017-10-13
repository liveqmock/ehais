package org.ehais.shop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.mapper.HaiUserSignRecordMapper;
import org.ehais.shop.model.HaiUserSignRecord;
import org.ehais.shop.model.HaiUserSignRecordExample;
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
	@Autowired
	private HaiUserSignRecordMapper haiUserSignRecordMapper;
	
	@Override
	public ReturnObject<EHaiUsers> member(
			HttpServletRequest request, Long user_id,String token) {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		if(user_id == null) user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		EHaiUsers model = eHaiUsersMapper.selectByPrimaryKey(user_id);
		model.setPassword(null);
		rm.setModel(model);
		
		Map<String, Object> map = new HashMap<String, Object>();
//		List<OrderStatus> list = haiOrderInfoMapper.statistics_member_order(user_id);
//		map.put("order_status", list);
		//今天是否签到了
		int isTSign = haiUserSignRecordMapper.isTodaySign(user_id);
		map.put("isTodaySign", isTSign);
		
		//签到天数
		HaiUserSignRecordExample usrexamp = new HaiUserSignRecordExample();
		usrexamp.createCriteria().andUserIdEqualTo(user_id);
		Long signCount = haiUserSignRecordMapper.countByExample(usrexamp);
		map.put("signCount", signCount);
		
		Map<String,Integer> mOrder = haiOrderInfoMapper.order_statistics(user_id);
		map.put("mOrder", mOrder);
		
		
		rm.setMap(map);
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<EHaiUsers> sign(HttpServletRequest request, Long user_id, String token) {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(user_id==null || user_id == 0)user_id = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			int sc = haiUserSignRecordMapper.isTodaySign(user_id);
			if(sc > 0 ){
				rm.setMsg("今天已签到了");
				return rm;
			}
			
			HaiUserSignRecordExample usrexamp = new HaiUserSignRecordExample();
			usrexamp.createCriteria().andUserIdEqualTo(user_id);
			Long cc = haiUserSignRecordMapper.countByExample(usrexamp);
			
			HaiUserSignRecord signRecord = new HaiUserSignRecord();
			signRecord.setUserId(user_id);
			signRecord.setSignTime(new Date());
			signRecord.setSignCount(cc.intValue()+1);
			haiUserSignRecordMapper.insertSelective(signRecord);
			
			rm.setCode(1);
			rm.setMsg("签到成功");
			return rm;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rm;
	}
	
}
