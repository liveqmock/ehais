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

import com.ehais.tracking.dao.HaiAdminUserDao;
import com.ehais.tracking.entity.HaiAdminUser;
import com.ehais.tracking.service.tracking.HaiAdminUserService;




@Service("haiAdminUserService")
public class HaiAdminUserServiceImpl  extends CommonServiceImpl implements HaiAdminUserService{
	
	@Autowired
	private HaiAdminUserDao haiAdminUserDao;
	
	public ReturnObject<HaiAdminUser> admin_user_list() throws Exception{
		
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAdminUser> admin_user_list_json(
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		ReturnObject<HaiAdminUser> rm = haiAdminUserDao.select(HaiAdminUser.class, page, len, map);
		
		return rm;
	}

	public ReturnObject<HaiAdminUser> admin_user_insert()
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();	
		HaiAdminUser model = new HaiAdminUser();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiAdminUser> admin_user_insert_submit(HaiAdminUser model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		haiAdminUserDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAdminUser> admin_user_update(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		
		HaiAdminUser model = haiAdminUserDao.findById(HaiAdminUser.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiAdminUser> admin_user_update_submit(HaiAdminUser model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		haiAdminUserDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAdminUser> admin_user_find(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		
		
		HaiAdminUser model = haiAdminUserDao.findById(HaiAdminUser.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiAdminUser> admin_user_delete(Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		haiAdminUserDao.delete(HaiAdminUser.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiAdminUser model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiAdminUser> admin_login(String username,
			String password) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdminUser> rm = new ReturnObject<HaiAdminUser>();
		rm.setCode(0);
		if(username == null || username.equals("") || password == null || password.equals("")){
			rm.setMsg("用户名与密码都不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		System.out.println(username + "     " + password);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userName", username);
		map.put("password", password);
		List<HaiAdminUser> aList = haiAdminUserDao.selectAll(HaiAdminUser.class, 1, 1, map, null, null);
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