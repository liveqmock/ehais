package org.ehais.epublic.service.impl;

import java.util.List;

import org.ehais.epublic.cache.EStoreCacheManager;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.service.EStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("eStoreService")
public class EStoreServiceImpl implements EStoreService{
	
	@Autowired
	private EHaiStoreMapper haiStoreMapper;

	@Override
	public EHaiStore getEStore(Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		EHaiStore store = EStoreCacheManager.getInstance().getEStore(store_id);
		if(store == null){
			store = haiStoreMapper.selectByPrimaryKey(store_id);
			if(store!=null)EStoreCacheManager.getInstance().putEStore(store_id, store);
		}
		return store;
	}

	@Override
	public void setEStore(Integer store_id, EHaiStore store) throws Exception {
		// TODO Auto-generated method stub
		if(store!=null)EStoreCacheManager.getInstance().putEStore(store_id, store);
	}

	@Override
	public List<EHaiStore> partnerStore(Integer partnerId) throws Exception {
		// TODO Auto-generated method stub
		EHaiStoreExample sexp = new EHaiStoreExample();
		sexp.createCriteria().andPartnerIdEqualTo(partnerId);
		
		List<EHaiStore> list = haiStoreMapper.selectByExample(sexp);
		
		return list;
	}
	
	
	@Override
	public List<EHaiStore> partnerStoreWithBLOBs(Integer partnerId) throws Exception {
		// TODO Auto-generated method stub
		EHaiStoreExample sexp = new EHaiStoreExample();
		sexp.createCriteria().andPartnerIdEqualTo(partnerId);
		
		List<EHaiStore> list = haiStoreMapper.selectByExampleWithBLOBs(sexp);
		
		return list;
	}

}
