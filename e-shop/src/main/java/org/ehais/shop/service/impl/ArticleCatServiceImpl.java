package org.ehais.shop.service.impl;

import java.util.List;

import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleCatService")
public class ArticleCatServiceImpl extends CommonServiceImpl implements ArticleCatService{

	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	
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

}
