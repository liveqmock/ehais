package org.ehais.project.service.impl;

import java.util.List;

import org.ehais.project.mapper.ProProjectMapper;
import org.ehais.project.model.ProProject;
import org.ehais.project.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("indexService")
public class IndexServiceImpl implements IndexService{

	@Autowired
	private ProProjectMapper proProjectMapper;
	
	public void IndexDate() {
		// TODO Auto-generated method stub
		List<ProProject> projectList = proProjectMapper.selectByExample(null);
		
	}

}
