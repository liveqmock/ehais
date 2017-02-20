package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.HaiAdminUserDao;
import com.ehais.tracking.entity.HaiAdminUser;

@Repository("haiAdminUserDao")
public class HaiAdminUserDaoImpl extends CommonDaoImpl<HaiAdminUser> implements HaiAdminUserDao{

}
