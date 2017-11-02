package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.HaiPartnerMapper;
import org.ehais.epublic.mapper.ThinkRoleAdminMapper;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.model.ThinkRoleAdminExample;
import org.ehais.epublic.model.ThinkRoleAdminKey;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("eHaiAdminUserService")
public class EHaiAdminUserServiceImpl  extends CommonServiceImpl implements EHaiAdminUserService{
	
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;
	@Autowired
	private ThinkRoleAdminMapper thinkRoleAdminMapper;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;
	
//	public ReturnObject<EHaiAdminUser> EHaiAdminUser_list(HttpServletRequest request) throws Exception{
//		
//		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		
//		rm.setCode(1);
//		return rm;
//	}

//	public ReturnObject<EHaiAdminUser> EHaiAdminUser_list_json(HttpServletRequest request,
//			Integer page, Integer len) throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		Integer start = (page - 1 ) * len;
//		
//		EHaiAdminUserExample example = new EHaiAdminUserExample();
//		EHaiAdminUserExample.Criteria c = example.createCriteria();
//		
//		example.setLimitStart((page - 1) * len );
//		example.setLimitEnd(len);
//		
//		List<EHaiAdminUser> list = eHaiAdminUserMapper.hai_admin_user_list_by_example(example);
//		Long total = eHaiAdminUserMapper.countByExample(example);
//		rm.setCode(1);
//		rm.setRows(list);
//		rm.setTotal(total);
//		
//		
//		return rm;
//	}

//	public ReturnObject<EHaiAdminUserWithBLOBs> EHaiAdminUser_insert(HttpServletRequest request)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();	
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		EHaiAdminUserWithBLOBs model = new EHaiAdminUserWithBLOBs();
//		rm.setBootStrapList(this.formatBootStrapList(model));
//		rm.setCode(1);
//		return rm;
//	}
	
//	public ReturnObject<EHaiAdminUserWithBLOBs> EHaiAdminUser_insert_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		model.setStoreId(store_id);
//		int code = eHaiAdminUserMapper.insertSelective(model);
//		rm.setCode(code);
//		rm.setMsg("添加成功");
//		return rm;
//	}

//	public ReturnObject<EHaiAdminUserWithBLOBs> EHaiAdminUser_update(HttpServletRequest request,Integer key)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		EHaiAdminUserWithBLOBs model = eHaiAdminUserMapper.selectByPrimaryKey(Short.valueOf(key+""));
//		rm.setBootStrapList(this.formatBootStrapList(model));
//		
//		rm.setCode(1);
//		rm.setModel(model);
//		return rm;
//	}
	
//	public ReturnObject<EHaiAdminUserWithBLOBs> EHaiAdminUser_update_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		EHaiAdminUserExample example = new EHaiAdminUserExample();
//		EHaiAdminUserExample.Criteria c = example.createCriteria();
//		
//		c.andStoreIdEqualTo(store_id);
//		c.andAdminIdEqualTo(model.getAdminId());
//		int code = eHaiAdminUserMapper.updateByExampleSelective(model, example);
//		rm.setCode(code);
//		rm.setMsg("编辑成功");
//		return rm;
//	}

//	public ReturnObject<EHaiAdminUserWithBLOBs> EHaiAdminUser_find(HttpServletRequest request,Integer key)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		
//		EHaiAdminUserWithBLOBs model = eHaiAdminUserMapper.selectByPrimaryKey(Short.valueOf(key+""));
//		rm.setBootStrapList(this.formatBootStrapList(model));
//		
//		rm.setCode(1);
//		rm.setModel(model);
//		return rm;
//	}

//	public ReturnObject<EHaiAdminUser> EHaiAdminUser_delete(HttpServletRequest request,Integer key)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		EHaiAdminUserExample example = new EHaiAdminUserExample();
//		EHaiAdminUserExample.Criteria c = example.createCriteria();
//		
//		c.andAdminIdEqualTo(Long.valueOf(key));
//		int code = eHaiAdminUserMapper.deleteByExample(example);
//		rm.setCode(code);
//		rm.setMsg("删除成功");
//		return rm;
//	}
	
//	private List<BootStrapModel> formatBootStrapList(EHaiAdminUserWithBLOBs model){
//		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
//		
//		
//		return bootStrapList;
//	}

