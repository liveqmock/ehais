package com.ehais.tracking.service.tracking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.QuestionGroup;
import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.entity.QuestionnaireGather;

public interface QuestionnaireService extends CommonService{
	public ReturnObject<Questionnaire> questionnaire_list(Integer store_id) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_insert(Integer store_id) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_save_submit(Questionnaire model) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<Questionnaire> questionnaire_delete(Integer store_id,Integer key) throws Exception;
	
	
	public ReturnObject<QuestionGroup> question_group_list(Integer store_id,Integer key)throws Exception;
	
	//问卷信息收集
	public ReturnObject<QuestionnaireGather> Gather(QuestionnaireGather qg)throws Exception;
	
	public ReturnObject<Questionnaire> questionnaire_file(HttpServletRequest request, HttpServletResponse response)throws Exception;
	
	public ReturnObject<QuestionnaireGather> questionnaire_gather_list_json(HttpServletRequest request,Integer questionnaireId,Integer page,Integer len) throws Exception;
	
	
}
