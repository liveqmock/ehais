package org.ehais.shop.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.project.HaiBegOffMapper;
import org.ehais.shop.model.project.HaiBegOff;
import org.ehais.shop.model.project.HaiBegOffExample;
import org.ehais.shop.model.project.HaiBegOffUser;
import org.ehais.shop.service.ProjectBegOffService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("HaiBegOffService")
public class ProjectBegOffServiceImpl  extends CommonServiceImpl implements ProjectBegOffService{
	
	@Autowired
	private HaiBegOffMapper HaiBegOffMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;

	public ReturnObject<HaiBegOff> begoff_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiBegOff> begoff_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String begOffName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		List<Long> usersList = new ArrayList<Long>();
		if(StringUtils.isNotBlank(begOffName)) {
			EHaiUsersExample userExp = new EHaiUsersExample();
			userExp.createCriteria().andQuestionEqualTo(begOffName).andStoreIdEqualTo(condition.getStore_id());
			List<EHaiUsers> uList = eHaiUsersMapper.selectByExample(userExp);
			for (EHaiUsers eHaiUsers : uList) {
				usersList.add(eHaiUsers.getUserId());
			}
		}
		
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		
		if(usersList.size() > 0)c.andUserIdIn(usersList);
		
		if(condition.getStartDate()!=null && condition.getEndDate() != null)
			c.andCreateDateBetween(condition.getStartDate(), DateUtil.addDate(condition.getEndDate(), 1));
		
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("create_date desc");
		
		List<HaiBegOff> list = HaiBegOffMapper.selectByExample(example);
		long total = HaiBegOffMapper.countByExample(example);

