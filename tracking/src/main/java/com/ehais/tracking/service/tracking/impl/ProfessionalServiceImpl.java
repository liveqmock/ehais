package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.ProfessionalDao;
import com.ehais.tracking.entity.Professional;
import com.ehais.tracking.service.tracking.ProfessionalService;





//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


//////////////////////////dao dao dao dao ///////////////////////////////

@Service("professionalService")
public class ProfessionalServiceImpl  extends CommonServiceImpl implements ProfessionalService{
	
	@Autowired
	private ProfessionalDao professionalDao;
	
	public ReturnObject<Professional> professional_list(HttpServletRequest request,Map<String, Object> map) throws Exception{
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
		List<Professional> list = professionalDao.selectAll(Professional.class, map);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Professional> professional_list_json(HttpServletRequest request,Integer department_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(department_id != null)map.put("departmentId", department_id);
		ReturnObject<Professional> rm = professionalDao.select(Professional.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Professional> professional_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Professional> rm = new ReturnObject<Professional>();	
		Professional model = new Professional();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Professional> professional_insert_submit(HttpServletRequest request,Professional model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
//		model.setSchoolId(store_id);
		professionalDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Professional> professional_update(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
		
		Professional model = professionalDao.findById(Professional.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Professional> professional_update_submit(HttpServletRequest request,Professional model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
		professionalDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Professional> professional_find(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
		
		
		Professional model = professionalDao.findById(Professional.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Professional> professional_delete(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		ReturnObject<Professional> rm = new ReturnObject<Professional>();
		professionalDao.delete(Professional.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Professional model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}


