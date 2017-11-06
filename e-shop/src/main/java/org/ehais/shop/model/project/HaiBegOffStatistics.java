package org.ehais.shop.model.project;

import java.io.Serializable;

public class HaiBegOffStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391808084460467951L;
	private Integer count;
	private String question;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
}
