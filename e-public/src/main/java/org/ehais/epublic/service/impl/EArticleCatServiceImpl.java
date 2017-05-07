package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.service.EArticleCatService;
import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("eArticlecatService")
public class EArticleCatServiceImpl  extends EArticleCommonServiceImpl implements EArticleCatService{
	
	
	
	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<EHaiArticleCat> list = eHaiArticleCatMapper.hai_article_cat_list_by_example(example);
		Integer total = eHaiArticleCatMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCat model = new EHaiArticleCat();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();

		if(model.getCatName() == null || model.getCatName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(model.getCatName());
		c.andStoreIdEqualTo(store_id);
		int count = eHaiArticleCatMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = eHaiArticleCatMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCat model = eHaiArticleCatMapper.selectByPrimaryKey(catId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		c.andStoreIdEqualTo(store_id);

		int count = eHaiArticleCatMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = eHaiArticleCatMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		EHaiArticleCat model = eHaiArticleCatMapper.selectByPrimaryKey(catId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		int code = eHaiArticleCatMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,EHaiArticleCat model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "catId", "", model.getCatId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "catName", "标题", model.getCatName(), "请输入分类标题", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "catType", "", model.getCatType(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "code", "编码", model.getCode(), "请输入分类编码", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select_tree", "parentId", "上级分类", model.getParentId(), "请选择上级分类", "", "", null,eTreeArticleCat(request), 0));
		bootStrapList.add(new BootStrapModel("text", "keywords", "关键字", model.getKeywords(), "请输入关键字", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "catDesc", "描述", model.getCatDesc(), "请输入描述", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "sortOrder", "排序", model.getSortOrder(), "请输入排序", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "images", "图片", model.getImages(), "请输入", "", "", null, 0));
		
		
		return bootStrapList;
	}
	
	

	public ReturnObject<TreeModel> articlecat_tree_json(
			HttpServletRequest request, Integer page, Integer len)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		
		example.setStart(start);
		example.setLen(len);
		List<EHaiArticleCat> list = eHaiArticleCatMapper.hai_article_cat_list_by_example(example);
		Integer total = eHaiArticleCatMapper.countByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecat_list", list);
		rm.setMap(map);
		
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		for (EHaiArticleCat haiArticleCat : list) {
			treeList.add(new TreeModel(haiArticleCat.getCatId().intValue(), haiArticleCat.getCatName(), haiArticleCat.getParentId().intValue(), 0,haiArticleCat.getCode(), null));
		}

		TreeUtil treeUtil = new TreeUtil();
		treeUtil.setTreeList(treeList);
		treeUtil.getTree(0);
		treeList = treeUtil.getTreeNewList();
		
		rm.setCode(1);
		rm.setRows(treeList);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecatcode(HttpServletRequest request,Integer store_id, String code) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCodeEqualTo(code);
		c.andStoreIdEqualTo(store_id);
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			rm.setCode(1);
			rm.setModel(list.get(0));
		}
		return rm;
	}
	
	
	
}











