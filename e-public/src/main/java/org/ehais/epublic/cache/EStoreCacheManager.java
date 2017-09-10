package org.ehais.epublic.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.epublic.model.EHaiStore;

public class EStoreCacheManager {
	private BaseOSCache eStoreCache;
	private static EStoreCacheManager instance;
	private static Object lock = new Object();
	
	
	public EStoreCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("EStoreCacheManager 这个根据配置文件来，初始BaseCache而已");
		eStoreCache = new BaseOSCache("e_store_cache", 1880000);
	}
	
	public static EStoreCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					System.out.println("创建EStoreCacheManager缓存对象");
					instance = new EStoreCacheManager();
				}
			}
		}
		return instance;
	}
	
	

	public void putEStore(Integer storeId, EHaiStore eStore) {
		// TODO 自动生成方法存根
		eStoreCache.put(storeId, eStore);
	}

	public void removeEStore(Integer storeId) {
		// TODO 自动生成方法存根
		eStoreCache.remove(storeId);
	}

	public EHaiStore getEStore(Integer storeId) {
		// TODO 自动生成方法存根
		try {
			return (EHaiStore) eStoreCache.get(storeId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("eStoreCache>>storeId[" + storeId + "]>>"
					+ e.getMessage());
			return null;
		}
	}

	public void removeAllEStore() {
		// TODO 自动生成方法存根
		eStoreCache.removeAll();
	}
	
	
}
