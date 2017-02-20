package com.ehais.tracking.dao.impl;

import org.springframework.stereotype.Repository;

import com.ehais.tracking.dao.UsersDao;
import com.ehais.tracking.entity.Users;

@Repository("usersDao")
public class UsersDaoImpl extends CommonDaoImpl<Users> implements UsersDao {

}
