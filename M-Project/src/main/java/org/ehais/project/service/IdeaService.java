package org.ehais.project.service;

import org.ehais.project.model.ProIdea;
import org.ehais.project.model.ProIdeaWithBLOBs;
import org.ehais.tools.ReturnObject;

public interface IdeaService {

	public ReturnObject<ProIdea> InsertIdea(ProIdeaWithBLOBs idea) throws Exception;
	public ReturnObject<ProIdea> UpdateIdea(ProIdeaWithBLOBs idea) throws Exception;
	public ReturnObject<ProIdea> FindIdea(Integer userId ,Integer ideaId) throws Exception;
	public ReturnObject<ProIdea> ListIdea(Integer userId ,Integer pageNo,Integer pageSize) throws Exception;
	public ReturnObject<ProIdea> RecoverIdea(ProIdea idea) throws Exception;
	
	
}
