package org.ehais.weixin.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.ResourceUtil;
import org.ehais.weixin.mapper.WpPublicMapper;
import org.ehais.weixin.model.WpPublicWithBLOBs;
import org.ehais.weixin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private EHaiUsersMapper haiUserMapper;
	@Autowired
	private WpPublicMapper wpPublicMapper;

	public ReturnObject<EHaiUsers> login(HttpServletRequest request,String userName, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> ro = new ReturnObject<EHaiUsers>();
		ro.setCode(0);
		System.out.println(password);
		password = EncryptUtils.md5(password);
		System.out.println(password);
		EHaiUsers user = haiUserMapper.login(userName, password);
		if(user == null){
			ro.setMsg("用户不存在或密码错误!");
			return ro;
		}
		//获取微信编号wxid
		WpPublicWithBLOBs p = wpPublicMapper.public_by_user(user.getUserId());
		if(p == null){
			p = new WpPublicWithBLOBs();
			p.setUid(user.getUserId());
			wpPublicMapper.insertSelective(p);
		}
		request.getSession().setAttribute(EConstants.SESSION_WX_ID,p.getId());
		

		request.getSession().setAttribute(EConstants.SESSION_USER_NAME,userName);
		request.getSession().setAttribute(EConstants.SESSION_USER_ID,user.getUserId());
		request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE, "user");
		
		request.getSession().removeAttribute(EConstants.SESSION_ADMIN_ID);//防止应用出错
		request.getSession().removeAttribute(EConstants.SESSION_STORE_ID);//防止应用出错
		
		
		
		ro.setModel(user);
		ro.setCode(1);
		return ro;
	}

	public ReturnObject<EHaiUsers> register(
			String userName, 
			String email,
			String password,			
			String confirmPassword, 
			String verificationCode,
			String sessionCode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> ro = new ReturnObject<EHaiUsers>();
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
		EHaiUsers user = new EHaiUsers();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(EncryptUtils.md5(password));
		user.setStoreId(store_id);
		Date date = new Date();
		user.setRegTime(date);
		haiUserMapper.insertSelective(user);
		
		//生成public的数据
		WpPublicWithBLOBs p = wpPublicMapper.public_by_user(user.getUserId());
		if(p == null){
			p = new WpPublicWithBLOBs();
			p.setUid(user.getUserId());
			wpPublicMapper.insertSelective(p);
		}
		
		ro.setCode(1);
		ro.setMsg("注册成功");
		return ro;
	}

}
