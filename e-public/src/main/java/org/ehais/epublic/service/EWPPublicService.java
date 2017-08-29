package org.ehais.epublic.service;

import org.ehais.epublic.model.WpPublicWithBLOBs;

public interface EWPPublicService {

	public WpPublicWithBLOBs getWpPublic(Integer store_id) throws Exception;
	
}
