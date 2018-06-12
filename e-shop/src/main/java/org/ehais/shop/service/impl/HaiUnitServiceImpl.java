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
import org.ehais.shop.mapper.HaiUnitMapper;
import org.ehais.shop.model.HaiUnit;
import org.ehais.shop.model.HaiUnitExample;
import org.ehais.shop.service.HaiUnitService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//unit_code
@NotBlank(message = "名称不能为空")//unit_name
@NotBlank(message = "store_id不能为空")//store_id

**/
/**

**/




@Service("haiUnitService")
public class HaiUnitServiceImpl  extends CommonServiceImpl implements HaiUnitService{
	
	@Autowired
	private HaiUnitMapper haiUnitMapper;


	public ReturnObject<HaiUnit> unit_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiUnit> unit_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String unitName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(unitName))c.andUnitNameLike("%"+unitName+"%");
		List<HaiUnit> list = haiUnitMapper.selectByExample(example);
		long total = haiUnitMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiUnit> unit_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnit model = new HaiUnit();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiUnit> unit_insert_submit(HttpServletRequest request,HaiUnit model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		c.andUnitNameEqualTo(model.getUnitName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiUnitMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiUnitMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiUnit> unit_update(HttpServletRequest request,Integer unitId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andUnitIdEqualTo(unitId);
		long count = haiUnitMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiUnit model = haiUnitMapper.selectByPrimaryKey(unitId);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiUnit> unit_update_submit(HttpServletRequest request,HaiUnit model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andUnitIdEqualTo(model.getUnitId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiUnitMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiUnit bean = haiUnitMapper.selectByPrimaryKey(model.getUnitId());

bean.setUnitCode(model.getUnitCode());
bean.setUnitName(model.getUnitName());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiUnitMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiUnit> unit_info(HttpServletRequest request,Integer unitId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		c.andUnitIdEqualTo(unitId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiUnit> list = haiUnitMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiUnit model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiUnit> unit_find(HttpServletRequest request,Integer unitId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		c.andUnitIdEqualTo(unitId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiUnit> list = haiUnitMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiUnit model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiUnit> unit_delete(HttpServletRequest request,Integer unitId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUnit> rm = new ReturnObject<HaiUnit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUnitExample example = new HaiUnitExample();
		HaiUnitExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andUnitIdEqualTo(unitId);

		long count = haiUnitMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiUnitMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiUnit model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "unit.xml",model,"hai_unit",optionMap);
		
		
		return bootStrapList;
	}













	
}











