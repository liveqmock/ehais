package org.ehais.project.service;

import org.ehais.project.model.ProProject;
import org.ehais.tools.ReturnObject;

public interface ProjectService {

	public ReturnObject<ProProject> InsertProject(ProProject Project) throws Exception;
	public ReturnObject<ProProject> UpdateProject(ProProject Project) throws Exception;
	public ReturnObject<ProProject> FindProject(Integer projectId) throws Exception;
	public ReturnObject<ProProject> ListProject(Integer pageNo,Integer pageSize) throws Exception;
	public ReturnObject<ProProject> RecoverProject(ProProject Project) throws Exception;
	
	
}
