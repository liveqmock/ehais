package org.ehais.cache;

import java.util.HashMap;
import java.util.Map;


public class EStoreAppKeySecretCacheManager {

	private static EStoreAppKeySecretCacheManager instance;
	private static Map<String, String> map = new HashMap<String, String>();
	
	public EStoreAppKeySecretCacheManager (){
		System.out.println("EStoreAppKeySecretCacheManager....初始化 ");
		
	}
	
	public static EStoreAppKeySecretCacheManager getInstance() {
		if (instance == null) {
			instance = new EStoreAppKeySecretCacheManager();
		}
		return instance;
	}
	
	public void putStoreKey(String appkey,String secret){
		map.put(appkey,secret);
	}
	
	public String getStoreSecret(String appkey) throws Exception{
		return map.get(appkey);
	}
}
