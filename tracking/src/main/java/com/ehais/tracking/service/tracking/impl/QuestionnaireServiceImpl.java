package com.ehais.tracking.service.tracking.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.QuestionnaireDao;
import com.ehais.tracking.dao.QuestionnaireGatherDao;
import com.ehais.tracking.dao.QuestionsAnswerDao;
import com.ehais.tracking.dao.QuestionsDao;
import com.ehais.tracking.entity.QuestionGroup;
import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.entity.QuestionnaireGather;
import com.ehais.tracking.entity.Questions;
import com.ehais.tracking.entity.QuestionsAnswer;
import com.ehais.tracking.service.tracking.QuestionnaireService;

import net.sf.json.JSONObject;

@Service("questionnaireService")
public class QuestionnaireServiceImpl  extends QuestionnaireCommonServiceImpl implements QuestionnaireService{
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	@Autowired
	private QuestionsDao questionsDao;
	@Autowired
	private QuestionsAnswerDao questionsAnswerDao;	
	@Autowired
	private QuestionnaireGatherDao questionnaireGatherDao;
	
	
	
	public ReturnObject<Questionnaire> questionnaire_list(Integer store_id) throws Exception{
		
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Questionnaire> questionnaire_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		this.storeIdMap(request, map);
		ReturnObject<Questionnaire> rm = questionnaireDao.select(Questionnaire.class, page, len, map);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Questionnaire> questionnaire_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();	
		Questionnaire model = new Questionnaire();
		rm.setBootStrapList(this.formatQuestionnaireBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Questionnaire> questionnaire_save_submit(Questionnaire model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		if(model.getId() == null || model.getId() == 0){
			questionnaireDao.insert(model);
		}else{
			questionnaireDao.update(model);
		}
		
		rm.setCode(1);
		rm.setMsg("提交成功");
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Questionnaire> questionnaire_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		//问卷信息获取
		Questionnaire model = questionnaireDao.findById(Questionnaire.class, "id", key);
		rm.setBootStrapList(this.formatQuestionnaireBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	

	public ReturnObject<Questionnaire> questionnaire_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		
		
		Questionnaire model = questionnaireDao.findById(Questionnaire.class, "id", key);
		rm.setBootStrapList(this.formatQuestionnaireBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Questionnaire> questionnaire_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		questionnaireDao.delete(Questionnaire.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}

	@Override
	public ReturnObject<QuestionGroup> question_group_list(Integer store_id,
			Integer key) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionGroup> rm = new ReturnObject<QuestionGroup>();
		
		//问题信息获取
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionnaireId", key);
		Map<String, Object> mapOrder = new HashMap<String, Object>();
		mapOrder.put("sort", "asc");
		List<Questions> questionsList = questionsDao.selectAll(Questions.class, null, null, map, null, mapOrder);
		
		Integer[] questionIds = new Integer[questionsList.size()];
		int i = 0;
		List<List<BootStrapModel>> questionsBSML = new ArrayList<List<BootStrapModel>>();
		for (Questions questions : questionsList) {
			questionIds[i] = questions.getId();
			i++;
			questionsBSML.add(this.formatQuestionsBootStrapList(questions));
		}
		List<QuestionsAnswer> answerList = null;
		List<List<BootStrapModel>> answerBSML = new ArrayList<List<BootStrapModel>>();
		if(questionIds.length > 0){
			Map<String, Object[]> mapIn = new HashMap<String, Object[]>();
			mapIn.put("questionsId", questionIds);			
			answerList = questionsAnswerDao.selectAll(QuestionsAnswer.class, null, null, map, mapIn, mapOrder);
			for (QuestionsAnswer questionsAnswer : answerList) {
				answerBSML.add(this.formatAnswerBootStrapList(questionsAnswer));
			}
		}
		
		QuestionGroup qGroup = new QuestionGroup();
		qGroup.setQuestionsBSML(questionsBSML);
		qGroup.setAnswerBSML(answerBSML);
		qGroup.setQuestionsList(questionsList);
		qGroup.setAnswerList(answerList);
		
		rm.setModel(qGroup);
		rm.setCode(1);
		
		return rm;
	}

	@Override
	public ReturnObject<QuestionnaireGather> Gather(QuestionnaireGather qg)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<QuestionnaireGather> rm = new ReturnObject<QuestionnaireGather>();
		questionnaireGatherDao.insert(qg);
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<Questionnaire> questionnaire_file(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Questionnaire> rm = new ReturnObject<Questionnaire>();
		String jsonStr = UploadUtils.upload_file(request, response);
		System.out.println(jsonStr);
		JSONObject json = JSONObject.fromObject(jsonStr);
		System.out.println(json.getString("msg"));
		InputStream is = new FileInputStream(json.getString("msg"));
		XWPFDocument  doc = new XWPFDocument(is); 
		POIXMLTextExtractor ex = new XWPFWordExtractor(doc);  
        String text = ex.getText();
		if (is != null)is.close();    
		String [] ss= text.split("\n");
		String   title ="";
		String instructions="";
		int titlenum=0;
		int descnum=0;
		int qunum=0;
       for(int i=0; i<ss.length;i++){
    	   
    	   if(ss[i].length()>1){
           if(ss[i].indexOf("[问卷标题]")>=0){
        	   titlenum=i;
           }
           if(ss[i].indexOf("[问卷说明]")>=0){
        	   descnum=i;
           }
           if(ss[i].indexOf("[问题]")>=0&&qunum==0){
        	   qunum=i;
           }

    	   }		    	   
       }
       for(int j=titlenum; j<descnum;j++){
		   title=title+ss[j].replaceAll("[问卷标题]", "").replaceAll("\\[\\]", "").replaceAll(" ", "");
	   }
	   for(int d=descnum; d<qunum;d++){
		   instructions=instructions+ss[d].replaceAll("问卷说明", "").replaceAll("\\[\\]", "").replaceAll(" ", "");
	   }
	   
	   System.out.println(title);
	   System.out.println("===================================");
	   
	   System.out.println(instructions);
	   System.out.println("===================================");
	   Questionnaire q=new Questionnaire();
       q.setTitle(title);
       q.setDisplay(1);
       q.setInstructions(instructions);
       q.setPreviewStatus(2);
       
       questionnaireDao.insert(q);
       int qMaxId= q.getId();
       
       int questionsMaxId = 0;
       
       int ordernum=1;
	   String questionsTitle="";
	   for(int k=qunum;k<ss.length;k++){
		   if(ss[k].length()>1){
		   if(ss[k].indexOf("[问题]")>=0){
			   int type=1;
			   if(ss[k].indexOf("[多选题]")>=0){
				    type=2;
			   }
			   if(ss[k].indexOf("[填空题]")>=0){
				    type=3;
			   }
			   
			   questionsTitle=ss[k].replaceAll("[问题]", "").replaceAll("\\[\\]", "").replaceAll(" ", "").replaceAll("[填空题]", "").replaceAll("\\[\\]", "").replaceAll(" ", "").replaceAll("[多选题]", "").replaceAll("\\[\\]", "").replaceAll(" ", "");
			   System.out.println(questionsTitle);
			   Questions qs = new Questions();
			   qs.setQuestionnaireId(qMaxId);
			   qs.setTitle(questionsTitle);
			   qs.setType(type);
			   qs.setSort(ordernum);
			   questionsDao.insert(qs);
			   questionsMaxId = qs.getId();
			   
			   ordernum=ordernum+1;
			   
		   }else{
			   String answerName=ss[k];
			   System.out.println(answerName);
			   QuestionsAnswer qa = new QuestionsAnswer();
			   qa.setName(answerName);
			   qa.setQuestionnaireId(qMaxId);
			   qa.setQuestionsId(questionsMaxId);
			   questionsAnswerDao.insert(qa);
		   }
		  }
	   }
	   
	   rm.setCode(1);
	   rm.setMsg("添加问卷成功");
	   
		return rm;
	}

	@Override
	public ReturnObject<QuestionnaireGather> questionnaire_gather_list_json(
			HttpServletRequest request, 
			Integer questionnaireId, Integer page, Integer len)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("questionnaireId", questionnaireId);
		this.storeIdMap(request, map);
		ReturnObject<QuestionnaireGather> rm = questionnaireGatherDao.select(QuestionnaireGather.class, page, len, map);
		
		rm.setCode(1);
		return rm;
	}
	

	
}