	public ReturnObject<EHaiAdminUser> login_admin(HttpServletRequest request,
			String username, String password, String verificationcode) throws Exception {
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
		
		HttpSession session = request.getSession(true);
		String verCode = (String)session.getAttribute("verCode");
		if(verCode == null || !verCode.toLowerCase().equals(verificationcode.toLowerCase())){
			rm.setMsg("验证码错误");
			return rm;
		}
		
		
		password = EncryptUtils.md5(password);
		
		EHaiAdminUser adminuser = eHaiAdminUserMapper.login_admin(username, password);
		if(adminuser == null){
			System.out.println(password);
			rm.setMsg("用户名或密码不正确");
			return rm;
		}
		
		
		request.getSession().setAttribute(EConstants.SESSION_ADMIN_ID, Integer.valueOf(adminuser.getAdminId().intValue()));
		//以后再扩展同一个商家有多个管理员
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, Integer.valueOf(adminuser.getAdminId().intValue()));
		request.getSession().setAttribute(EConstants.SESSION_ADMIN_NAME, username);
		request.getSession().setAttribute(EConstants.SESSION_ADMIN_PROJECT_FOLDER, adminuser.getProjectFolder());
		//防止应用混合登录
		request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
		
//		List<ThinkRoleAdmin> role = thinkRoleMapper.think_role_admin(adminuser.getAdminId());
		
		ThinkRoleAdminExample exp = new ThinkRoleAdminExample();
		exp.createCriteria().andAdminIdEqualTo(adminuser.getAdminId());
		List<ThinkRoleAdminKey> role = thinkRoleAdminMapper.selectByExample(exp);
		
		if(role==null || role.size() == 0){
			rm.setMsg("角色权限未分配，请联系管理员10045");
			return rm;
		}
		
		List<Integer> roleids = new ArrayList<Integer>();
		for (ThinkRoleAdminKey thinkRoleAdmin : role) {
			roleids.add(thinkRoleAdmin.getRoleId());
		}
		
		String str = StringUtils.join(roleids.toArray(), ",");  
		request.getSession().setAttribute(EConstants.SESSION_ROLE_ID_ARRAY, str);
		
//		if(role != null && role.getRoleCode() != null && !role.getRoleCode().equals("")){
//			request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE, role.getRoleCode());
//		}else if(adminuser.getProjectFolder() != null && !adminuser.getProjectFolder().equals("")){
//			request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE, adminuser.getProjectFolder());
//		}else{
//			rm.setMsg("角色权限未分配，请联系管理员10045");
//			return rm;
//		}
		
		rm.setCode(1);
		rm.setMsg("登录成功");
		
		return rm;
	}

	public ReturnObject<EHaiAdminUser> logout_admin(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		
		request.getSession().removeAttribute(EConstants.SESSION_ADMIN_ID);
		request.getSession().removeAttribute(EConstants.SESSION_STORE_ID);
		request.getSession().removeAttribute(EConstants.SESSION_ADMIN_NAME);
		request.getSession().removeAttribute(EConstants.SESSION_ROLE_TYPE);
		
		rm.setCode(1);
		rm.setMsg("退出成功");
		
		return rm;
	}
	
	
	
	//商家的统一
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
		session.setAttribute(EConstants.SESSION_ADMIN_CLASSIFY, adminuser.getClassify());
		session.setAttribute(EConstants.SESSION_ADMIN_PROJECT_FOLDER, adminuser.getProjectFolder());
		
//		ThinkRole role = thinkRoleMapper.think_role_admin(adminuser.getAdminId().intValue());
//		if(role != null && role.getRoleCode() != null && !role.getRoleCode().equals("")){
//			request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE, role.getRoleCode());
//		}else if(adminuser.getProjectFolder() != null && !adminuser.getProjectFolder().equals("")){
//			request.getSession().setAttribute(EConstants.SESSION_ROLE_TYPE, adminuser.getProjectFolder());
//		}
		/*///////////////////权限判断
		ThinkRoleAdminExample exp = new ThinkRoleAdminExample();
		exp.createCriteria().andAdminIdEqualTo(adminuser.getAdminId());
		List<ThinkRoleAdminKey> role = thinkRoleAdminMapper.selectByExample(exp);
		
		if(role==null || role.size() == 0){
			rm.setMsg("角色权限未分配，请联系管理员10045");
			return rm;
		}
		
		List<Integer> roleids = new ArrayList<Integer>();
		for (ThinkRoleAdminKey thinkRoleAdmin : role) {
			roleids.add(thinkRoleAdmin.getRoleId());
		}
		
		String str = StringUtils.join(roleids.toArray(), ",");  
		request.getSession().setAttribute(EConstants.SESSION_ROLE_ID_ARRAY, str);
		*/
		
		
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
				rm.setMsg("非本系统后台的用户");
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
	
}











