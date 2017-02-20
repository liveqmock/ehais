package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.ProfessionalDao;
import com.ehais.tracking.entity.Professional;


@Repository("professionalDao")
public class ProfessionalDaoImpl extends CommonDaoImpl<Professional> implements ProfessionalDao{

}
