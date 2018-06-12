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
import org.ehais.shop.mapper.HaiPropertyMapper;
import org.ehais.shop.model.HaiProperty;
import org.ehais.shop.model.HaiPropertyExample;
import org.ehais.shop.service.HaiPropertyService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//property_code
@NotBlank(message = "名称不能为空")//property_name

**/
/**

**/




@Service("haiPropertyService")
public class HaiPropertyServiceImpl  extends CommonServiceImpl implements HaiPropertyService{
	
	@Autowired
	private HaiPropertyMapper haiPropertyMapper;


	public ReturnObject<HaiProperty> property_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiProperty> property_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String propertyName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(propertyName))c.andPropertyNameLike("%"+propertyName+"%");
		List<HaiProperty> list = haiPropertyMapper.selectByExample(example);
		long total = haiPropertyMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiProperty> property_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiProperty model = new HaiProperty();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiProperty> property_insert_submit(HttpServletRequest request,HaiProperty model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		c.andPropertyNameEqualTo(model.getPropertyName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiPropertyMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiPropertyMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiProperty> property_update(HttpServletRequest request,Integer propertyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPropertyIdEqualTo(propertyId);
		long count = haiPropertyMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiProperty model = haiPropertyMapper.selectByPrimaryKey(propertyId);
**/
		HaiProperty model = haiPropertyMapper.get_hai_property_info(propertyId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiProperty> property_update_submit(HttpServletRequest request,HaiProperty model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPropertyIdEqualTo(model.getPropertyId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiPropertyMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiProperty bean = haiPropertyMapper.selectByPrimaryKey(model.getPropertyId());

bean.setPropertyCode(model.getPropertyCode());
bean.setPropertyName(model.getPropertyName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiPropertyMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiProperty> property_info(HttpServletRequest request,Integer propertyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		c.andPropertyIdEqualTo(propertyId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiProperty> list = haiPropertyMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiProperty model = list.get(0);
		**/

		HaiProperty model = haiPropertyMapper.get_hai_property_info(propertyId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiProperty> property_find(HttpServletRequest request,Integer propertyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		c.andPropertyIdEqualTo(propertyId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiProperty> list = haiPropertyMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiProperty model = list.get(0);
		**/

		HaiProperty model = haiPropertyMapper.get_hai_property_info(propertyId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiProperty> property_delete(HttpServletRequest request,Integer propertyId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiProperty> rm = new ReturnObject<HaiProperty>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPropertyExample example = new HaiPropertyExample();
		HaiPropertyExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPropertyIdEqualTo(propertyId);

		long count = haiPropertyMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiPropertyMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiProperty model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "property.xml",model,"hai_property",optionMap);
		
		
		return bootStrapList;
	}













	
}











