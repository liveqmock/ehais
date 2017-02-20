package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpSurveyAnswerMapper;
import org.ehais.weixin.model.WpSurveyAnswer;
import org.ehais.weixin.model.WpSurveyAnswerExample;
import org.ehais.weixin.service.action.SurveyAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("surveyAnswerService")
public class SurveyAnswerServiceImpl extends CommonServiceImpl implements SurveyAnswerService{
	
	@Autowired
	private WpSurveyAnswerMapper wpSurveyAnswerMapper;
	
	public ReturnObject<WpSurveyAnswer> survey_answer_list(Integer store_id) throws Exception{
		
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		Integer start = (page - 1 ) * len;
		
		WpSurveyAnswerExample example = new WpSurveyAnswerExample();
		WpSurveyAnswerExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<WpSurveyAnswer> list = wpSurveyAnswerMapper.wp_survey_answer_list_by_example(example);
		Integer total = wpSurveyAnswerMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();	
		WpSurveyAnswer model = new WpSurveyAnswer();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpSurveyAnswer> survey_answer_insert_submit(WpSurveyAnswer model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		int code = wpSurveyAnswerMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		
		WpSurveyAnswer model = wpSurveyAnswerMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpSurveyAnswer> survey_answer_update_submit(WpSurveyAnswer model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		WpSurveyAnswerExample example = new WpSurveyAnswerExample();
		WpSurveyAnswerExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(model.getStoreId());
		c.andIdEqualTo(model.getId());
		int code = wpSurveyAnswerMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		
		
		WpSurveyAnswer model = wpSurveyAnswerMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		WpSurveyAnswerExample example = new WpSurveyAnswerExample();
		WpSurveyAnswerExample.Criteria c = example.createCriteria();

		c.andIdEqualTo(key);
		int code = wpSurveyAnswerMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(WpSurveyAnswer model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	public ReturnObject<WpSurveyAnswer> survey_answer_insert_submit(
			String token, String openid, Integer surveyId, String extend)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyAnswer> rm = new ReturnObject<WpSurveyAnswer>();
		WpSurveyAnswer model = new WpSurveyAnswer();
		model.setAnswer(extend);
		model.setOpenid(openid);
		model.setQuestionId(0);
		model.setSurveyId(surveyId);
		model.setToken(token);
		int code = wpSurveyAnswerMapper.insert(model);
		rm.setCode(code);
		rm.setMsg("提交成功，感谢你的支持!");
		return rm;
	}
	
}
