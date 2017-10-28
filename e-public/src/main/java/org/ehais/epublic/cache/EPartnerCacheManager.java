package org.ehais.epublic.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.epublic.model.HaiPartner;

public class EPartnerCacheManager {

	private BaseOSCache ePartnerCache;
	private static EPartnerCacheManager instance;
	private static Object lock = new Object();
	
	
	public EPartnerCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("EPartnerCacheManager 这个根据配置文件来，初始BaseCache而已");
		ePartnerCache = new BaseOSCache("e_partner_cache", 1880000);
	}
	
	public static EPartnerCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					System.out.println("创建EPartnerCacheManager缓存对象");
					instance = new EPartnerCacheManager();
				}
			}
		}
		return instance;
	}
	
	

	public void putEPartner(Integer partnerId, HaiPartner ePartner) {
		// TODO 自动生成方法存根
		ePartnerCache.put(partnerId, ePartner);
	}

	public void removeEPartner(Integer partnerId) {
		// TODO 自动生成方法存根
		ePartnerCache.remove(partnerId);
	}

	public HaiPartner getEPartner(Integer partnerId) {
		// TODO 自动生成方法存根
		try {
			return (HaiPartner) ePartnerCache.get(partnerId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("ePartnerCache>>partnerId[" + partnerId + "]>>"
					+ e.getMessage());
			return null;
		}
	}

	public void removeAllEPartner() {
		// TODO 自动生成方法存根
		ePartnerCache.removeAll();
	}
	
}
