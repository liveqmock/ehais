package org.ehais.epublic.service.impl;

import java.util.List;

import org.ehais.epublic.cache.WXPublicCacheManager;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.WpPublicExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eWPPublicService")
public class EWPPublicServiceImpl implements EWPPublicService {
	@Autowired
	protected WpPublicMapper wpPublicMapper;

	@Override
	public WpPublicWithBLOBs getWpPublic(Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		WpPublicWithBLOBs wpPublic = WXPublicCacheManager.getInstance().getWXPublic(store_id);
		if(wpPublic == null){
			WpPublicExample example = new WpPublicExample();
			example.createCriteria().andStoreIdEqualTo(store_id);
			List<WpPublicWithBLOBs> list = wpPublicMapper.selectByExampleWithBLOBs(example);
			if(list != null && list.size() > 0){
				wpPublic = list.get(0);
				WXPublicCacheManager.getInstance().putWXPublic(store_id, wpPublic);
			}
			
		}
		return wpPublic;
	}
}
