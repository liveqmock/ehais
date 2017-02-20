package org.ehais.epublic.service;

import java.util.List;

import org.ehais.epublic.model.EHaiRegion;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface ERegionService extends CommonService{

	public ReturnObject<EHaiRegion> region_parent_list(Integer parent_id) throws Exception;
	public ReturnObject<EHaiRegion> region_id_list(String region_ids) throws Exception;
	public ReturnObject<EHaiRegion> region_parentid_list(String region_ids) throws Exception;
	public List<EHaiRegion> region_lists(List<Integer> region_list) throws Exception;
	public List<EHaiRegion> region_parent_lists(List<Integer> region_list) throws Exception;
	
}
