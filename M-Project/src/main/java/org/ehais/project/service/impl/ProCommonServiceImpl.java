package org.ehais.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.mapper.ProTaskMapper;
import org.ehais.project.mapper.ProUserProjectMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.HaiUsersExample;
import org.ehais.project.model.ProTask;
import org.ehais.project.model.ProTaskExample;
import org.ehais.project.model.ProUserProject;
import org.ehais.project.model.ProUserProjectExample;
import org.ehais.project.service.ProCommonService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ProCommonServiceImpl extends CommonServiceImpl implements ProCommonService{

	@Autowired
	private HaiUsersMapper haiUserMapper;
	@Autowired
	private ProUserProjectMapper proUserProjectMapper;
	

	@Autowired
	private ProTaskMapper proTaskMapper;
	
	
	public ReturnObject<HaiUsers> userProjectList(Integer proId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUsers> ro = new ReturnObject<HaiUsers>();
		//找出当前项目的开发者
		ProUserProjectExample example = new ProUserProjectExample();
		example.or().andProIdEqualTo(proId);
		List<ProUserProject> userProList = proUserProjectMapper.selectByExample(example);
		List<Integer> values = new ArrayList<Integer>();
		for (ProUserProject proUserProject : userProList) {
			values.add(proUserProject.getUserId());
		}
		if(values.size() == 0) return null;
		//找出这些开发者的信息
		HaiUsersExample user_example = new HaiUsersExample();
		user_example.or().andUserIdIn(values);
		List<HaiUsers> userList = haiUserMapper.selectByExample(user_example);
		ro.setRows(userList);
		return ro;
	}
	
	protected List<ProTask> taskList(Integer proId){
		ProTaskExample example = new ProTaskExample();
		ProTaskExample.Criteria c = example.createCriteria();
		c.andProIdEqualTo(proId);
		example.setOrderByClause("plan_start_date asc");
		return proTaskMapper.selectByExample(example);
		
	}

	protected Map<String, String> taskMap(Integer proId){
		Map<String, String> map = new HashMap<String, String>();
		
		List<ProTask> list = this.taskList(proId);
		for (ProTask proTask : list) {
			map.put(proTask.getTaskId().toString(), proTask.getTaskTitle());
		}
		
		return map;
	}
	
	protected Map<String,String> getDPUser(Integer proId){
		Map<String, String> map = new HashMap<String, String>();
		ReturnObject<HaiUsers> rm = null;
		try {
			rm = this.userProjectList(proId);
			List<HaiUsers> list = rm.getRows();
			for (HaiUsers haiUsers : list) {
				map.put(haiUsers.getUserId().toString(), haiUsers.getRealname()!=null?haiUsers.getRealname():haiUsers.getUserName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
	
}
