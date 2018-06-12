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
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.service.HaiCategoryService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//cat_code
@NotBlank(message = "名称不能为空")//cat_name

**/
/**

**/




@Service("haiCategoryService")
public class HaiCategoryServiceImpl  extends CommonServiceImpl implements HaiCategoryService{
	
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;


	public ReturnObject<HaiCategory> category_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String catName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(catName))c.andCatNameLike("%"+catName+"%");
		List<HaiCategory> list = haiCategoryMapper.selectByExample(example);
		long total = haiCategoryMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryWithBLOBs model = new HaiCategoryWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiCategoryWithBLOBs> category_insert_submit(HttpServletRequest request,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(model.getCatName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiCategoryMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiCategoryMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_update(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		long count = haiCategoryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCategoryWithBLOBs model = haiCategoryMapper.selectByPrimaryKey(catId);
**/
		HaiCategoryWithBLOBs model = haiCategoryMapper.get_hai_category_info(catId,store_id);
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
	
	public ReturnObject<HaiCategoryWithBLOBs> category_update_submit(HttpServletRequest request,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiCategoryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiCategoryWithBLOBs bean = haiCategoryMapper.selectByPrimaryKey(model.getCatId());

bean.setCatCode(model.getCatCode());
bean.setCatName(model.getCatName());
bean.setKeywords(model.getKeywords());
bean.setSubCatName(model.getSubCatName());
bean.setCatDesc(model.getCatDesc());
bean.setParentId(model.getParentId());
bean.setSortOrder(model.getSortOrder());
bean.setTemplateFile(model.getTemplateFile());
bean.setMeasureUnit(model.getMeasureUnit());
bean.setShowInNav(model.getShowInNav());
bean.setStyle(model.getStyle());
bean.setIsShow(model.getIsShow());
bean.setGrade(model.getGrade());
bean.setFilterAttr(model.getFilterAttr());
bean.setSubParentId(model.getSubParentId());
bean.setBrandId(model.getBrandId());
bean.setThumb(model.getThumb());
bean.setImage(model.getImage());
bean.setUserId(model.getUserId());
bean.setRecommend(model.getRecommend());
bean.setCategoryUrl(model.getCategoryUrl());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiCategoryMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_info(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCategoryWithBLOBs> list = haiCategoryMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCategoryWithBLOBs model = list.get(0);
		**/

		HaiCategoryWithBLOBs model = haiCategoryMapper.get_hai_category_info(catId,store_id);
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


	public ReturnObject<HaiCategoryWithBLOBs> category_find(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCategoryWithBLOBs> list = haiCategoryMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiCategoryWithBLOBs model = list.get(0);
		**/

		HaiCategoryWithBLOBs model = haiCategoryMapper.get_hai_category_info(catId,store_id);
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

	public ReturnObject<HaiCategory> category_delete(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);

		long count = haiCategoryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiCategoryMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "category.xml",model,"hai_category",optionMap);
		
		
		return bootStrapList;
	}













	
}











