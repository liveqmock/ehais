package com.ehais.tracking.entity;

import java.util.List;

import org.ehais.model.BootStrapModel;

public class QuestionGroup {
	private List<List<BootStrapModel>> questionsBSML;
	private List<List<BootStrapModel>> answerBSML;
	
	List<Questions> questionsList;
	List<QuestionsAnswer> answerList;
	
	public List<List<BootStrapModel>> getQuestionsBSML() {
		return questionsBSML;
	}
	public void setQuestionsBSML(List<List<BootStrapModel>> questionsBSML) {
		this.questionsBSML = questionsBSML;
	}
	public List<List<BootStrapModel>> getAnswerBSML() {
		return answerBSML;
	}
	public void setAnswerBSML(List<List<BootStrapModel>> answerBSML) {
		this.answerBSML = answerBSML;
	}
	public List<Questions> getQuestionsList() {
		return questionsList;
	}
	public void setQuestionsList(List<Questions> questionsList) {
		this.questionsList = questionsList;
	}
	public List<QuestionsAnswer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<QuestionsAnswer> answerList) {
		this.answerList = answerList;
	}
	
}
