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
import org.ehais.shop.mapper.HaiBusinessMapper;
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessExample;
import org.ehais.shop.model.HaiBusinessWithBLOBs;
import org.ehais.shop.service.HaiBusinessService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "企业名称不能为空")//business_name

**/
/**

**/




@Service("haiBusinessService")
public class HaiBusinessServiceImpl  extends CommonServiceImpl implements HaiBusinessService{
	
	@Autowired
	private HaiBusinessMapper haiBusinessMapper;


	public ReturnObject<HaiBusiness> business_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiBusiness> rm = new ReturnObject<HaiBusiness>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiBusiness> business_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String businessName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusiness> rm = new ReturnObject<HaiBusiness>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(businessName))c.andBusinessNameLike("%"+businessName+"%");
		List<HaiBusiness> list = haiBusinessMapper.selectByExample(example);
		long total = haiBusinessMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiBusinessWithBLOBs> business_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessWithBLOBs model = new HaiBusinessWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiBusinessWithBLOBs> business_insert_submit(HttpServletRequest request,HaiBusinessWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		c.andBusinessNameEqualTo(model.getBusinessName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiBusinessMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiBusinessMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiBusinessWithBLOBs> business_update(HttpServletRequest request,Integer businessId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessIdEqualTo(businessId);
		long count = haiBusinessMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessWithBLOBs model = haiBusinessMapper.selectByPrimaryKey(businessId);
**/
		HaiBusinessWithBLOBs model = haiBusinessMapper.get_hai_business_info(businessId,store_id);
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
	
	public ReturnObject<HaiBusinessWithBLOBs> business_update_submit(HttpServletRequest request,HaiBusinessWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessIdEqualTo(model.getBusinessId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiBusinessMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiBusinessWithBLOBs bean = haiBusinessMapper.selectByPrimaryKey(model.getBusinessId());

bean.setBusinessCode(model.getBusinessCode());
bean.setUserId(model.getUserId());
bean.setBusinessName(model.getBusinessName());
bean.setSectorsId(model.getSectorsId());
bean.setRemark(model.getRemark());
bean.setLegalPerson(model.getLegalPerson());
bean.setLinkman(model.getLinkman());
bean.setTel(model.getTel());
bean.setFax(model.getFax());
bean.setEmail(model.getEmail());
bean.setAddress(model.getAddress());
bean.setLongitude(model.getLongitude());
bean.setLatitude(model.getLatitude());
bean.setIsVoid(model.getIsVoid());
bean.setIntro(model.getIntro());
bean.setLogo(model.getLogo());
bean.setWebsite(model.getWebsite());
bean.setClassify(model.getClassify());
bean.setPinyin(model.getPinyin());
bean.setCountry(model.getCountry());
bean.setProvince(model.getProvince());
bean.setCity(model.getCity());
bean.setCounty(model.getCounty());
bean.setDistrict(model.getDistrict());
bean.setStreet(model.getStreet());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiBusinessMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiBusinessWithBLOBs> business_info(HttpServletRequest request,Integer businessId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		c.andBusinessIdEqualTo(businessId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBusinessWithBLOBs> list = haiBusinessMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessWithBLOBs model = list.get(0);
		**/

		HaiBusinessWithBLOBs model = haiBusinessMapper.get_hai_business_info(businessId,store_id);
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


	public ReturnObject<HaiBusinessWithBLOBs> business_find(HttpServletRequest request,Integer businessId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusinessWithBLOBs> rm = new ReturnObject<HaiBusinessWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		c.andBusinessIdEqualTo(businessId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBusinessWithBLOBs> list = haiBusinessMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBusinessWithBLOBs model = list.get(0);
		**/

		HaiBusinessWithBLOBs model = haiBusinessMapper.get_hai_business_info(businessId,store_id);
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

	public ReturnObject<HaiBusiness> business_delete(HttpServletRequest request,Integer businessId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBusiness> rm = new ReturnObject<HaiBusiness>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBusinessExample example = new HaiBusinessExample();
		HaiBusinessExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBusinessIdEqualTo(businessId);

		long count = haiBusinessMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiBusinessMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiBusinessWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "business.xml",model,"hai_business",optionMap);
		
		
		return bootStrapList;
	}













	
}











