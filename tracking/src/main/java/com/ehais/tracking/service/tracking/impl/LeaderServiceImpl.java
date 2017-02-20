package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.LeaderDao;
import com.ehais.tracking.entity.Leader;
import com.ehais.tracking.service.tracking.LeaderService;
import com.ehais.tracking.entity.Leader;




@Service("leaderService")
public class LeaderServiceImpl  extends CommonServiceImpl implements LeaderService{
	
	@Autowired
	private LeaderDao leaderDao;
	
	public ReturnObject<Leader> leader_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Leader> leader_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		ReturnObject<Leader> rm = leaderDao.select(Leader.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Leader> leader_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();	
		Leader model = new Leader();
//		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setBootStrapList(this.BootStrapXml(request, "leader.xml", model, "leader", null));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Leader> leader_insert_submit(HttpServletRequest request,Leader model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		
		if(model.getPassword()!=null || !model.getPassword().equals("")){
			model.setPassword(EncryptUtils.md5(model.getPassword()));
		}
		
		
		leaderDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Leader> leader_update(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		
		Leader model = leaderDao.findById(Leader.class, "leaderId", key);
//		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setBootStrapList(this.BootStrapXml(request, "leader.xml", model, "leader", null));
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Leader> leader_update_submit(HttpServletRequest request,Leader model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		if(model.getPassword()!=null || !model.getPassword().equals("")){
			model.setPassword(EncryptUtils.md5(model.getPassword()));
		}
		leaderDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Leader> leader_find(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		
		
		Leader model = leaderDao.findById(Leader.class, "leaderId", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Leader> leader_delete(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		leaderDao.delete(Leader.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Leader model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		

bootStrapList.add(new BootStrapModel("hidden", "leaderId", "", model.getLeaderId(), "请输入", "", "", null, 0));
bootStrapList.add(new BootStrapModel("text", "leaderName", "领导名称", model.getLeaderName(), "请输入", "", "", null, 0));
bootStrapList.add(new BootStrapModel("text", "mobile", "手机号码", model.getMobile(), "请输入", "", "", null, 0));
bootStrapList.add(new BootStrapModel("text", "email", "邮箱", model.getEmail(), "请输入", "", "", null, 0));
bootStrapList.add(new BootStrapModel("text", "address", "地址", model.getAddress(), "请输入", "", "", null, 0));

		return bootStrapList;
	}

	@Override
	public ReturnObject<Leader> leader_login(HttpServletRequest request,String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Leader> rm = new ReturnObject<Leader>();
		rm.setCode(0);
		if(username == null || username.equals("") || password == null || password.equals("")){
			rm.setMsg("用户名与密码都不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		System.out.println(username + "     " + password);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("leaderName", username);
		map.put("password", password);
		List<Leader> aList = leaderDao.selectAll(Leader.class, 1, 1, map, null, null);
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
