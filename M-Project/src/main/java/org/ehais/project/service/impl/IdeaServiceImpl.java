package org.ehais.project.service.impl;

import java.util.Date;
import java.util.List;

import org.ehais.project.mapper.ProIdeaMapper;
import org.ehais.project.model.ProIdea;
import org.ehais.project.model.ProIdeaExample;
import org.ehais.project.model.ProIdeaWithBLOBs;
import org.ehais.project.service.IdeaService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ideaService")
public class IdeaServiceImpl implements IdeaService{


	@Autowired
	private ProIdeaMapper proIdeaMapper;
	
	public ReturnObject<ProIdea> InsertIdea(ProIdeaWithBLOBs idea)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProIdea> ro = new ReturnObject<ProIdea>();
		ro.setCode(0);
		if(idea.getIdeaTitle() == null || idea.getIdeaTitle().equals("")){
			ro.setMsg("请输入标题!");
			return ro;
		}
		Date date = new Date();
		idea.setCreateDate(date);
		idea.setUpdateDate(date);
		idea.setState(1);
		
		proIdeaMapper.insertSelective(idea);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProIdea> UpdateIdea(ProIdeaWithBLOBs idea)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProIdea> ro = new ReturnObject<ProIdea>();
		ro.setCode(0);
		if(idea.getIdeaTitle() == null || idea.getIdeaTitle().equals("")){
			ro.setMsg("请输入标题!");
			return ro;
		}
		
		
		Date date = new Date();
		idea.setUpdateDate(date);
		proIdeaMapper.updateByPrimaryKeySelective(idea);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProIdea> FindIdea(Integer userId, Integer ideaId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProIdea> ro = new ReturnObject<ProIdea>();
		ro.setCode(0);
		
		
//		ProIdeaExample example = new ProIdeaExample();
//		example.or().andUserIdEqualTo(userId);
//		example.or().andIdeaIdEqualTo(ideaId);
		ProIdeaWithBLOBs model = proIdeaMapper.getByExample(ideaId,userId);
		ro.setModel(model);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProIdea> ListIdea(Integer userId , Integer pageNo,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		if(pageNo == null || pageNo < 1)pageNo=1;
		if(pageSize == null )pageSize=1;
		
		ReturnObject<ProIdea> ro = new ReturnObject<ProIdea>();
		ro.setCode(0);
		
		List<ProIdea> ideaList = proIdeaMapper.ideaList(userId,(pageNo - 1) * pageSize, pageSize);
		ro.setRows(ideaList);
		
		
		ProIdeaExample example = new ProIdeaExample();
		int count = proIdeaMapper.countByExample(example);
//		System.out.println("count:"+count);
		
		
		ro.setTotal(count);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProIdea> RecoverIdea(ProIdea idea)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProIdea> ro = new ReturnObject<ProIdea>();
		ro.setCode(0);
		
		proIdeaMapper.Recover(idea.getIdeaId(), idea.getUserId());
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}
	
}
