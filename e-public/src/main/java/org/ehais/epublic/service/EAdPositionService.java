package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiAdPosition;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;



public interface EAdPositionService extends CommonService{
	public ReturnObject<EHaiAdPosition> adposition_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_insert_submit(HttpServletRequest request,EHaiAdPosition model) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_update(HttpServletRequest request,Integer positionId) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_update_submit(HttpServletRequest request,EHaiAdPosition model) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_find(HttpServletRequest request,Integer positionId) throws Exception;
	public ReturnObject<EHaiAdPosition> adposition_delete(HttpServletRequest request,Integer positionId) throws Exception;
	
	

}

