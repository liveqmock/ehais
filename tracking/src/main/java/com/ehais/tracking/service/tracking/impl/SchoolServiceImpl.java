package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.SchoolDao;
import com.ehais.tracking.entity.School;
import com.ehais.tracking.service.tracking.SchoolService;
import com.ehais.tracking.entity.School;



@Service("schoolService")
public class SchoolServiceImpl  extends CommonServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolDao schoolDao;
	
	public ReturnObject<School> school_list(Map<String,Object> map) throws Exception{
		
		ReturnObject<School> rm = new ReturnObject<School>();
		
		List<School> list = schoolDao.selectAll(School.class, map);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<School> school_list_json(Map<String,Object> map,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

//		Map<String,Object> map = new HashMap<String,Object>();
		ReturnObject<School> rm = schoolDao.select(School.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<School> school_insert()
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();	
		School model = new School();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<School> school_insert_submit(School model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		schoolDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<School> school_update(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		
		School model = schoolDao.findById(School.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<School> school_update_submit(School model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		schoolDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<School> school_find(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		
		
		School model = schoolDao.findById(School.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<School> school_delete(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		schoolDao.delete(School.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(School model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "name", "学校名称", model.getName(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "createTime", "", model.getCreateTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "usersId", "", model.getUsersId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "logo", "", model.getLogo(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "mobile", "手机号码", model.getMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "email", "邮箱", model.getEmail(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "cityId", "", model.getCityId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "provinceId", "", model.getProvinceId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "schoolNumber", "学校编号", model.getSchoolNumber(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<School> school_login(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<School> rm = new ReturnObject<School>();
		rm.setCode(0);
		if(username == null || username.equals("") || password == null || password.equals("")){
			rm.setMsg("用户名与密码都不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		System.out.println(username + "     " + password);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", username);
		map.put("password", password);
		List<School> aList = schoolDao.selectAll(School.class, 1, 1, map, null, null);
		if(aList == null || aList.size() == 0){
			rm.setMsg("用户名与密码错误");
			return rm;
		}
		rm.setMsg("登录成功!");
		rm.setModel(aList.get(0));
		rm.setCode(1);
		return rm;
	}
	
}