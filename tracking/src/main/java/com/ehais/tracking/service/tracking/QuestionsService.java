package com.ehais.tracking.service.tracking;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Questions;

public interface QuestionsService extends CommonService{
	public ReturnObject<Questions> questions_list(Integer store_id) throws Exception;
	public ReturnObject<Questions> questions_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Questions> questions_insert(Integer store_id) throws Exception;
	public ReturnObject<Questions> questions_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<Questions> questions_save_submit(Questions model) throws Exception;
	public ReturnObject<Questions> questions_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<Questions> questions_delete(Integer store_id,Integer key) throws Exception;
	
	

}
