package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.SchoolDao;
import com.ehais.tracking.entity.School;

@Repository("schoolDao")
public class SchoolDaoImpl extends CommonDaoImpl<School> implements SchoolDao{

}
