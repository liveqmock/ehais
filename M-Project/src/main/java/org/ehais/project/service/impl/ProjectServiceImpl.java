package org.ehais.project.service.impl;

import java.util.List;

import org.ehais.project.mapper.ProProjectMapper;
import org.ehais.project.model.ProProject;
import org.ehais.project.model.ProProjectExample;
import org.ehais.project.service.ProjectService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProProjectMapper proProjectMapper;
	
	public ReturnObject<ProProject> InsertProject(ProProject Project)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		ProProjectExample example = new ProProjectExample();
		ProProjectExample.Criteria criteria = example.createCriteria();
		criteria.andProNameEqualTo(Project.getProName());
		List<ProProject> list = proProjectMapper.selectByExample(example);
		if(list.size() > 0){
			ro.setMsg("项目名称已存在");
			return ro;
		}
		proProjectMapper.insert(Project);
		ro.setCode(1);
		ro.setMsg("保存成功");
		return ro;
	}

	public ReturnObject<ProProject> UpdateProject(ProProject Project)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		ProProject project2 = proProjectMapper.selectByPrimaryKey(Project.getProId());
		if(project2 == null){
			ro.setMsg("项目名称不存在");
			return ro;
		}
		proProjectMapper.updateByPrimaryKey(Project);
		ro.setCode(1);
		ro.setMsg("保存成功");
		return ro;
	}

	public ReturnObject<ProProject> FindProject(Integer projectId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		ProProject project2 = proProjectMapper.selectByPrimaryKey(projectId);
		ro.setModel(project2);
		ro.setCode(1);
		ro.setMsg("保存成功");
		return ro;
	}

	public ReturnObject<ProProject> ListProject(Integer pageNo, Integer pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		List<ProProject> list = proProjectMapper.listProject((pageNo - 1 ) * pageSize, pageSize);
		int total = proProjectMapper.countByExample(null);
		ro.setRows(list);
		ro.setTotal(total);
		
		ro.setCode(1);
		return ro;
	}

	public ReturnObject<ProProject> RecoverProject(ProProject Project)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProProject> ro = new ReturnObject<ProProject>();
		ro.setCode(0);
		proProjectMapper.deleteByPrimaryKey(Project.getProId());
		
		ro.setCode(1);
		ro.setMsg("删除成功");
		return ro;
	}


	
}
