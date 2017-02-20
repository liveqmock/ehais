package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpSurvey;
import org.ehais.weixin.model.WpSurveyWithBLOBs;

public interface SurveyService  extends CommonService{
	public ReturnObject<WpSurvey> survey_list(String token) throws Exception;
	public ReturnObject<WpSurvey> survey_list_json(String token,Integer page,Integer len) throws Exception;
	public ReturnObject<WpSurveyWithBLOBs> survey_insert(String token) throws Exception;
	public ReturnObject<WpSurveyWithBLOBs> survey_insert_submit(WpSurveyWithBLOBs model) throws Exception;
	public ReturnObject<WpSurveyWithBLOBs> survey_update(String token,Integer key) throws Exception;
	public ReturnObject<WpSurveyWithBLOBs> survey_update_submit(WpSurveyWithBLOBs model) throws Exception;
	public ReturnObject<WpSurveyWithBLOBs> survey_find(String token,Integer key) throws Exception;
	public ReturnObject<WpSurvey> survey_delete(String token,Integer key) throws Exception;
	
	

}
