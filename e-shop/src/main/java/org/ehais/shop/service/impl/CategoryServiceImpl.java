package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.shop.mapper.HaiBrandMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.service.CategoryService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("categoryService")
public class CategoryServiceImpl  extends EShopCommonServiceImpl implements CategoryService{
	
	@Autowired
	private HaiBrandMapper haiBrandMapper;
	
	public ReturnObject<HaiCategory> category_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<HaiCategory> list = haiCategoryMapper.hai_category_list_by_example(example);
		Integer total = haiCategoryMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(condition.getStart());
		example.setLen(condition.getRows());
		List<HaiCategory> list = haiCategoryMapper.selectByExample(example);
		long total = haiCategoryMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryWithBLOBs model = new HaiCategoryWithBLOBs();
//		rm.setBootStrapList(this.formatBootStrapList(request,model));
	
		
		Map<String,Object> option = new HashMap<String,Object>();
		option.put("brand", brandList(request));
		option.put("category", categoryTreeList(request));
		
		rm.setBootStrapList(this.BootStrapXml(request, "category.xml", model, "hai_category", option));
		
		
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiCategoryWithBLOBs> category_insert_submit(HttpServletRequest request,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		model.setStyle("");
		model.setFilterAttr("");
		int code = haiCategoryMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_update(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryWithBLOBs model = haiCategoryMapper.selectByPrimaryKey(catId);
//		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		Map<String,Object> option = new HashMap<String,Object>();
		option.put("brand", brandList(request));
		option.put("category", categoryTreeList(request));
		
		rm.setBootStrapList(this.BootStrapXml(request, "category.xml", model, "hai_category", option));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiCategoryWithBLOBs> category_update_submit(HttpServletRequest request,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		int code = haiCategoryMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> category_find(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiCategoryWithBLOBs model = haiCategoryMapper.selectByPrimaryKey(catId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiCategory> category_delete(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		int code = haiCategoryMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception{
		
		Map<String,Object> optionMap = new HashMap<String, Object>();
		optionMap.put("categoryTree", this.categoryTreeList(request));
		optionMap.put("brand", this.brandList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "category.xml",model,"hai_category",optionMap);
		
		
		return bootStrapList;
		
//		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
//		
//		bootStrapList.add(new BootStrapModel("hidden", "catId", "", model.getCatId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "catName", "名称", model.getCatName(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("text", "catCode", "", model.getCatCode(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "keywords", "关键字", model.getKeywords(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "catDesc", "描述", model.getCatDesc(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_tree", "parentId", "上级类别", model.getParentId(), "请输入", "", "", null, this.categoryTreeList(request), 0));
//		bootStrapList.add(new BootStrapModel("text", "sortOrder", "排序", model.getSortOrder(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("text", "templateFile", "", model.getTemplateFile(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("text", "measureUnit", "", model.getMeasureUnit(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("checkbox", "showIn_nav", "", model.getShowIn_nav(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("hidden", "style", "", "", "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("checkbox", "isShow", "", model.getIsShow(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("checkbox", "grade", "", model.getGrade(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("hidden", "filterAttr", "", "", "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("textarea", "subParent_id", "", model.getSubParent_id(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("textarea", "brandId", "", model.getBrandId(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("checkbox", "storeId", "", model.getStoreId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("images", "thumb", "", model.getThumb(), "请输入", "", "", null, 0));
////		bootStrapList.add(new BootStrapModel("text", "image", ""s, model.getImage(), "请输入", "", "", null, 0));
//		
//		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiCategory> category_list(HttpServletRequest request,
			Integer store_id,Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andParentIdEqualTo(parent_id);
		List<HaiCategory> list = haiCategoryMapper.hai_category_list_by_example(example);
		
		rm.setCode(1);
		rm.setRows(list);
		
		
		return rm;
	}

	@Override
	public ReturnObject<HaiCategoryWithBLOBs> category_info(HttpServletRequest request,
			Integer store_id, Integer catId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		
		HaiCategoryWithBLOBs model = haiCategoryMapper.get_hai_category_info(store_id, catId);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	
	/* 用于网页请求返回数据
	 * @see org.ehais.shop.service.CategoryService#list(javax.servlet.http.HttpServletRequest)
	 */
	public List<HaiCategory> list(HttpServletRequest request) throws Exception{
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		c.andIsShowEqualTo(true);
		List<HaiCategory> list = haiCategoryMapper.hai_category_list_by_example(example);
		
		return list;
	}

	@Override
	public List<TreeModel> categoryTreeList2(HttpServletRequest request)throws Exception{
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiCategory> categoryList = haiCategoryMapper.hai_category_list_by_example(example);
		for (HaiCategory haiCategory : categoryList) {
			treeList.add(new TreeModel(haiCategory.getCatId().intValue(), haiCategory.getCatName(), haiCategory.getParentId().intValue(), 0,haiCategory.getCatCode(), null));
		}
		
		TreeUtil tUtil = new TreeUtil();
		tUtil.setTreeList(treeList);
		tUtil.getTree(0);
		treeList = tUtil.getTreeNewList();
		
		return treeList;
	}
	
}











