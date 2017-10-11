package org.ehais.epublic.service;

import org.ehais.epublic.model.EHaiStore;

public interface EStoreService {

	public EHaiStore getEStore(Integer store_id) throws Exception;
	
	public void setEStore(Integer store_id,EHaiStore store) throws Exception;
	
	
}
