package org.ehais.project.service.impl;

import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.service.HaiUserService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("haiUserService")
public class HaiUserServiceImpl implements HaiUserService {

	@Autowired
	private HaiUsersMapper haiUserMapper;

	public HaiUsers getUserById(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return haiUserMapper.selectByPrimaryKey(userId);
	}

	public ReturnObject<HaiUsers> login(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ReturnObject<HaiUsers> register(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
