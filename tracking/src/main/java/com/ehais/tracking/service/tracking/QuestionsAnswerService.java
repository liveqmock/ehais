package com.ehais.tracking.service.tracking;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.QuestionsAnswer;

public interface QuestionsAnswerService extends CommonService{
	public ReturnObject<QuestionsAnswer> questions_answer_list(Integer store_id) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_insert(Integer store_id) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_insert_submit(QuestionsAnswer model) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_update_submit(QuestionsAnswer model) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<QuestionsAnswer> questions_answer_delete(Integer store_id,Integer key) throws Exception;
	
	public ReturnObject<QuestionsAnswer> questions_answer_save_submit(Integer store_id,String extend) throws Exception;

}
