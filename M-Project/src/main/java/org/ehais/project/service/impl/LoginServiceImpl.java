package org.ehais.project.service.impl;

import java.util.Date;

import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.service.LoginService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private HaiUsersMapper haiUserMapper;

	public ReturnObject<HaiUsers> login(String userName, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUsers> ro = new ReturnObject<HaiUsers>();
		System.out.println(password);
		password = EncryptUtils.md5(password);
		System.out.println(password);
		HaiUsers user = haiUserMapper.login(userName, password);
		ro.setModel(user);
		ro.setCode(1);
		return ro;
	}

	public ReturnObject<HaiUsers> register(
			String userName, 
			String email,
			String password,			
			String confirmPassword, 
			String verificationCode,
			String sessionCode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUsers> ro = new ReturnObject<HaiUsers>();
		ro.setCode(0);
		
		
		if(userName==null || userName.equals("")){
			ro.setMsg("用户名为空");
			return ro;
		}
		if(email==null || email.equals("")){
			ro.setMsg("邮箱为空");
			return ro;
		}
		if(password==null || password.equals("")){
			ro.setMsg("密码为空");
			return ro;
		}
		if(confirmPassword==null || confirmPassword.equals("")){
			ro.setMsg("确认密码为空");
			return ro;
		}
		if(verificationCode==null || verificationCode.equals("")){
			ro.setMsg("验证码为空");
			return ro;
		}
		if(!verificationCode.equals(sessionCode)){
			ro.setMsg("验证码错误");
			return ro;
		}
		if(!password.equals(confirmPassword)){
			ro.setMsg("密码不一致");
			return ro;
		}
		
		Integer store_id = Integer.valueOf(ResourceUtil.getProValue("store_id"));
		int c = haiUserMapper.checkUser(userName, email);
		if(c>0){
			ro.setMsg("用户名或邮箱存在");
			return ro;
		}
		HaiUsers user = new HaiUsers();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(EncryptUtils.md5(password));
		user.setStoreId(store_id);
		Date date = new Date();
		user.setRegTime(date);
		haiUserMapper.insertSelective(user);
		ro.setCode(1);
		ro.setMsg("注册成功");
		return ro;
	}

}
