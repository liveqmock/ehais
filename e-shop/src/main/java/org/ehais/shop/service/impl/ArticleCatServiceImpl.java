package org.ehais.shop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleCatService")
public class ArticleCatServiceImpl extends CommonServiceImpl implements ArticleCatService{

	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	protected EHaiArticleMapper eHaiArticleMapper;
	
	
	public ReturnObject<EHaiArticleCat> article_cat_parent_list(String moduleEnum,Integer store_id, Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		example.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andParentIdEqualTo(parent_id)
		.andModuleEqualTo(moduleEnum);
		
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	@Override
	public List<EHaiArticleCat> articleCatList(String moduleEnum,Integer store_id, Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		if(parent_id != 0)c.andParentIdEqualTo(parent_id);
		
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		return list;
	}
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,String moduleEnum,EHaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();

		if(model.getTitle() == null || model.getTitle().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		model.setStoreId(store_id);
		model.setCreateDate(new Date());
		model.setModule(moduleEnum);

		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		int count = Long.valueOf(eHaiArticleMapper.countByExample(example)).intValue();
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}

		Bean2Utils.printEntity(model);
		

		int code = eHaiArticleMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}
	
	
	
	
	
	
	
	
	
	
	
	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request,String moduleEnum) throws Exception{
		
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,String moduleEnum,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andModuleEqualTo(moduleEnum);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		long total = eHaiArticleCatMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request,String moduleEnum)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCat model = new EHaiArticleCat();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,String moduleEnum,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		model.setModule(moduleEnum);
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(model.getCatName());
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		long count = eHaiArticleCatMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}

		model.setIsValid(true);

		int code = eHaiArticleCatMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,String moduleEnum,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticleCat model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,String moduleEnum,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);

		long count = eHaiArticleCatMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiArticleCat bean = eHaiArticleCatMapper.selectByPrimaryKey(model.getCatId());

		bean.setCatId(model.getCatId());
bean.setCatName(model.getCatName());
bean.setCatType(model.getCatType());
//bean.setModule(model.getModule());
bean.setKeywords(model.getKeywords());
bean.setCatDesc(model.getCatDesc());
bean.setSortOrder(model.getSortOrder());
bean.setShowInNav(model.getShowInNav());
bean.setParentId(model.getParentId());
bean.setStoreId(model.getStoreId());
bean.setCode(model.getCode());
bean.setUserId(model.getUserId());
bean.setImages(model.getImages());
bean.setIsValid(true);

		int code = eHaiArticleCatMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String moduleEnum,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticleCat model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,String moduleEnum,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		EHaiArticleCat model = eHaiArticleCatMapper.selectByPrimaryKey(catId);
		
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,String moduleEnum,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		c.andModuleEqualTo(moduleEnum);

		long count = eHaiArticleCatMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		EHaiArticleExample aexample = new EHaiArticleExample();
		EHaiArticleExample.Criteria ca = aexample.createCriteria();
		aexample.CriteriaStoreId(ca, this.storeIdCriteriaObject(request));
		ca.andCatIdEqualTo(catId);
		count = eHaiArticleMapper.countByExample(aexample);
		if(count > 0){
			rm.setMsg("此项存在关联资讯信息，请先移除关联资讯");
			return rm;
		}

		int code = eHaiArticleCatMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}

	
	
	
	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String moduleEnum)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		EHaiArticleCat model = null;
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			//添加分类信息
			model = new EHaiArticleCat();
			model.setCatName(moduleEnum);
			model.setModule(moduleEnum);
			model.setCatType(0);
			model.setKeywords("");
			model.setCatDesc("");
			model.setSortOrder(0);
			model.setShowInNav(false);
			model.setParentId(0);
			model.setStoreId(store_id);
			eHaiArticleCatMapper.insert(model);
		}else{
			model = list.get(0);
		}
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	
}
