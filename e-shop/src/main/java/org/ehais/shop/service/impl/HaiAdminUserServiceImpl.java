package org.ehais.shop.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.ThinkRoleAdminMapper;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.ThinkRole;
import org.ehais.epublic.model.ThinkRoleAdminExample;
import org.ehais.epublic.model.ThinkRoleAdminKey;
import org.ehais.epublic.model.ThinkRoleExample;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiAdminUserService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "user_name不能为空")//user_name
@NotBlank(message = "email不能为空")//email
@NotBlank(message = "password不能为空")//password

**/
/**

**/




@Service("haiAdminUserService")
public class HaiAdminUserServiceImpl  extends CommonServiceImpl implements HaiAdminUserService{
	
	@Autowired
	private EHaiAdminUserMapper haiAdminUserMapper;
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;
	@Autowired
	private ThinkRoleAdminMapper thinkRoleAdminMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	protected WpPublicMapper wpPublicMapper;
	

	public ReturnObject<EHaiAdminUser> adminuser_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiAdminUser> adminuser_list_json(HttpServletRequest request,EConditionObject condition,Long keySubId,String userName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("admin_id desc");
		
		if(StringUtils.isNotEmpty(userName))c.andUserNameLike("%"+userName+"%");
		List<EHaiAdminUser> list = haiAdminUserMapper.selectByExample(example);
		List<Long> adminIds = new ArrayList<Long>();
		for (EHaiAdminUser eHaiAdminUser : list) {
			eHaiAdminUser.setPassword(null);
			adminIds.add(eHaiAdminUser.getAdminId());
		}
		
		long total = haiAdminUserMapper.countByExample(example);

		Map<String, Object> map = new HashMap<String, Object>();
		if(adminIds.size()>0){
			ThinkRoleAdminExample rae = new ThinkRoleAdminExample();
			rae.createCriteria().andAdminIdIn(adminIds);
			List<ThinkRoleAdminKey> raList = thinkRoleAdminMapper.selectByExample(rae);
			map.put("role_admin", raList);
		}
		rm.setMap(map);

		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserWithBLOBs model = new EHaiAdminUserWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role_list", this.role_list(request));
		map.put("role", new ArrayList<String>());
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_insert_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model,String roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();

		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(model.getUserName());
		long count = haiAdminUserMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}

		if(StringUtils.isBlank(model.getPassword())) {
			model.setPassword(EncryptUtils.md5("123456"));
		} else {
			model.setPassword(EncryptUtils.md5(model.getPassword()));
		}

		int code = haiAdminUserMapper.insertSelective(model);
		
