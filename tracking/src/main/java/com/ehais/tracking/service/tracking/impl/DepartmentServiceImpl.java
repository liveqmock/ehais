package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.DepartmentDao;
import com.ehais.tracking.entity.Department;
import com.ehais.tracking.service.tracking.DepartmentService;





//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


//////////////////////////dao dao dao dao ///////////////////////////////

@Service("departmentService")
public class DepartmentServiceImpl  extends CommonServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentDao departmentDao;
	
	public ReturnObject<Department> department_list(HttpServletRequest request,Map<String,Object> map) throws Exception{
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		List<Department> list = departmentDao.selectAll(Department.class, map);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Department> department_list_json(HttpServletRequest request,Integer school_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(school_id != null)map.put("schoolId", school_id);
		ReturnObject<Department> rm = departmentDao.select(Department.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Department> department_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Department> rm = new ReturnObject<Department>();	
		Department model = new Department();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Department> department_insert_submit(HttpServletRequest request,Department model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		model.setSchoolId(user_id);
		departmentDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Department> department_update(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		
		Department model = departmentDao.findById(Department.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Department> department_update_submit(HttpServletRequest request,Department model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		departmentDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Department> department_find(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		
		
		Department model = departmentDao.findById(Department.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Department> department_delete(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Department> rm = new ReturnObject<Department>();
		departmentDao.delete(Department.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Department model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "name", "院系名称：", model.getName(), "请输入名称", "", "", null, 0));
		
		return bootStrapList;
	}
	
}


