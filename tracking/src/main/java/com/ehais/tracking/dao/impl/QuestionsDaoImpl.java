package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.QuestionsDao;
import com.ehais.tracking.entity.Questions;

@Repository("questionsDao")
public class QuestionsDaoImpl extends CommonDaoImpl<Questions> implements QuestionsDao {

}
