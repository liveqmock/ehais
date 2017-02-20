package org.ehais.project.service;

import org.ehais.project.model.HaiUsers;
import org.ehais.tools.ReturnObject;

public interface ProCommonService {

	/**
	 * 找出参与此项目的开发者
	 * @param proId
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<HaiUsers> userProjectList(Integer proId)throws Exception;
	
}
