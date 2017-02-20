package org.ehais.cache;

import java.util.Date;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class BaseOSCache extends GeneralCacheAdministrator {
	 //过期时间(单位为秒);
	private int refreshPeriod; 
	// 关键字前缀字符;
	private String keyPrefix;
	private static final long serialVersionUID = -4397192926052141162L;

	public BaseOSCache(String keyPrefix, int refreshPeriod) {
		super();
		this.keyPrefix = keyPrefix;
		this.refreshPeriod = refreshPeriod;
	}

	// 添加被缓存的对象;
	public void put(String key, Object value) {
		this.putInCache(this.keyPrefix + "_" + key, value);
	}
	
	public void put(int key, Object value) {
		this.putInCache(this.keyPrefix + "_" + key, value);
	}
	
	// 获取被缓存的对象;
	public Object get(String key) throws Exception {
		try {
			return this.getFromCache(this.keyPrefix + "_" + key,
					this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(this.keyPrefix + "_" + key);
			throw e;
		}
	}
	
	public Object get(int key) throws Exception {
		try {
			return this.getFromCache(this.keyPrefix + "_" + key,
					this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(this.keyPrefix + "_" + key);
			throw e;
		}
	}

	// 删除被缓存的对象;
	public void remove(String key) {
		this.flushEntry(this.keyPrefix + "_" + key);
	}
	
	public void remove(int key) {
		this.flushEntry(this.keyPrefix + "_" + key);
	}

	// 删除所有被缓存的对象;
	public void removeAll(Date date) {
		this.flushAll(date);
	}

	public void removeAll() {
		this.flushAll();
	} // 获取被缓存的对象; public Object get(String key) throws Exception { try { return
		// this.getFromCache(this.keyPrefix + "_" + key, this.refreshPeriod); }
		// catch (NeedsRefreshException e) { this.cancelUpdate(this.keyPrefix +
		// "_" + key); throw e; } }

}
