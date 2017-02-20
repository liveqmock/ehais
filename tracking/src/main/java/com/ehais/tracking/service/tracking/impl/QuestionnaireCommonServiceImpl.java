package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;

import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.entity.Questions;
import com.ehais.tracking.entity.QuestionsAnswer;

public class QuestionnaireCommonServiceImpl extends TrackingCommonServiceImpl{

	protected List<BootStrapModel> formatQuestionnaireBootStrapList(Questionnaire model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "问卷标题：", model.getTitle(), "请输入问卷标题", "", "", null, 0));
		Map<String,String> statusMap = new HashMap<String, String>();
//		statusMap.put("1", "审核");
//		bootStrapList.add(new BootStrapModel("radio", "status", "状态：", model.getStatus(), "请输入", "", "", statusMap, 0));
		bootStrapList.add(new BootStrapModel("textarea", "instructions", "问卷描述：", model.getInstructions(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "display", "", model.getDisplay(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "previewStatus", "", model.getPreviewStatus(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "template", "", model.getTemplate(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "schoolId", "", model.getSchoolId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "startTime", "开始时间：", model.getStartTime(), "为空不限制", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "endTime", "截止时间：", model.getEndTime(), "为空不限制", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("images", "picPath", "PC图片", model.getPicPath(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("images", "appPic_path", "APP图片", model.getAppPicPath(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
	
	protected List<BootStrapModel> formatQuestionsBootStrapList(Questions model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "questionsId__Index", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "questionsTitle__Index", "题目标题：", model.getTitle(), "请输入", "", "", null, 0));
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("1", "单选");
		typeMap.put("2", "多选");
		typeMap.put("3", "填空");
		bootStrapList.add(new BootStrapModel("select", "questionsType__Index", "问卷类型：", model.getType(), "请输入", "", "", typeMap, 0));
		Map<String, String> emptyMap = new HashMap<String, String>();
		emptyMap.put("1", "必填");
		bootStrapList.add(new BootStrapModel("checkbox", "questionsNotEmpty__Index", "必填题目", (model.getNotEmpty() == null)?"1":model.getNotEmpty(), "请输入", "", "", emptyMap, 0));

		return bootStrapList;
	}
	
	
	protected List<BootStrapModel> formatAnswerBootStrapList(QuestionsAnswer model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "questionsAnswerId__Index__Item", "_Item.", model.getId(), "", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("hidden", "questionsId__Index__Item", "_Item.", model.getQuestionsId(), "", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text_icon", "questionsAnswerName__Index__Item", "_Item.", model.getName(), "", "", "", null, 0));
		
		return bootStrapList;
	}
	
}
