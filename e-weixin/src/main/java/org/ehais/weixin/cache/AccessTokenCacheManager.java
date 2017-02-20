package org.ehais.weixin.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.weixin.model.AccessToken;

public class AccessTokenCacheManager {

	private BaseOSCache accesstokenCache;
	private static AccessTokenCacheManager instance;
	private static Object lock = new Object();

	public AccessTokenCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("AccentTokenCacheManager这个根据配置文件来，初始BaseCache而已");
		accesstokenCache = new BaseOSCache("token", 7200);
	}

	public static AccessTokenCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new AccessTokenCacheManager();
				}
			}
		}
		return instance;
	}

	public void putAccessToken(AccessToken token) {
		// TODO 自动生成方法存根
		accesstokenCache.put(token.getId(), token);
	}

	public void removeAccessToken(String id) {
		// TODO 自动生成方法存根
		accesstokenCache.remove(id);
	}

	public AccessToken getAccessToken(int id) {
		// TODO 自动生成方法存根
		try {
			return (AccessToken) accesstokenCache.get(id);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("getAccessToken>>id[" + id + "]>>"
					+ e.getMessage());
			return null;
			// AccessToken token = new AccessToken(id);
			// this.putAccessToken(token);
			// return news;
		}
	}

	public void removeAllAccessToken() {
		// TODO 自动生成方法存根
		accesstokenCache.removeAll();
	}

}
