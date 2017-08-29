package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.IXiangmengService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("iXiangmengService")
public class IXiangmengServiceImpl  extends CommonServiceImpl implements IXiangmengService{
	
	@Autowired
	private EHaiArticleMapper iXiangmengMapper;

	
	public ReturnObject<EHaiArticle> xiangmeng_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticle> xiangmeng_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		if(StringUtils.isNotEmpty(title))c.andTitleLike("%"+title+"%");
		List<EHaiArticle> list = iXiangmengMapper.selectByExample(example);
		long total = iXiangmengMapper.countByExample(example);
		
		
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiArticle> xiangmeng_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticle model = new EHaiArticle();
		


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiArticle> xiangmeng_insert_submit(HttpServletRequest request,EHaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		model.setContent("");
		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = iXiangmengMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = iXiangmengMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> xiangmeng_update(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		c.andStoreIdEqualTo(store_id);
		List<EHaiArticle> list = iXiangmengMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);


		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiArticle> xiangmeng_update_submit(HttpServletRequest request,EHaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(model.getArticleId());
		c.andStoreIdEqualTo(store_id);

		long count = iXiangmengMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiArticle bean = iXiangmengMapper.selectByPrimaryKey(model.getArticleId());

bean.setCatId(model.getCatId());
bean.setCode(model.getCode());
bean.setModule(model.getModule());
bean.setTitle(model.getTitle());
bean.setContent(model.getContent());
bean.setAuthor(model.getAuthor());
bean.setAuthorEmail(model.getAuthorEmail());
bean.setArticleDate(model.getArticleDate());
bean.setStartPublishDate(model.getStartPublishDate());
bean.setEndPublishDate(model.getEndPublishDate());
bean.setStartApplyDate(model.getStartApplyDate());
bean.setEndApplyDate(model.getEndApplyDate());
bean.setKeywords(model.getKeywords());
bean.setIsOpen(model.getIsOpen());
bean.setFileUrl(model.getFileUrl());
bean.setOpenType(model.getOpenType());
bean.setLink(model.getLink());
bean.setDescription(model.getDescription());
bean.setSort(model.getSort());
bean.setArticleThumb(model.getArticleThumb());
bean.setArticleImages(model.getArticleImages());
bean.setVideoUrl(model.getVideoUrl());
bean.setArticleTypeCode(model.getArticleTypeCode());
bean.setUserId(model.getUserId());
bean.setArticleEnum(model.getArticleEnum());
bean.setArticleLabel(model.getArticleLabel());

Date date = new Date();
bean.setUpdateDate(date);

		int code = iXiangmengMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> xiangmeng_info(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<EHaiArticle> list = iXiangmengMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);


		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiArticle> xiangmeng_find(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<EHaiArticle> list = iXiangmengMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiArticle> xiangmeng_delete(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);

		long count = iXiangmengMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = iXiangmengMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	






	
}











