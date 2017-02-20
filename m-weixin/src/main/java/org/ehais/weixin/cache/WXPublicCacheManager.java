package org.ehais.weixin.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.model.WpPublicWithBLOBs;

public class WXPublicCacheManager {

	private BaseOSCache wxPublicCache;
	private static WXPublicCacheManager instance;
	private static Object lock = new Object();
	
	
	public WXPublicCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("WXPublicCacheManager 这个根据配置文件来，初始BaseCache而已");
		wxPublicCache = new BaseOSCache("wx_token", 1880000);
	}
	
	public static WXPublicCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					System.out.println("创建WXTokenCacheManager缓存对象");
					instance = new WXPublicCacheManager();
				}
			}
		}
		return instance;
	}
	
	

	public void putWXPublic(Integer wxid, WpPublicWithBLOBs publicToken) {
		// TODO 自动生成方法存根
		wxPublicCache.put(wxid, publicToken);
	}

	public void removePublic(Integer wxid) {
		// TODO 自动生成方法存根
		wxPublicCache.remove(wxid);
	}

	public WpPublicWithBLOBs getWXPublic(int wxid) {
		// TODO 自动生成方法存根
		try {
			return (WpPublicWithBLOBs) wxPublicCache.get(wxid);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("wxPublicCache>>wxid[" + wxid + "]>>"
					+ e.getMessage());
			return null;
		}
	}

	public void removeAllWXToken() {
		// TODO 自动生成方法存根
		wxPublicCache.removeAll();
	}
	
	
	
}