		List<Long> userIds = new ArrayList<Long>();
		for (HaiBegOff haiBegOff : list) {
			userIds.add(haiBegOff.getUserId());
			if(haiBegOff.getTeacherUserId() != null && haiBegOff.getTeacherUserId() > 0)userIds.add(haiBegOff.getTeacherUserId());
			if(haiBegOff.getDepartmentUserId() != null && haiBegOff.getDepartmentUserId() > 0)userIds.add(haiBegOff.getDepartmentUserId());
			if(haiBegOff.getLeaderUserId() != null && haiBegOff.getLeaderUserId() > 0)userIds.add(haiBegOff.getLeaderUserId());
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(userIds.size() > 0){
			List<EHaiUsers> user_list =  eHaiUsersMapper.inUserIdList(StringUtils.join(userIds.toArray(), ","));
			Map<Long,String> userMap = new HashMap<Long,String>();
			for (EHaiUsers eHaiUsers : user_list) {
				userMap.put(eHaiUsers.getUserId(), eHaiUsers.getRealname());
			}
			map.put("userMap", userMap);
		}


		
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	
	public ReturnObject<HaiBegOffUser> begoff_list_json(HttpServletRequest request,Integer store_id,
			String start_date, 
			String end_date) throws Exception{
		ReturnObject<HaiBegOffUser> rm = new ReturnObject<HaiBegOffUser>();
		rm.setCode(0);
		
		Date startDate = DateUtil.formatDate(start_date, DateUtil.FORMATSTR_3);
		Date endDate = DateUtil.formatDate(end_date, DateUtil.FORMATSTR_3);
		
		
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCreateDateGreaterThanOrEqualTo(startDate).andCreateDateLessThanOrEqualTo(endDate);
		
		example.setOrderByClause("create_date asc");
		
		List<HaiBegOffUser> boList = new ArrayList<HaiBegOffUser>();
		List<HaiBegOff> list = HaiBegOffMapper.selectByExample(example);

		List<Long> userIds = new ArrayList<Long>();
		for (HaiBegOff haiBegOff : list) {
			HaiBegOffUser b = new HaiBegOffUser();
			userIds.add(haiBegOff.getUserId());
			if(haiBegOff.getTeacherUserId() != null && haiBegOff.getTeacherUserId() > 0) {
				userIds.add(haiBegOff.getTeacherUserId());
				b.setTeacherUserId(haiBegOff.getTeacherUserId());
			}
			if(haiBegOff.getDepartmentUserId() != null && haiBegOff.getDepartmentUserId() > 0) {
				userIds.add(haiBegOff.getDepartmentUserId());
				b.setDepartmentUserId(haiBegOff.getDepartmentUserId());
			}
			if(haiBegOff.getLeaderUserId() != null && haiBegOff.getLeaderUserId() > 0) {
				userIds.add(haiBegOff.getLeaderUserId());
				b.setLeaderUserId(haiBegOff.getLeaderUserId());
			}
			
			b.setUserId(haiBegOff.getUserId());
			b.setBegoffId(haiBegOff.getBegoffId());
			b.setNumber(haiBegOff.getNumber());
			b.setReason(haiBegOff.getReason());
			b.setBegOffDate(DateUtil.formatDate(haiBegOff.getCreateDate(), DateUtil.FORMATSTR_3));
			
			boList.add(b);
		}
		
		
		
		if(userIds.size() > 0){
			List<EHaiUsers> user_list =  eHaiUsersMapper.inUserIdList(StringUtils.join(userIds.toArray(), ","));
			for (HaiBegOffUser b : boList) {
				for (EHaiUsers u : user_list) {
					if(b.getUserId().longValue() == u.getUserId().longValue()) {
						b.setRealname(u.getRealname());
						b.setClassName(u.getQuestion());
						b.setUsername(u.getUserName());
					}
					if(b.getTeacherUserId() != null && b.getTeacherUserId().longValue() == u.getUserId().longValue()) {
						b.setTeacherName(u.getRealname());
					}
					if(b.getDepartmentUserId() != null && b.getDepartmentUserId().longValue() == u.getUserId().longValue()) {
						b.setDepartmentName(u.getRealname());
					}
					if(b.getLeaderUserId() != null && b.getLeaderUserId().longValue() == u.getUserId().longValue()) {
						b.setLeaderName(u.getRealname());
					}
				}
			}
		}


		rm.setCode(1);
		rm.setRows(boList);
		
		
		return rm;
	}

	public ReturnObject<HaiBegOff> begoff_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOff model = new HaiBegOff();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiBegOff> begoff_insert_submit(HttpServletRequest request,HaiBegOff model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);

		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = HaiBegOffMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = HaiBegOffMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiBegOff> begoff_update(HttpServletRequest request,Integer begOffId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBegoffIdEqualTo(begOffId);
		List<HaiBegOff> list = HaiBegOffMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBegOff model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiBegOff> begoff_update_submit(HttpServletRequest request,HaiBegOff model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBegoffIdEqualTo(model.getBegoffId());
		c.andStoreIdEqualTo(store_id);

		long count = HaiBegOffMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiBegOff bean = HaiBegOffMapper.selectByPrimaryKey(model.getBegoffId());

bean.setUserId(model.getUserId());
bean.setNumber(model.getNumber());
bean.setReason(model.getReason());
bean.setCreateDate(model.getCreateDate());
bean.setTeacherUserId(model.getTeacherUserId());
bean.setTeacherApprove(model.getTeacherApprove());
bean.setTeacherApproveTime(model.getTeacherApproveTime());
bean.setDepartmentUserId(model.getDepartmentUserId());
bean.setDepartmentApprove(model.getDepartmentApprove());
bean.setDepartmentApproveTime(model.getDepartmentApproveTime());
bean.setLeaderUserId(model.getLeaderUserId());
bean.setLeaderApprove(model.getLeaderApprove());
bean.setLeaderApproveTime(model.getLeaderApproveTime());


		int code = HaiBegOffMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiBegOff> begoff_info(HttpServletRequest request,Integer begOffId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		c.andBegoffIdEqualTo(begOffId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBegOff> list = HaiBegOffMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBegOff model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiBegOff> begoff_find(HttpServletRequest request,Integer begOffId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		c.andBegoffIdEqualTo(begOffId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBegOff> list = HaiBegOffMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBegOff model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiBegOff> begoff_delete(HttpServletRequest request,Integer begOffId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBegOffExample example = new HaiBegOffExample();
		HaiBegOffExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBegoffIdEqualTo(begOffId);

		long count = HaiBegOffMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = HaiBegOffMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	





	
}











