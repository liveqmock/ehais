package org.ehais.weixin.cache;

import org.ehais.cache.BaseOSCache;
import org.ehais.weixin.model.JsApiTicket;

public class JsApiTicketCacheManager {

	private BaseOSCache jsapiticketCache;
	private static JsApiTicketCacheManager instance;
	private static Object lock = new Object();
	
	
	public JsApiTicketCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		System.out.println("JsApiTicketCacheManager这个根据配置文件来，初始BaseCache而已");
		jsapiticketCache = new BaseOSCache("jsapiticket", 7200);
	}

	public static JsApiTicketCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new JsApiTicketCacheManager();
				}
			}
		}
		return instance;
	}

	public void putJsApiTicket(JsApiTicket jsapiticket) {
		// TODO 自动生成方法存根
		jsapiticketCache.put(jsapiticket.getId(), jsapiticket);
	}

	public void removeJsApiTicket(String id) {
		// TODO 自动生成方法存根
		jsapiticketCache.remove(id);
	}

	public JsApiTicket getJsApiTicket(int id) {
		// TODO 自动生成方法存根
		try {
			return (JsApiTicket) jsapiticketCache.get(id);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			System.out.println("getJsApiTicket>>id[" + id + "]>>"+ e.getMessage());
			return null;
			// JsApiTicket token = new JsApiTicket(id);
			// this.putJsApiTicket(token);
			// return news;
		}
	}

	public void removeAllJsApiTicket() {
		// TODO 自动生成方法存根
		jsapiticketCache.removeAll();
	}

	
	
}
