package org.ehais.epublic.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.epublic.model.EHaiStore;

public class EAdminTokenCacheManager {
	private BaseOSCache eAdminTokenCache;
	private static EAdminTokenCacheManager instance;
	private static Object lock = new Object();
	
	
	public EAdminTokenCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("EAdminTokenCacheManager 这个根据配置文件来，初始BaseCache而已");
		eAdminTokenCache = new BaseOSCache("e_admin_token_cache", 1880000);
	}
	
	public static EAdminTokenCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					System.out.println("创建EAdminTokenCacheManager缓存对象");
					instance = new EAdminTokenCacheManager();
				}
			}
		}
		return instance;
	}
	
	

	public void putAdminToken(Integer storeId, String token) {
		// TODO 自动生成方法存根
		eAdminTokenCache.put(storeId, token);
	}

	public void removeAdminToken(Integer storeId) {
		// TODO 自动生成方法存根
		eAdminTokenCache.remove(storeId);
	}

	public String getAdminToken(Integer storeId) {
		// TODO 自动生成方法存根
		try {
			return (String) eAdminTokenCache.get(storeId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("eAdminTokenCache>>storeId[" + storeId + "]>>"
					+ e.getMessage());
			return null;
		}
	}

	public void removeAllAdminToken() {
		// TODO 自动生成方法存根
		eAdminTokenCache.removeAll();
	}
}
