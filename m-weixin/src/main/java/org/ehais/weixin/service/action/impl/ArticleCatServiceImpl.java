package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.CriteriaObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.TreeUtil;
import org.ehais.weixin.mapper.HaiArticleCatMapper;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.model.HaiArticleCatExample;
import org.ehais.weixin.service.action.ArticleCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("articlecatService")
public class ArticleCatServiceImpl  extends ArticleCommonServiceImpl implements ArticleCatService{
	
	
	
	public ReturnObject<HaiArticleCat> articlecat_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiArticleCat> articlecat_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
//		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		Integer start = (page - 1 ) * len;
		
		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		
		CriteriaObject co = this.storeIdCriteriaObject(request);
		example.CriteriaStoreId(c, co);
		
		example.setStart(start);
		example.setLen(len);
		List<HaiArticleCat> list = haiArticleCatMapper.hai_article_cat_list_by_example(example);
		Integer total = haiArticleCatMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiArticleCat> articlecat_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiArticleCat model = new HaiArticleCat();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiArticleCat> articlecat_insert_submit(HttpServletRequest request,HaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		
		CriteriaObject co = this.storeIdCriteriaObject(request);
		if(co.getRoleType().equals("user")){
			model.setUserId(co.getUserId().intValue());
		}else{
			model.setStoreId(co.getStoreId());
		}
		
		
		int code = haiArticleCatMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiArticleCat> articlecat_update(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiArticleCat model = haiArticleCatMapper.selectByPrimaryKey(Short.valueOf(catId+""));
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiArticleCat> articlecat_update_submit(HttpServletRequest request,HaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();


		CriteriaObject co = this.storeIdCriteriaObject(request);
		if(co.getRoleType().equals("user")){
			model.setUserId(co.getUserId().intValue());
		}else{
			model.setStoreId(co.getStoreId());
		}
		
		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		if(model.getParentId() == null)model.setParentId(Short.valueOf("0"));
		int code = haiArticleCatMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiArticleCat> articlecat_find(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		HaiArticleCat model = haiArticleCatMapper.selectByPrimaryKey(Short.valueOf(catId+""));
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiArticleCat> articlecat_delete(HttpServletRequest request,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		int code = haiArticleCatMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request , HaiArticleCat model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		TreeUtil tree = treeArticleCat(request);
		List<TreeModel> treeCat = tree.getTreeNewList();
		
		bootStrapList.add(new BootStrapModel("hidden", "catId", "", model.getCatId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "catName", "标题", model.getCatName(), "请输入分类标题", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "catType", "", model.getCatType(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "code", "编码", model.getCode(), "请输入分类编码", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select_tree", "parentId", "上级分类", model.getParentId(), "请选择上级分类", "", "", null,treeCat, 0));
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
//		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		Integer start = (page - 1 ) * len;
		
		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		CriteriaObject co = this.storeIdCriteriaObject(request);
		example.CriteriaStoreId(c, co);
		
		example.setStart(start);
		example.setLen(len);
		List<HaiArticleCat> list = haiArticleCatMapper.hai_article_cat_list_by_example(example);
		Integer total = haiArticleCatMapper.countByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecat_list", list);
		rm.setMap(map);
		
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		for (HaiArticleCat haiArticleCat : list) {
			treeList.add(new TreeModel(haiArticleCat.getCatId().intValue(), haiArticleCat.getCatName(), haiArticleCat.getParentId().intValue(), 0 , haiArticleCat.getCode() , null));
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

	public ReturnObject<HaiArticleCat> articlecatcode(HttpServletRequest request,Integer store_id, String code) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticleCat> rm = new ReturnObject<HaiArticleCat>();
		rm.setCode(0);
		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCodeEqualTo(code);
		c.andStoreIdEqualTo(store_id);
		List<HaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			rm.setCode(1);
			rm.setModel(list.get(0));
		}
		return rm;
	}
	
}











