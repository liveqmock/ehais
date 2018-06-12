package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiBrandMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.model.HaiBrandExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class EShopCommonServiceImpl extends CommonServiceImpl{

	@Autowired
	protected HaiCategoryMapper haiCategoryMapper;
	@Autowired
	protected HaiBrandMapper haiBrandMapper;
	
	
	protected List<TreeModel> categoryTreeList(HttpServletRequest request)throws Exception{
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCategory> categoryList = haiCategoryMapper.selectByExample(example);
		for (HaiCategory haiCategory : categoryList) {
			treeList.add(new TreeModel(haiCategory.getCatId().intValue(), haiCategory.getCatName(), haiCategory.getParentId().intValue(), 0,haiCategory.getCatCode(), null));
		}
		
		TreeUtil tUtil = new TreeUtil();
		tUtil.setTreeList(treeList);
		tUtil.getTree(0);
		treeList = tUtil.getTreeNewList();
		
		return treeList;
	}
	
	
	protected Map<String,String> brandList(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiBrand> brandList = haiBrandMapper.hai_brand_list_by_example(example);
		for (HaiBrand haiBrand : brandList) {
			map.put(haiBrand.getBrandId().toString(), haiBrand.getBrandName());
		}
		
		return map;
	}
	
	
	protected Map<String,String> is_hot(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("", "1");
		return map;
	}
	
	
	
}
