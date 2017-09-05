package org.ehais.shop.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.shop.mapper.HaiAgencyMapper;
import org.ehais.shop.mapper.HaiStoreMapper;
import org.ehais.shop.model.HaiAgency;
import org.ehais.shop.model.HaiAgencyExample;
import org.ehais.shop.model.HaiStore;
import org.ehais.shop.model.HaiStoreExample;
import org.ehais.shop.service.UsersAgencyService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usersAgencyService")
public class UsersAgencyServiceImpl implements UsersAgencyService {

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiStoreMapper haiStoreMapper;
	@Autowired
	private HaiAgencyMapper haiAgencyMapper;
	
	/**
	 * 代理登录
	 */
	@Override
	public ReturnObject<EHaiUsers> agencyLogin(HttpServletRequest request,
			String username, String password,String verificationcode) throws Exception {
		// TODO Auto-generated method stub
		
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(username == null || username.trim().length() == 0){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(password == null || password.trim().length() == 0){
			rm.setMsg("密码不能为空");
			return rm;
		}
		if(StringUtils.isEmpty(verificationcode)){
			rm.setMsg("验证码不能为空");
			return rm;
		}

		HttpSession session = request.getSession(true);
		String verCode = (String)session.getAttribute("verCode");
		if(verCode == null || !verCode.equals(verificationcode)){
			rm.setMsg("验证码错误");
			return rm;
		}
		
		
		password = EncryptUtils.md5(password);
		System.out.println(password);
		
		
		EHaiUsersExample example = new EHaiUsersExample();
		example.createCriteria().andUserNameEqualTo(username)
		.andPasswordEqualTo(password)
		.andStoreIdIsNotNull().andAgencyIdIsNotNull();
		List<EHaiUsers> listUser = eHaiUsersMapper.selectByExample(example);
		if(listUser == null || listUser.size() == 0){
			rm.setMsg("用户不存在或密码错误");
			return rm;
		}
		EHaiUsers users = listUser.get(0);	
		if(users.getStoreId() == null || users.getStoreId() == 0){
			rm.setMsg("您非合法商家用户，请联系微信管理员：haisoftware");
			return rm;
		}
		if(users.getAgencyId() == null || users.getAgencyId() == 0){
			rm.setMsg("您非合法代理用户，请联系微信管理员：haisoftware");
			return rm;
		}
		//查找代理是否存在
		HaiStoreExample storeExample = new HaiStoreExample();
		storeExample.createCriteria().andStoreIdEqualTo(users.getStoreId());
		List<HaiStore> listStore = haiStoreMapper.selectByExample(storeExample);
		if(listStore == null || listStore.size() == 0){
			rm.setMsg("您所在的商家非法，请联系微信管理员：haisoftware");
			return rm;
		}
		HaiStore store = listStore.get(0);
		
		//查找商家是否存在
		HaiAgencyExample agencyExample = new HaiAgencyExample();
		agencyExample.createCriteria()
		.andAgencyIdEqualTo(users.getAgencyId())
		.andStoreIdEqualTo(users.getStoreId());
		List<HaiAgency> listAgency = haiAgencyMapper.selectByExample(agencyExample);
		if(listAgency == null || listAgency.size() == 0){
			rm.setMsg("您所在的代理非法，请联系微信管理员：haisoftware");
			return rm;
		}
		HaiAgency agency = listAgency.get(0);
		
		
		rm.setCode(1);
		rm.setModel(users);
		
		request.getSession().setAttribute(EConstants.SESSION_USER_ID, users.getUserId());
		request.getSession().setAttribute(EConstants.SESSION_USER_NAME, users.getUserName());
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store.getStoreId());
		request.getSession().setAttribute(EConstants.SESSION_STORE_NAME, store.getStoreName());
		request.getSession().setAttribute(EConstants.SESSION_AGENCY_ID, agency.getAgencyId());
		request.getSession().setAttribute(EConstants.SESSION_AGENCY_NAME, agency.getAgencyName());
		
		return rm;
	}


	
}
