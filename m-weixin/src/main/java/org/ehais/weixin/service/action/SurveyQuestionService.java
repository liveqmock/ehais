package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpSurveyQuestion;
import org.ehais.weixin.model.WpSurveyQuestionWithBLOBs;

public interface SurveyQuestionService extends CommonService{
	public ReturnObject<WpSurveyQuestion> survey_question_list(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyQuestion> survey_question_list_json(String token,Integer survey_id,Integer page,Integer len) throws Exception;
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_insert(Integer store_id) throws Exception;
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_insert_submit(WpSurveyQuestionWithBLOBs model) throws Exception;
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_update_submit(WpSurveyQuestionWithBLOBs model) throws Exception;
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpSurveyQuestion> survey_question_delete(Integer store_id,Integer key) throws Exception;
	
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_blob_list_json(String token,Integer survey_id,Integer page,Integer len) throws Exception;
	

}
