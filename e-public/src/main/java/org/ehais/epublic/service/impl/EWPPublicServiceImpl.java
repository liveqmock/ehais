package org.ehais.epublic.service.impl;

import java.util.List;

import org.ehais.epublic.cache.WXPublicCacheManager;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.WpPublicExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eWPPublicService")
public class EWPPublicServiceImpl implements EWPPublicService {
	@Autowired
	protected WpPublicMapper wpPublicMapper;
	@Autowired
	private EStoreService eStoreService;
	@Override
	public WpPublicWithBLOBs getWpPublic(Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		EHaiStore store = eStoreService.getEStore(store_id);
		WpPublicWithBLOBs wpPublic = WXPublicCacheManager.getInstance().getWXPublic(store.getPublicId());
		if(wpPublic == null){
			WpPublicExample example = new WpPublicExample();
			example.createCriteria().andIdEqualTo(store.getPublicId());
			List<WpPublicWithBLOBs> list = wpPublicMapper.selectByExampleWithBLOBs(example);
			if(list != null && list.size() > 0){
				wpPublic = list.get(0);
				WXPublicCacheManager.getInstance().putWXPublic(store_id, wpPublic);
			}
			
		}
		return wpPublic;
	}
	@Override
	public void setWpPublic(Integer store_id, WpPublicWithBLOBs wpPublic) throws Exception {
		// TODO Auto-generated method stub
		WXPublicCacheManager.getInstance().putWXPublic(store_id, wpPublic);
	}
}
