package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpSurveyMapper;
import org.ehais.weixin.model.WpSurvey;
import org.ehais.weixin.model.WpSurveyExample;
import org.ehais.weixin.model.WpSurveyWithBLOBs;
import org.ehais.weixin.service.action.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("surveyService")
public class SurveyServiceImpl   extends CommonServiceImpl implements SurveyService{
	
	@Autowired
	private WpSurveyMapper wpSurveyMapper;
	
	public ReturnObject<WpSurvey> survey_list(String token) throws Exception{
		
		ReturnObject<WpSurvey> rm = new ReturnObject<WpSurvey>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpSurvey> survey_list_json(String token,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurvey> rm = new ReturnObject<WpSurvey>();
		Integer start = (page - 1 ) * len;
		
		WpSurveyExample example = new WpSurveyExample();
		WpSurveyExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(token);
		example.setStart(start);
		example.setLen(len);
		List<WpSurvey> list = wpSurveyMapper.wp_survey_list_by_example(example);
		Integer total = wpSurveyMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpSurveyWithBLOBs> survey_insert(String token)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyWithBLOBs> rm = new ReturnObject<WpSurveyWithBLOBs>();	
		WpSurveyWithBLOBs model = new WpSurveyWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpSurveyWithBLOBs> survey_insert_submit(WpSurveyWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyWithBLOBs> rm = new ReturnObject<WpSurveyWithBLOBs>();
		int code = wpSurveyMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpSurveyWithBLOBs> survey_update(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyWithBLOBs> rm = new ReturnObject<WpSurveyWithBLOBs>();
		
		WpSurveyWithBLOBs model = wpSurveyMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpSurveyWithBLOBs> survey_update_submit(WpSurveyWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyWithBLOBs> rm = new ReturnObject<WpSurveyWithBLOBs>();
		WpSurveyExample example = new WpSurveyExample();
		WpSurveyExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(model.getToken());
		c.andIdEqualTo(model.getId());
				
		int code = wpSurveyMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpSurveyWithBLOBs> survey_find(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyWithBLOBs> rm = new ReturnObject<WpSurveyWithBLOBs>();
		
		
		WpSurveyWithBLOBs model = wpSurveyMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpSurvey> survey_delete(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurvey> rm = new ReturnObject<WpSurvey>();
		WpSurveyExample example = new WpSurveyExample();
		WpSurveyExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(token);
		c.andIdEqualTo(key);
//		c.andStoreIdEqualTo(store_id);
//		c.andKeyIdEqualTo(key);
		int code = wpSurveyMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(WpSurveyWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "keyword", "关键词", model.getKeyword(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "keywordType", "", model.getKeywordType(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "surCat_id", "", model.getSurCat_id(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textarea", "intro", "简介", model.getIntro(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "mTime", "", model.getMTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "cover", "", model.getCover(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "cTime", "", model.getCTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "token", "", model.getToken(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("textarea", "finishTip", "", model.getFinishTip(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "template", "", model.getTemplate(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "startTime", "开始时间", model.getStartTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "endTime", "结束时间", model.getEndTime(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "images", "图片", model.getImages(), "请输入", "", "", null, 0));
		Map<String, String> isVoidMap = new HashMap<String, String>();
		isVoidMap.put("1", "可用");
		isVoidMap.put("0", "不可用");
		
//		bootStrapList.add(new BootStrapModel("checkbox", "isVoid", "是否可用", model.getIsVoid(), "请输入", "", "", isVoidMap, 0));
		
		bootStrapList.add(new BootStrapModel("radio", "isVoid", "是否可用", model.getIsVoid(), "请输入", "", "", isVoidMap, 0));
		
//		bootStrapList.add(new BootStrapModel("text", "extendMsg", "", model.getExtendMsg(), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
}
