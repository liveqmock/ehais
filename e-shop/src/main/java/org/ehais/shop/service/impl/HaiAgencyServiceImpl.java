package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiAgencyMapper;
import org.ehais.shop.model.HaiAgency;
import org.ehais.shop.model.HaiAgencyExample;
import org.ehais.shop.service.HaiAgencyService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiAgencyService")
public class HaiAgencyServiceImpl  extends CommonServiceImpl implements HaiAgencyService{
	
	@Autowired
	private HaiAgencyMapper haiAgencyMapper;
	
	public ReturnObject<HaiAgency> agency_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAgency> agency_list_json(HttpServletRequest request,EConditionObject condition,String agencyName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("agency_level asc");
		if(StringUtils.isNotEmpty(agencyName))c.andAgencyNameLike("%"+agencyName+"%");
		List<HaiAgency> list = haiAgencyMapper.selectByExampleWithBLOBs(example);
		long total = haiAgencyMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiAgency> agency_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgency model = new HaiAgency();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiAgency> agency_insert_submit(HttpServletRequest request,HaiAgency model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		c.andAgencyNameEqualTo(model.getAgencyName());
		c.andStoreIdEqualTo(store_id);
		long count = haiAgencyMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAgencyMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAgency> agency_update(HttpServletRequest request,Integer agencyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		c.andAgencyIdEqualTo(agencyId);
		c.andStoreIdEqualTo(store_id);
		List<HaiAgency> list = haiAgencyMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAgency model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiAgency> agency_update_submit(HttpServletRequest request,HaiAgency model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAgencyIdEqualTo(model.getAgencyId());
		c.andStoreIdEqualTo(store_id);

		long count = haiAgencyMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiAgency bean = haiAgencyMapper.selectByPrimaryKey(model.getAgencyId());

bean.setAgencyName(model.getAgencyName());
bean.setAgencyDesc(model.getAgencyDesc());
bean.setAgencyLevel(model.getAgencyLevel());
bean.setStoreId(model.getStoreId());


		int code = haiAgencyMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAgency> agency_info(HttpServletRequest request,Integer agencyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		c.andAgencyIdEqualTo(agencyId);
		c.andStoreIdEqualTo(store_id);
		List<HaiAgency> list = haiAgencyMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAgency model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiAgency> agency_find(HttpServletRequest request,Integer agencyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiAgency model = haiAgencyMapper.selectByPrimaryKey(agencyId);
		
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiAgency> agency_delete(HttpServletRequest request,Integer agencyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAgency> rm = new ReturnObject<HaiAgency>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgencyExample example = new HaiAgencyExample();
		HaiAgencyExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAgencyIdEqualTo(agencyId);

		long count = haiAgencyMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiAgencyMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiAgency model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "agency.xml",model,"hai_agency",optionMap);
		
		
		return bootStrapList;
	}
	
}











