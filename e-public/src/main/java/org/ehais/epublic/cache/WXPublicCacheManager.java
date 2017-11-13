package org.ehais.epublic.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.epublic.model.WpPublicWithBLOBs;

public class WXPublicCacheManager {

	private BaseOSCache wxPublicCache;
	private static WXPublicCacheManager instance;
	private static Object lock = new Object();
	
	
	public WXPublicCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("WXPublicCacheManager 这个根据配置文件来，初始BaseCache而已");
		wxPublicCache = new BaseOSCache("wx_public", 1880000);
	}
	
	public static WXPublicCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					System.out.println("创建WXPublicCacheManager缓存对象");
					instance = new WXPublicCacheManager();
				}
			}
		}
		return instance;
	}
	
	

	public void putWXPublic(Integer publicId, WpPublicWithBLOBs publicToken) {
		// TODO 自动生成方法存根
		wxPublicCache.put(publicId, publicToken);
	}

	public void removePublic(Integer publicId) {
		// TODO 自动生成方法存根
		wxPublicCache.remove(publicId);
	}

	public WpPublicWithBLOBs getWXPublic(Integer publicId) {
		// TODO 自动生成方法存根
		try {
			return (WpPublicWithBLOBs) wxPublicCache.get(publicId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("wxPublicCache>>publicId[" + publicId + "]>>"
					+ e.getMessage());
			return null;
		}
	}

	public void removeAllWXToken() {
		// TODO 自动生成方法存根
		wxPublicCache.removeAll();
	}
	
	
	
}
