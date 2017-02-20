package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.LeaderDao;
import com.ehais.tracking.entity.Leader;

@Repository("leaderDao")
public class LeaderDaoImpl extends CommonDaoImpl<Leader> implements LeaderDao{

}
