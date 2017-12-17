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
import org.ehais.shop.mapper.HaiBrandMapper;
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.model.HaiBrandExample;
import org.ehais.shop.service.HaiBrandService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "品牌名称不能为空")//brand_name
@NotBlank(message = "LOGO不能为空")//brand_logo
@NotBlank(message = "品牌描述不能为空")//brand_desc
@NotBlank(message = "描述地址不能为空")//site_url
@NotBlank(message = "排序不能为空")//sort_order
@NotBlank(message = "是否显示不能为空")//is_show

**/
/**

**/




@Service("haiBrandService")
public class HaiBrandServiceImpl  extends CommonServiceImpl implements HaiBrandService{
	
	@Autowired
	private HaiBrandMapper haiBrandMapper;


	public ReturnObject<HaiBrand> brand_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiBrand> brand_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String brandName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("sort_order asc");
		
		if(StringUtils.isNotEmpty(brandName))c.andBrandNameLike("%"+brandName+"%");
		List<HaiBrand> list = haiBrandMapper.selectByExample(example);
		long total = haiBrandMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiBrand> brand_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrand model = new HaiBrand();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiBrand> brand_insert_submit(HttpServletRequest request,HaiBrand model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();

		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		c.andBrandNameEqualTo(model.getBrandName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiBrandMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiBrandMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiBrand> brand_update(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBrandIdEqualTo(brandId);
		List<HaiBrand> list = haiBrandMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBrand model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiBrand> brand_update_submit(HttpServletRequest request,HaiBrand model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBrandIdEqualTo(model.getBrandId());
		c.andStoreIdEqualTo(store_id);

		long count = haiBrandMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiBrand bean = haiBrandMapper.selectByPrimaryKey(model.getBrandId());

bean.setBrandName(model.getBrandName());
bean.setBrandLogo(model.getBrandLogo());
bean.setBrandDesc(model.getBrandDesc());
bean.setSiteUrl(model.getSiteUrl());
bean.setSortOrder(model.getSortOrder());
bean.setIsShow(model.getIsShow());
bean.setCatId(model.getCatId());
bean.setRecommend(model.getRecommend());


		Date date = new Date();

		int code = haiBrandMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiBrand> brand_info(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		c.andBrandIdEqualTo(brandId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBrand> list = haiBrandMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBrand model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiBrand> brand_find(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		c.andBrandIdEqualTo(brandId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBrand> list = haiBrandMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiBrand model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiBrand> brand_delete(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBrandIdEqualTo(brandId);

		long count = haiBrandMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiBrandMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiBrand model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "brand.xml",model,"hai_brand",optionMap);
		
		
		return bootStrapList;
	}













	
}











