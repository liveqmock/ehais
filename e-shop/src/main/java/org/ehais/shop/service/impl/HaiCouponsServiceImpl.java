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
import org.ehais.shop.mapper.HaiCouponsMapper;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.model.HaiCouponsExample;
import org.ehais.shop.service.HaiCouponsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "优惠券名称不能为空")//coupons_name

**/
/**

**/




@Service("haiCouponsService")
public class HaiCouponsServiceImpl  extends CommonServiceImpl implements HaiCouponsService{
	
	@Autowired
	private HaiCouponsMapper haiCouponsMapper;


	public ReturnObject<HaiCoupons> coupons_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCoupons> coupons_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String couponsName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		
		if(StringUtils.isNotEmpty(couponsName))c.andCouponsNameLike("%"+couponsName+"%");
		List<HaiCoupons> list = haiCouponsMapper.selectByExample(example);
		long total = haiCouponsMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiCoupons> coupons_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCoupons model = new HaiCoupons();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiCoupons> coupons_insert_submit(HttpServletRequest request,HaiCoupons model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);

		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		c.andCouponsNameEqualTo(model.getCouponsName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiCouponsMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiCouponsMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiCoupons> coupons_update(HttpServletRequest request,Integer couponsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCouponsIdEqualTo(couponsId);
		List<HaiCoupons> list = haiCouponsMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCoupons model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiCoupons> coupons_update_submit(HttpServletRequest request,HaiCoupons model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCouponsIdEqualTo(model.getCouponsId());
		c.andStoreIdEqualTo(store_id);

		long count = haiCouponsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiCoupons bean = haiCouponsMapper.selectByPrimaryKey(model.getCouponsId());

bean.setCouponsName(model.getCouponsName());
bean.setQuota(model.getQuota());
bean.setDiscounts(model.getDiscounts());
bean.setCouponsType(model.getCouponsType());
bean.setScope(model.getScope());
bean.setStartDate(model.getStartDate());
bean.setEndDate(model.getEndDate());
bean.setIsValid(model.getIsValid());
bean.setStoreId(model.getStoreId());
bean.setReceiveCount(model.getReceiveCount());
bean.setUseCount(model.getUseCount());


		Date date = new Date();
		model.setUpdateDate(date);

		int code = haiCouponsMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiCoupons> coupons_info(HttpServletRequest request,Integer couponsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		c.andCouponsIdEqualTo(couponsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCoupons> list = haiCouponsMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCoupons model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiCoupons> coupons_find(HttpServletRequest request,Integer couponsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		c.andCouponsIdEqualTo(couponsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCoupons> list = haiCouponsMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCoupons model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiCoupons> coupons_delete(HttpServletRequest request,Integer couponsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCouponsExample example = new HaiCouponsExample();
		HaiCouponsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCouponsIdEqualTo(couponsId);

		long count = haiCouponsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiCouponsMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiCoupons model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "coupons.xml",model,"hai_coupons",optionMap);
		
		
		return bootStrapList;
	}













	
}











