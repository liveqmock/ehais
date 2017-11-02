package org.ehais.epublic.service;

import java.util.List;

import org.ehais.epublic.model.EHaiStore;

public interface EStoreService {

	public EHaiStore getEStore(Integer store_id) throws Exception;
	
	public void setEStore(Integer store_id,EHaiStore store) throws Exception;
	
	public List<EHaiStore> partnerStore(Integer partnerId) throws Exception;
	
	public List<EHaiStore> partnerStoreWithBLOBs(Integer partnerId) throws Exception;
	
	
}
