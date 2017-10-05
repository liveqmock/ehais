package org.ehais.shop.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiPartnerMapper;
import org.ehais.shop.mapper.tp.TpAdminMapper;
import org.ehais.shop.mapper.tp.TpSuppliersMapper;
import org.ehais.shop.model.HaiPartner;
import org.ehais.shop.model.HaiPartnerExample;
import org.ehais.shop.model.tp.TpAdmin;
import org.ehais.shop.model.tp.TpAdminExample;
import org.ehais.shop.model.tp.TpSuppliers;
import org.ehais.shop.model.tp.TpSuppliersExample;
import org.ehais.shop.service.AdminUserService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminUserService")
public class AdminUserServiceImpl extends CommonServiceImpl implements AdminUserService{

	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private TpAdminMapper tpAdminMapper;
	@Autowired
	private TpSuppliersMapper tpSuppliersMapper;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;
	
	
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

	
	@Override
	public ReturnObject<EHaiAdminUser> hai_login_submit(HttpServletRequest request,String username, String password, String verificationcode)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		
		if(StringUtils.isEmpty(username)){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(StringUtils.isEmpty(password)){
			rm.setMsg("密码不能为空");
			return rm;
		}
		if(StringUtils.isEmpty(verificationcode)){
			rm.setMsg("验证码不能为空");
			return rm;
		}
		
		HttpSession session = request.getSession(true);
		String verCode = (String)session.getAttribute("verCode");
		if(verCode == null || !verCode.toLowerCase().equals(verificationcode.toLowerCase())){
			rm.setMsg("验证码错误");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(username);
		c.andPasswordEqualTo(password);
		System.out.println(username+"=="+password);
		
		List<EHaiAdminUser> listAdmin = eHaiAdminUserMapper.selectByExample(example);
		
		if(listAdmin == null || listAdmin.size() == 0){
			rm.setMsg("用户名或密码不正确");
			return rm;
		}
		EHaiAdminUser adminuser = listAdmin.get(0);
		session.setAttribute(EConstants.SESSION_ADMIN_ID, adminuser.getAdminId());
		session.setAttribute(EConstants.SESSION_ADMIN_NAME, adminuser.getUserName());
		
		if(adminuser.getClassify() != null && adminuser.getClassify().equals(EAdminClassifyEnum.partner)){
			//合作伙伴
			HaiPartner partner = haiPartnerMapper.selectByPrimaryKey(adminuser.getPartnerId());
			if(partner == null){
				rm.setMsg("非本后台的用户");
				return rm;
			}
			session.setAttribute(EConstants.SESSION_PARTNER_ID, partner.getPartnerId());
			session.setAttribute(EConstants.SESSION_PARTNER_NAME, partner.getPartnerName());
			session.setAttribute(EConstants.SESSION_PARTNER_THEME, partner.getTheme());
			//强行做法
			session.setAttribute(EConstants.SESSION_STORE_THEME, partner.getTheme());
			
		}else{
			if(adminuser.getStoreId() == null || adminuser.getStoreId() == 0){
				rm.setMsg("非本后台的用户");
				return rm;
			}
			//读取相应的商家
			EHaiStore store = haiStoreMapper.selectByPrimaryKey(adminuser.getStoreId());
			if(store == null){
				rm.setMsg("非本商家后台的用户");
				return rm;
			}
			
			session.setAttribute(EConstants.SESSION_STORE_ID, store.getStoreId());
			session.setAttribute(EConstants.SESSION_STORE_NAME, store.getStoreName());
			session.setAttribute(EConstants.SESSION_STORE_THEME, store.getTheme());
			
		}
				
		rm.setCode(1);
		rm.setMsg("登录成功");
		
		return rm;
	}
	

	@Override
	public ReturnObject<TpAdmin> login_submit(HttpServletRequest request,String username, String password, String verificationcode)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpAdmin> rm = new ReturnObject<TpAdmin>();
		rm.setCode(0);
		
		if(StringUtils.isEmpty(username)){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(StringUtils.isEmpty(password)){
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
		TpAdminExample example = new TpAdminExample();
		TpAdminExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(username);
		c.andPasswordEqualTo(password);
		System.out.println(username+"=="+password);
		
		List<TpAdmin> listAdmin = tpAdminMapper.selectByExample(example);
		
		if(listAdmin == null || listAdmin.size() == 0){
			rm.setMsg("用户名或密码不正确");
			return rm;
		}
		TpAdmin adminuser = listAdmin.get(0);
		if(adminuser.getSuppliersId() == null || adminuser.getSuppliersId()==0){
			rm.setMsg("未加盟本系统");
			return rm;
		}
		
		
		TpSuppliersExample supExample = new TpSuppliersExample();
		TpSuppliersExample.Criteria csup = supExample.createCriteria();
		csup.andSuppliersIdEqualTo(adminuser.getSuppliersId());
		List<TpSuppliers> listSup = tpSuppliersMapper.selectByExample(supExample);
		if(listSup == null || listSup.size() == 0){
			rm.setMsg("未加盟本系统");
			return rm;
		}
		TpSuppliers suppliers = listSup.get(0);
		
		session.setAttribute(EConstants.SESSION_ADMIN_ID, adminuser.getAdminId());
		session.setAttribute(EConstants.SESSION_ADMIN_NAME, adminuser.getUserName());
		session.setAttribute(EConstants.SESSION_SUPPLIERS_ID, adminuser.getSuppliersId());
		session.setAttribute("session_supplierssn", suppliers.getSuppliersSn());
		
		rm.setCode(1);
		rm.setMsg("登录成功");
		
		return rm;
	}

}
