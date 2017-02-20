package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpSurveyQuestionMapper;
import org.ehais.weixin.model.WpSurveyQuestion;
import org.ehais.weixin.model.WpSurveyQuestionExample;
import org.ehais.weixin.model.WpSurveyQuestionWithBLOBs;
import org.ehais.weixin.service.action.SurveyQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("surveyQuestionService")
public class SurveyQuestionServiceImpl extends CommonServiceImpl implements SurveyQuestionService{
	
	@Autowired
	private WpSurveyQuestionMapper wpSurveyQuestionMapper;
	
	public ReturnObject<WpSurveyQuestion> survey_question_list(Integer store_id) throws Exception{
		
		ReturnObject<WpSurveyQuestion> rm = new ReturnObject<WpSurveyQuestion>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpSurveyQuestion> survey_question_list_json(String token,
			Integer survey_id,Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestion> rm = new ReturnObject<WpSurveyQuestion>();
		Integer start = (page != null && len != null)?((page - 1 ) * len) : null;
		
		WpSurveyQuestionExample example = new WpSurveyQuestionExample();
		WpSurveyQuestionExample.Criteria c = example.createCriteria();
		c.andSurveyIdEqualTo(survey_id);
		c.andTokenEqualTo(token);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("sort asc");
		List<WpSurveyQuestion> list = wpSurveyQuestionMapper.wp_survey_question_list_by_example(example);
		Integer total = wpSurveyQuestionMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();	
		WpSurveyQuestionWithBLOBs model = new WpSurveyQuestionWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_insert_submit(WpSurveyQuestionWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();
		int code = wpSurveyQuestionMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();
		
		WpSurveyQuestionWithBLOBs model = wpSurveyQuestionMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_update_submit(WpSurveyQuestionWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();
		WpSurveyQuestionExample example = new WpSurveyQuestionExample();
		WpSurveyQuestionExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(model.getToken());
		c.andIdEqualTo(model.getId());
		c.andSurveyIdEqualTo(model.getSurveyId());
//		c.andStoreIdEqualTo(model.getStoreId());
//		c.andKeyIdEqualTo(model.getKeyId());
		int code = wpSurveyQuestionMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();
		
		WpSurveyQuestionWithBLOBs model = wpSurveyQuestionMapper.selectByPrimaryKey(key);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpSurveyQuestion> survey_question_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestion> rm = new ReturnObject<WpSurveyQuestion>();
		WpSurveyQuestionExample example = new WpSurveyQuestionExample();
		WpSurveyQuestionExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
//		c.andKeyIdEqualTo(key);
		int code = wpSurveyQuestionMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	
	public ReturnObject<WpSurveyQuestionWithBLOBs> survey_question_blob_list_json(String token,
			Integer survey_id,Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyQuestionWithBLOBs> rm = new ReturnObject<WpSurveyQuestionWithBLOBs>();
		Integer start = (page != null && len != null)?((page - 1 ) * len) : null;
		
		WpSurveyQuestionExample example = new WpSurveyQuestionExample();
		WpSurveyQuestionExample.Criteria c = example.createCriteria();
		c.andSurveyIdEqualTo(survey_id);
		c.andTokenEqualTo(token);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("sort asc");
		List<WpSurveyQuestionWithBLOBs> list = wpSurveyQuestionMapper.wp_survey_question_blob_list_by_example(example);
		Integer total = wpSurveyQuestionMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	
	private List<BootStrapModel> formatBootStrapList(WpSurveyQuestionWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}
