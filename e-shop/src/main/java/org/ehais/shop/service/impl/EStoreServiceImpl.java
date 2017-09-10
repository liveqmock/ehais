package org.ehais.shop.service.impl;

import org.ehais.cache.EStoreCacheManager;
import org.ehais.shop.mapper.HaiStoreMapper;
import org.ehais.shop.model.HaiStore;
import org.ehais.shop.service.EStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("eStoreService")
public class EStoreServiceImpl implements EStoreService{
	
	@Autowired
	private HaiStoreMapper haiStoreMapper;

	@Override
	public HaiStore getEStore(Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		HaiStore store = EStoreCacheManager.getInstance().getEStore(store_id);
		if(store == null){
			store = haiStoreMapper.selectByPrimaryKey(store_id);
			if(store!=null)EStoreCacheManager.getInstance().putEStore(store_id, store);
		}
		return store;
	}

}
