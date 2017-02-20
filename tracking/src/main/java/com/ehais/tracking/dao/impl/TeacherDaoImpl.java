package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.TeacherDao;
import com.ehais.tracking.entity.Teacher;

@Repository("teacherDao")
public class TeacherDaoImpl extends CommonDaoImpl<Teacher> implements TeacherDao{

}