		this.saveRoleAdmin(request, model.getAdminId(), roleId);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_update(HttpServletRequest request,Long adminId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andAdminIdEqualTo(adminId);
		List<EHaiAdminUserWithBLOBs> list = haiAdminUserMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiAdminUserWithBLOBs model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role_list", this.role_list(request));
		map.put("role", this.role_admin(request, adminId));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_update_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model,String roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		
		c.andAdminIdEqualTo(model.getAdminId());
//		c.andStoreIdEqualTo(store_id);

		long count = haiAdminUserMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiAdminUserWithBLOBs bean = haiAdminUserMapper.selectByPrimaryKey(model.getAdminId());

bean.setUserName(model.getUserName());
bean.setEmail(model.getEmail());
//bean.setPassword(model.getPassword());
bean.setEcSalt(model.getEcSalt());
bean.setAddTime(model.getAddTime());
bean.setLastLogin(model.getLastLogin());
bean.setLastIp(model.getLastIp());
bean.setActionList(model.getActionList());
bean.setNavList(model.getNavList());
bean.setLangType(model.getLangType());
bean.setAgencyId(model.getAgencyId());
bean.setBusinessId(model.getBusinessId());
bean.setTodolist(model.getTodolist());
bean.setStoreId(model.getStoreId());
bean.setProjectFolder(model.getProjectFolder());
bean.setClassify(model.getClassify());
bean.setPartnerId(model.getPartnerId());


		Date date = new Date();

		int code = haiAdminUserMapper.updateByExampleSelective(bean, example);
		
		this.saveRoleAdmin(request, model.getAdminId(), roleId);
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_info(HttpServletRequest request,Long adminId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andAdminIdEqualTo(adminId);
		List<EHaiAdminUserWithBLOBs> list = haiAdminUserMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiAdminUserWithBLOBs model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role_list", this.role_list(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_find(HttpServletRequest request,Long adminId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andAdminIdEqualTo(adminId);
		List<EHaiAdminUserWithBLOBs> list = haiAdminUserMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiAdminUserWithBLOBs model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiAdminUser> adminuser_delete(HttpServletRequest request,Long adminId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andAdminIdEqualTo(adminId);

		long count = haiAdminUserMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiAdminUserMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,EHaiAdminUserWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "adminuser.xml",model,"hai_adminuser",optionMap);
		
		
		return bootStrapList;
	}


	private Map<String,String> role_list(HttpServletRequest request){
		Map<String,String> map = new TreeMap<String,String>();
		ThinkRoleExample re = new ThinkRoleExample();
		re.setOrderByClause("role_id asc");
		List<ThinkRole> troleList = thinkRoleMapper.selectByExample(re);
		for (ThinkRole thinkRole : troleList) {
			map.put(thinkRole.getRoleId().toString(), thinkRole.getName());
		}
		return map;
	}
	
	private List<String> role_admin(HttpServletRequest request,Long adminId){
		List<String> role = new ArrayList<String>();
		ThinkRoleAdminExample rae = new ThinkRoleAdminExample();
		rae.createCriteria().andAdminIdEqualTo(adminId);
		List<ThinkRoleAdminKey> roleList = thinkRoleAdminMapper.selectByExample(rae);
		
		for (ThinkRoleAdminKey thinkRoleAdminKey : roleList) {
			role.add(thinkRoleAdminKey.getRoleId().toString());
		}
		
		return role;
	}

	
	private void saveRoleAdmin(HttpServletRequest request,Long adminId,String roleId){
		ThinkRoleAdminExample rae = new ThinkRoleAdminExample();
		rae.createCriteria().andAdminIdEqualTo(adminId);
		thinkRoleAdminMapper.deleteByExample(rae);
		if(StringUtils.isNoneBlank(roleId)){
//			List<String> result = Arrays.asList(StringUtils.split(roleId,","));
			String[] roleids = StringUtils.split(roleId,",");
			for (String string : roleids) {
				ThinkRoleAdminKey ra = new ThinkRoleAdminKey();
				ra.setAdminId(adminId);
				ra.setRoleId(Integer.valueOf(string));
				thinkRoleAdminMapper.insert(ra);
			}
		}
	}




	
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_weixin_insert_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model,String roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();

		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(model.getUserName());
		long count = haiAdminUserMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的帐号1001");
			return rm;
		}
		
		EHaiStoreExample storeExp = new EHaiStoreExample();
		storeExp.createCriteria().andStoreNameEqualTo(model.getUserName());
		long sUser = eHaiStoreMapper.countByExample(storeExp);
		if(sUser > 0){rm.setMsg("存在相同的帐号1002");return rm;}
		
		//------------插入微信公众号配置public表
		WpPublicWithBLOBs wp = new WpPublicWithBLOBs();
		wp.setPublicName(model.getUserName());
		wpPublicMapper.insertSelective(wp);
		
		Integer addTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
		EHaiStore store = new EHaiStore();
		store.setStoreName(model.getUserName());
		store.setTheme(EAdminClassifyEnum.company);
		store.setAddTime(addTime);
		store.setPublicId(wp.getId());
		store.setState(true);
		eHaiStoreMapper.insert(store);
		
		model.setPassword(EncryptUtils.md5("123456"));
		model.setStoreId(store.getStoreId());

		int code = haiAdminUserMapper.insertSelective(model);
		
		this.saveRoleAdmin(request, model.getAdminId(), roleId);
		
		
		
		
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	
	
	@Override
	public ReturnObject<EHaiAdminUser> adminuser_modify_password(HttpServletRequest request, String old_password,
			String new_password, String confirmed_password) throws Exception {
		// TODO Auto-generated method stub
		Long adminId = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		
		if(!new_password.equals(confirmed_password)){
			rm.setMsg("确认密码不一致");
			return rm;
		}
		
		EHaiAdminUserWithBLOBs adminUser = haiAdminUserMapper.selectByPrimaryKey(adminId);
		if(adminUser == null){
			rm.setMsg("当前用户信息错误");
			return rm;
		}
		
		old_password = EncryptUtils.md5(old_password);
		if(!adminUser.getPassword().equals(old_password)){
			rm.setMsg("旧密码不正确");
			return rm;
		}
		
		new_password = EncryptUtils.md5(new_password);
		adminUser.setPassword(new_password);
		
		rm.setCode(1);
		rm.setMsg("密码更换成功");
		
		return rm;
	}




	
}











