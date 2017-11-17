package org.ehais.epublic.service;

import org.ehais.epublic.model.HaiPartner;

public interface EPartnerService {

	public HaiPartner getEPartner(Integer partner_id) throws Exception;
	
	public void setEPartner(Integer partner_id,HaiPartner partner) throws Exception;
	
	
}
