package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.model.ThinkRole;
import org.ehais.epublic.model.ThinkRoleExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.ThinkRoleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "角色名称不能为空")//name
@NotBlank(message = "上级编号不能为空")//parent_id

**/
/**

**/




@Service("thinkRoleService")
public class ThinkRoleServiceImpl  extends CommonServiceImpl implements ThinkRoleService{
	
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;


	public ReturnObject<ThinkRole> role_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<ThinkRole> role_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String name) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_time desc");
		
		if(StringUtils.isNotEmpty(name))c.andNameLike("%"+name+"%");
		List<ThinkRole> list = thinkRoleMapper.selectByExample(example);
		long total = thinkRoleMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<ThinkRole> role_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRole model = new ThinkRole();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<ThinkRole> role_insert_submit(HttpServletRequest request,ThinkRole model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		Date date = new Date();
		model.setCreateTime(date);
		model.setUpdateTime(date);

		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		c.andNameEqualTo(model.getName());
		long count = thinkRoleMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = thinkRoleMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<ThinkRole> role_update(HttpServletRequest request,Integer roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(roleId);
		List<ThinkRole> list = thinkRoleMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		ThinkRole model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<ThinkRole> role_update_submit(HttpServletRequest request,ThinkRole model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		
		c.andRoleIdEqualTo(model.getRoleId());

		long count = thinkRoleMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		ThinkRole bean = thinkRoleMapper.selectByPrimaryKey(model.getRoleId());

bean.setName(model.getName());
bean.setParentId(model.getParentId());
bean.setStatus(model.getStatus()==null?false:true);
bean.setRemark(model.getRemark());


		Date date = new Date();
		model.setUpdateTime(date);

		int code = thinkRoleMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<ThinkRole> role_info(HttpServletRequest request,Integer roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(roleId);
		List<ThinkRole> list = thinkRoleMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		ThinkRole model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<ThinkRole> role_find(HttpServletRequest request,Integer roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(roleId);
		List<ThinkRole> list = thinkRoleMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		ThinkRole model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<ThinkRole> role_delete(HttpServletRequest request,Integer roleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ThinkRole> rm = new ReturnObject<ThinkRole>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		ThinkRoleExample example = new ThinkRoleExample();
		ThinkRoleExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(roleId);

		long count = thinkRoleMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = thinkRoleMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,ThinkRole model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "role.xml",model,"think_role",optionMap);
		
		
		return bootStrapList;
	}













	
}











