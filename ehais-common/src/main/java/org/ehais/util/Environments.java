package org.ehais.util;

/**
 * 环境变量的配置
 * @author lgj628
 *
 */
public class Environments {
	
	private String jdbcUrl;
	private Integer store_id;

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public Integer getStore_id() {
		return store_id;
	}

	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	

}
