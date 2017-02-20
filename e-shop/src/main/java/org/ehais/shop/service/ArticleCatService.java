package org.ehais.shop.service;

import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface ArticleCatService extends CommonService{

	public ReturnObject<EHaiArticleCat> article_cat_parent_list(Integer store_id,Integer parent_id)throws Exception;
	
}
