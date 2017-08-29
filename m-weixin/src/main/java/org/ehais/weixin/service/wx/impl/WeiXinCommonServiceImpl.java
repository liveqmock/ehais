package org.ehais.weixin.service.wx.impl;

import java.util.List;

import org.ehais.epublic.cache.WXPublicCacheManager;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.WpPublicExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class WeiXinCommonServiceImpl extends CommonServiceImpl{

	@Autowired
	protected WpPublicMapper wpPublicMapper;


	public Integer wxStoreId(Integer store_id) throws Exception {
		// TODO Auto-generated method stub
//		Integer store_id = null;
		//根据id找到缓存中的token
		WpPublicWithBLOBs wpPublic = WXPublicCacheManager.getInstance().getWXPublic(store_id);
		if(wpPublic == null){
			wpPublic = wpPublicMapper.selectByPrimaryKey(store_id);
			WXPublicCacheManager.getInstance().putWXPublic(store_id, wpPublic);
		}
		store_id = wpPublic.getUid().intValue();
		
		return store_id;
	}

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
	
	public void setWpPublic(Integer store_id,WpPublicWithBLOBs wpPublic) throws Exception{
		WXPublicCacheManager.getInstance().putWXPublic(store_id, wpPublic);
	}

	
	
}
