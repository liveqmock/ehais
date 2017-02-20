package org.ehais.shop.service.impl;

import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.AdminUserService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminUserService")
public class AdminUserServiceImpl extends CommonServiceImpl implements AdminUserService{

	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	
	
	public ReturnObject<EHaiAdminUser> admin_login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		if(username == null || username.trim().length() == 0){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(password == null || password.trim().length() == 0){
			rm.setMsg("密码不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		
		EHaiAdminUser adminuser = eHaiAdminUserMapper.login_admin(username, password);
		if(adminuser == null){
			rm.setMsg("用户名或密码不正确");
			return rm;
		}
		
		rm.setModel(adminuser);
		rm.setCode(1);
		rm.setMsg("登录成功");
		
		return rm;
	}

}
