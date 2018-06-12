package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiEmployeeMapper;
import org.ehais.shop.model.HaiEmployee;
import org.ehais.shop.model.HaiEmployeeExample;
import org.ehais.shop.service.HaiEmployeeService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//employee_code
@NotBlank(message = "名称不能为空")//employee_name

**/
/**

**/




@Service("haiEmployeeService")
public class HaiEmployeeServiceImpl  extends CommonServiceImpl implements HaiEmployeeService{
	
	@Autowired
	private HaiEmployeeMapper haiEmployeeMapper;


	public ReturnObject<HaiEmployee> employee_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiEmployee> employee_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String employeeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(employeeName))c.andEmployeeNameLike("%"+employeeName+"%");
		List<HaiEmployee> list = haiEmployeeMapper.selectByExample(example);
		long total = haiEmployeeMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiEmployee> employee_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiEmployee model = new HaiEmployee();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiEmployee> employee_insert_submit(HttpServletRequest request,HaiEmployee model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		c.andEmployeeNameEqualTo(model.getEmployeeName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiEmployeeMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiEmployeeMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiEmployee> employee_update(HttpServletRequest request,Integer employeeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andEmployeeIdEqualTo(employeeId);
		long count = haiEmployeeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiEmployee model = haiEmployeeMapper.selectByPrimaryKey(employeeId);
**/
		HaiEmployee model = haiEmployeeMapper.get_hai_employee_info(employeeId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiEmployee> employee_update_submit(HttpServletRequest request,HaiEmployee model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andEmployeeIdEqualTo(model.getEmployeeId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiEmployeeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiEmployee bean = haiEmployeeMapper.selectByPrimaryKey(model.getEmployeeId());

bean.setEmployeeCode(model.getEmployeeCode());
bean.setEmployeeName(model.getEmployeeName());
bean.setPhone(model.getPhone());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiEmployeeMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiEmployee> employee_info(HttpServletRequest request,Integer employeeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		c.andEmployeeIdEqualTo(employeeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiEmployee> list = haiEmployeeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiEmployee model = list.get(0);
		**/

		HaiEmployee model = haiEmployeeMapper.get_hai_employee_info(employeeId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiEmployee> employee_find(HttpServletRequest request,Integer employeeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		c.andEmployeeIdEqualTo(employeeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiEmployee> list = haiEmployeeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiEmployee model = list.get(0);
		**/

		HaiEmployee model = haiEmployeeMapper.get_hai_employee_info(employeeId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiEmployee> employee_delete(HttpServletRequest request,Integer employeeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiEmployee> rm = new ReturnObject<HaiEmployee>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiEmployeeExample example = new HaiEmployeeExample();
		HaiEmployeeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andEmployeeIdEqualTo(employeeId);

		long count = haiEmployeeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiEmployeeMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiEmployee model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "employee.xml",model,"hai_employee",optionMap);
		
		
		return bootStrapList;
	}













	
}











