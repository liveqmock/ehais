package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpSurveyCat;

public interface SurveyCatService  extends CommonService{
	public ReturnObject<WpSurveyCat> survey_cat_list(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_insert(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_insert_submit(WpSurveyCat model) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_update_submit(WpSurveyCat model) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyCat> survey_cat_delete(Integer store_id,Integer key) throws Exception;
	
	

}
