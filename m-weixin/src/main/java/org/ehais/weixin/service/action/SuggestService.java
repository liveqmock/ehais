package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiSuggest;

public interface SuggestService extends CommonService {

	public ReturnObject<HaiSuggest> suggest_list(Integer store_id) throws Exception;
	public ReturnObject<HaiSuggest> suggest_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	
	public ReturnObject<HaiSuggest> suggest_insert(HaiSuggest suggest)throws Exception;
	
	
	public ReturnObject<HaiSuggest> suggest_find(Integer store_id,Integer key) throws Exception;
	
	
}
