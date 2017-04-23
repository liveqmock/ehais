package org.ehais.shop.service.impl;

import java.util.List;

import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleServiceImpl  extends CommonServiceImpl implements ArticleService{

	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;

	public ReturnObject<EHaiArticle> article_list_cid(Integer store_id, Integer cat_id,Integer page,Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		Integer start = (((page == null)?1:page) - 1 ) * len;
		
		EHaiArticleExample example = new EHaiArticleExample();
		example.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cat_id);
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<EHaiArticle> list = eHaiArticleMapper.selectByExample(example);
		
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	
	
	public ReturnObject<EHaiArticle> article_list_code(Integer store_id, String cat_code,Integer page,Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		Integer start = (((page == null)?1:page) - 1 ) * len;
		List<EHaiArticle> list = eHaiArticleMapper.article_list_by_catcode(store_id, cat_code, start, len);
		
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	

	public ReturnObject<EHaiArticle> article_info(Integer store_id, Integer article_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		EHaiArticle model = eHaiArticleMapper.get_hai_article_info(store_id, article_id);
		rm.setModel(model);
		rm.setCode(1);
		return rm;
	}
	
}
