package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.DepartmentDao;
import com.ehais.tracking.entity.Department;


@Repository("departmentDao")
public class DepartmentDaoImpl extends CommonDaoImpl<Department> implements DepartmentDao{

}
