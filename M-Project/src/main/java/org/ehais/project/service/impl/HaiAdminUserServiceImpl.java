package org.ehais.project.service.impl;

import org.ehais.project.mapper.HaiAdminUserMapper;
import org.ehais.project.model.HaiAdminUser;
import org.ehais.project.service.HaiAdminUserService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("adminService")
public class HaiAdminUserServiceImpl implements HaiAdminUserService{

	@Autowired
	private HaiAdminUserMapper haiAdminUserMapper;
	
	public ReturnObject<HaiAdminUser> login(String userName, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> ro = new ReturnObject<HaiAdminUser>();
		System.out.println(password);
		password = EncryptUtils.md5(password);
		System.out.println(password);
		HaiAdminUser user = haiAdminUserMapper.login(userName, password);
		ro.setModel(user);
		ro.setCode(1);
		return ro;
	}

}
