package com.ehais.tracking.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.QuestionnaireDao;
import com.ehais.tracking.entity.Questionnaire;

@Repository("questionnaireDao")
public class QuestionnaireDaoImpl extends CommonDaoImpl<Questionnaire> implements QuestionnaireDao {

}
