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

import com.ehais.tracking.dao.GradesDao;
import com.ehais.tracking.entity.Grades;
import com.ehais.tracking.service.tracking.GradesService;





//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


//////////////////////////dao dao dao dao ///////////////////////////////

@Service("gradesService")
public class GradesServiceImpl  extends CommonServiceImpl implements GradesService{
	
	@Autowired
	private GradesDao gradesDao;
	
	public ReturnObject<Grades> grades_list(HttpServletRequest request,Map<String, Object> map) throws Exception{
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
		List<Grades> list = gradesDao.selectAll(Grades.class, map);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Grades> grades_list_json(HttpServletRequest request,Integer professional_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(professional_id != null)map.put("professionalId", professional_id);
		ReturnObject<Grades> rm = gradesDao.select(Grades.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Grades> grades_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Grades> rm = new ReturnObject<Grades>();	
		Grades model = new Grades();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Grades> grades_insert_submit(HttpServletRequest request,Grades model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
//		model.setSchoolId(store_id);
		gradesDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Grades> grades_update(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
		
		Grades model = gradesDao.findById(Grades.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Grades> grades_update_submit(HttpServletRequest request,Grades model)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
		gradesDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Grades> grades_find(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
		
		
		Grades model = gradesDao.findById(Grades.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Grades> grades_delete(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		ReturnObject<Grades> rm = new ReturnObject<Grades>();
		gradesDao.delete(Grades.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Grades model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}


