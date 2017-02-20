package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.GradesDao;
import com.ehais.tracking.entity.Grades;


@Repository("gradesDao")
public class GradesDaoImpl extends CommonDaoImpl<Grades> implements GradesDao{

}
