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
import org.ehais.shop.mapper.HaiBusinessTypeMapper;
import org.ehais.shop.model.HaiBusinessType;
import org.ehais.shop.model.HaiBusinessTypeExample;
import org.ehais.shop.service.HaiBusinessTypeService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "商业/往来单位简码不能为空")//business_type_code
@NotBlank(message = "商业/往来单位名称不能为空")//business_type_name

**/
/**

**/




@Service("haiBusinessTypeService")
public class HaiBusinessTypeServiceImpl  extends CommonServiceImpl implements HaiBusinessTypeService{
	
	@Autowired
	private HaiBusinessTypeMapper haiBusinessTypeMapper;


	public ReturnObject<HaiBusinessType> businesstype_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiBusinessType> businesstype_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String businessTypeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(businessTypeName))c.andBusinessTypeNameLike("%"+businessTypeName+"%");
		List<HaiBusinessType> list = haiBusinessTypeMapper.selectByExample(example);
		long total = haiBusinessTypeMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiBusinessType> businesstype_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessType model = new HaiBusinessType();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiBusinessType> businesstype_insert_submit(HttpServletRequest request,HaiBusinessType model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		c.andBusinessTypeNameEqualTo(model.getBusinessTypeName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiBusinessTypeMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiBusinessTypeMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiBusinessType> businesstype_update(HttpServletRequest request,Integer businessTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessTypeIdEqualTo(businessTypeId);
		long count = haiBusinessTypeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessType model = haiBusinessTypeMapper.selectByPrimaryKey(businessTypeId);
**/
		HaiBusinessType model = haiBusinessTypeMapper.get_hai_business_type_info(businessTypeId,store_id);
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
	
	public ReturnObject<HaiBusinessType> businesstype_update_submit(HttpServletRequest request,HaiBusinessType model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessTypeIdEqualTo(model.getBusinessTypeId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiBusinessTypeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiBusinessType bean = haiBusinessTypeMapper.selectByPrimaryKey(model.getBusinessTypeId());

bean.setBusinessTypeCode(model.getBusinessTypeCode());
bean.setBusinessTypeName(model.getBusinessTypeName());
bean.setBusinessTypeParentId(model.getBusinessTypeParentId());
bean.setRemark(model.getRemark());
bean.setClassify(model.getClassify());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiBusinessTypeMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiBusinessType> businesstype_info(HttpServletRequest request,Integer businessTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		c.andBusinessTypeIdEqualTo(businessTypeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBusinessType> list = haiBusinessTypeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessType model = list.get(0);
		**/

		HaiBusinessType model = haiBusinessTypeMapper.get_hai_business_type_info(businessTypeId,store_id);
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


	public ReturnObject<HaiBusinessType> businesstype_find(HttpServletRequest request,Integer businessTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		c.andBusinessTypeIdEqualTo(businessTypeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBusinessType> list = haiBusinessTypeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessType model = list.get(0);
		**/

		HaiBusinessType model = haiBusinessTypeMapper.get_hai_business_type_info(businessTypeId,store_id);
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

	public ReturnObject<HaiBusinessType> businesstype_delete(HttpServletRequest request,Integer businessTypeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessType> rm = new ReturnObject<HaiBusinessType>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessTypeExample example = new HaiBusinessTypeExample();
		HaiBusinessTypeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessTypeIdEqualTo(businessTypeId);

		long count = haiBusinessTypeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiBusinessTypeMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiBusinessType model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "businesstype.xml",model,"hai_businesstype",optionMap);
		
		
		return bootStrapList;
	}













	
}











