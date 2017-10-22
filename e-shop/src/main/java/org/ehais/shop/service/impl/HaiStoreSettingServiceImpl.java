package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiStoreSettingMapper;
import org.ehais.shop.model.HaiStoreSetting;
import org.ehais.shop.model.HaiStoreSettingExample;
import org.ehais.shop.service.HaiStoreSettingService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiStoreSettingService")
public class HaiStoreSettingServiceImpl  extends CommonServiceImpl implements HaiStoreSettingService{
	
	@Autowired
	private HaiStoreSettingMapper haiStoreSettingMapper;


	public ReturnObject<HaiStoreSetting> storesetting_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiStoreSetting> storesetting_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String storeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		
		List<HaiStoreSetting> list = haiStoreSettingMapper.selectByExample(example);
		long total = haiStoreSettingMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiStoreSetting> storesetting_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiStoreSetting model = new HaiStoreSetting();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiStoreSetting> storesetting_insert_submit(HttpServletRequest request,HaiStoreSetting model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();

		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiStoreSettingMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiStoreSettingMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiStoreSetting> storesetting_update(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andStoreIdEqualTo(storeId);
		List<HaiStoreSetting> list = haiStoreSettingMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiStoreSetting model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiStoreSetting> storesetting_update_submit(HttpServletRequest request,HaiStoreSetting model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andStoreIdEqualTo(model.getStoreId());
		c.andStoreIdEqualTo(store_id);

		long count = haiStoreSettingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiStoreSetting bean = haiStoreSettingMapper.selectByPrimaryKey(model.getStoreId());

bean.setDistributionType(model.getDistributionType());
bean.setDistributionPercentage(model.getDistributionPercentage());
bean.setFirstPercentage(model.getFirstPercentage());
bean.setSecondPercentage(model.getSecondPercentage());
bean.setThirdPercentage(model.getThirdPercentage());
bean.setMoneyToIntegral(model.getMoneyToIntegral());


		Date date = new Date();

		int code = haiStoreSettingMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiStoreSetting> storesetting_info(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(storeId == null )storeId = store_id;
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiStoreSetting> list = haiStoreSettingMapper.selectByExample(example);
		HaiStoreSetting model = null;
		if(list == null || list.size() == 0){
			model = new HaiStoreSetting();
			model.setStoreId(store_id);
			model.setDistributionType(0);
			model.setDistributionPercentage(0);
			model.setFirstPercentage(0);
			model.setSecondPercentage(0);
			model.setThirdPercentage(0);
			model.setMoneyToIntegral(0);
			
			haiStoreSettingMapper.insertStoreSetting(model);
		}else{
			model = list.get(0);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiStoreSetting> storesetting_find(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiStoreSetting> list = haiStoreSettingMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiStoreSetting model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiStoreSetting> storesetting_delete(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiStoreSetting> rm = new ReturnObject<HaiStoreSetting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiStoreSettingExample example = new HaiStoreSettingExample();
		HaiStoreSettingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andStoreIdEqualTo(storeId);

		long count = haiStoreSettingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiStoreSettingMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiStoreSetting model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "storesetting.xml",model,"hai_storesetting",optionMap);
		
		
		return bootStrapList;
	}













	
}











