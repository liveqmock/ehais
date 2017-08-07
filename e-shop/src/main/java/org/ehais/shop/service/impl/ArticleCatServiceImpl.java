package org.ehais.shop.service.impl;

import java.util.Date;
import java.util.List;

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
	
	
	public ReturnObject<EHaiArticleCat> article_cat_parent_list(Integer store_id, Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		example.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andParentIdEqualTo(Short.valueOf(parent_id+""));
		
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	@Override
	public List<EHaiArticleCat> articleCatList(Integer store_id, Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		if(parent_id != 0)c.andParentIdEqualTo(Short.valueOf(parent_id+""));
		
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		return list;
	}
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,EHaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();

		if(model.getTitle() == null || model.getTitle().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(store_id == null)store_id = 55;
		model.setStoreId(store_id);
		model.setCreateDate(new Date());

		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		c.andStoreIdEqualTo(store_id);
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

}
