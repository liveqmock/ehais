package org.ehais.epublic.service.impl;

import org.ehais.epublic.cache.EPartnerCacheManager;
import org.ehais.epublic.mapper.HaiPartnerMapper;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.service.EPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ePartnerService")
public class EPartnerServiceImpl implements EPartnerService {

	@Autowired
	private HaiPartnerMapper haiPartnerMapper;
	
	
	@Override
	public HaiPartner getEPartner(Integer partner_id) throws Exception {
		// TODO Auto-generated method stub
		HaiPartner partner = EPartnerCacheManager.getInstance().getEPartner(partner_id);
		if(partner == null){
			partner = haiPartnerMapper.selectByPrimaryKey(partner_id);
			if(partner != null) EPartnerCacheManager.getInstance().putEPartner(partner_id, partner);
		}
		return partner;
	}

	@Override
	public void setEPartner(Integer partner_id, HaiPartner partner) throws Exception {
		// TODO Auto-generated method stub
		if(partner != null) EPartnerCacheManager.getInstance().putEPartner(partner_id, partner);
	}

}
