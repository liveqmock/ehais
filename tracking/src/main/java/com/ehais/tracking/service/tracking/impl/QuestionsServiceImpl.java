package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.QuestionsDao;
import com.ehais.tracking.entity.Questions;
import com.ehais.tracking.service.tracking.QuestionsService;


@Service("questionsService")
public class QuestionsServiceImpl extends QuestionnaireCommonServiceImpl implements QuestionsService{
	
	@Autowired
	private QuestionsDao questionsDao;
	
	public ReturnObject<Questions> questions_list(Integer store_id) throws Exception{
		
		ReturnObject<Questions> rm = new ReturnObject<Questions>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Questions> questions_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		ReturnObject<Questions> rm = questionsDao.select(Questions.class, page, len, map);
		
		return rm;
	}

	public ReturnObject<Questions> questions_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questions> rm = new ReturnObject<Questions>();	
		Questions model = new Questions();
		rm.setBootStrapList(this.formatQuestionsBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Questions> questions_save_submit(Questions model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questions> rm = new ReturnObject<Questions>();
		
		if(model.getId() == null || model.getId() == 0){
			questionsDao.insert(model);
		}else{
			questionsDao.update(model);
		}
		
		rm.setModel(model);
		rm.setCode(1);
		rm.setMsg("提交成功");
		return rm;
	}

	public ReturnObject<Questions> questions_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questions> rm = new ReturnObject<Questions>();
		
		Questions model = questionsDao.findById(Questions.class, "id", key);
		rm.setBootStrapList(this.formatQuestionsBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	

	public ReturnObject<Questions> questions_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questions> rm = new ReturnObject<Questions>();
		
		
		Questions model = questionsDao.findById(Questions.class, "id", key);
		rm.setBootStrapList(this.formatQuestionsBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Questions> questions_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questions> rm = new ReturnObject<Questions>();
		questionsDao.delete(Questions.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	

	
}
