package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.QuestionsAnswerDao;
import com.ehais.tracking.entity.QuestionsAnswer;
import com.ehais.tracking.service.tracking.QuestionsAnswerService;

import net.sf.json.JSONArray;


@Service("questionsAnswerService")
//@Transactional
public class QuestionsAnswerServiceImpl  extends QuestionnaireCommonServiceImpl implements QuestionsAnswerService{
	
	@Autowired
	private QuestionsAnswerDao questionsAnswerDao;
	
	public ReturnObject<QuestionsAnswer> questions_answer_list(Integer store_id) throws Exception{
		
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<QuestionsAnswer> questions_answer_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		ReturnObject<QuestionsAnswer> rm = questionsAnswerDao.select(QuestionsAnswer.class, page, len, map);
		
		return rm;
	}

	public ReturnObject<QuestionsAnswer> questions_answer_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();	
		QuestionsAnswer model = new QuestionsAnswer();
		rm.setBootStrapList(this.formatAnswerBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<QuestionsAnswer> questions_answer_insert_submit(QuestionsAnswer model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		questionsAnswerDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<QuestionsAnswer> questions_answer_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		
		QuestionsAnswer model = questionsAnswerDao.findById(QuestionsAnswer.class, "id", key);
		rm.setBootStrapList(this.formatAnswerBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<QuestionsAnswer> questions_answer_update_submit(QuestionsAnswer model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		questionsAnswerDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<QuestionsAnswer> questions_answer_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		
		
		QuestionsAnswer model = questionsAnswerDao.findById(QuestionsAnswer.class, "id", key);
		rm.setBootStrapList(this.formatAnswerBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<QuestionsAnswer> questions_answer_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
		questionsAnswerDao.delete(QuestionsAnswer.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	


	@Override
	public ReturnObject<QuestionsAnswer> questions_answer_save_submit(Integer store_id, String extend) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionsAnswer> rm = new ReturnObject<QuestionsAnswer>();
//		System.out.println(extend);
		JSONArray jsonArray = JSONArray.fromObject(extend);
		List<QuestionsAnswer> list = new ArrayList<QuestionsAnswer>();

		for(int i = 0 ; i < jsonArray.size() ; i++){
			JSONArray ja = jsonArray.getJSONArray(i);
//			System.out.println(ja.getString(0) + ja.getString(1) +ja.getString(2)+ja.getString(3) +ja.getInt(4));
			QuestionsAnswer qa = new QuestionsAnswer();
			qa.setQuestionsId(ja.getInt(1));
			qa.setQuestionnaireId(ja.getInt(2));
			qa.setName(ja.getString(3));
			qa.setSort(ja.getInt(4));
			if(ja.getString(0) == null || ja.getString(0).equals("")){
				//插入
				questionsAnswerDao.insert(qa);
			}else{
				//更新
				qa.setId(ja.getInt(0));
				questionsAnswerDao.update(qa);				
			}
			
			list.add(qa);
		}
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	
}
