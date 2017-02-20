package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.StudentDao;
import com.ehais.tracking.entity.Student;

@Repository("studentDao")
public class StudentDaoImpl extends CommonDaoImpl<Student> implements StudentDao{

}
