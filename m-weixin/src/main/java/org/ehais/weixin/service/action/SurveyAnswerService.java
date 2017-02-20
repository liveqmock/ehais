package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpSurveyAnswer;

public interface SurveyAnswerService extends CommonService{
	public ReturnObject<WpSurveyAnswer> survey_answer_list(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_insert(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_insert_submit(WpSurveyAnswer model) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_insert_submit(String token,String openid,Integer surveyId,String extend) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_update_submit(WpSurveyAnswer model) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyAnswer> survey_answer_delete(Integer store_id,Integer key) throws Exception;
	
	

}
