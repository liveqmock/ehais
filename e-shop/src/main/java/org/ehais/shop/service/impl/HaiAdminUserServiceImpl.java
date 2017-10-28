package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.ThinkRole;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiAdminUserService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
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
		long total = haiAdminUserMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
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
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_insert_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model)
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


		int code = haiAdminUserMapper.insertSelective(model);
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
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_update_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdminUserWithBLOBs> rm = new ReturnObject<EHaiAdminUserWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiAdminUserExample example = new EHaiAdminUserExample();
		EHaiAdminUserExample.Criteria c = example.createCriteria();
		
		c.andAdminIdEqualTo(model.getAdminId());
		c.andStoreIdEqualTo(store_id);

		long count = haiAdminUserMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiAdminUserWithBLOBs bean = haiAdminUserMapper.selectByPrimaryKey(model.getAdminId());

bean.setUserName(model.getUserName());
bean.setEmail(model.getEmail());
bean.setPassword(model.getPassword());
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
		
		List<ThinkRole> troleList = thinkRoleMapper.selectByExample(null);
		for (ThinkRole thinkRole : troleList) {
			map.put(thinkRole.getRoleId().toString(), thinkRole.getName());
		}
		return map;
	}










	
}










