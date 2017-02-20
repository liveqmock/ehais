package org.ehais.model.storeconfig;

import java.util.HashMap;
import java.util.Map;

import org.ehais.model.BootStrapModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@XStreamAlias("sub") 
public class StoreConfigSub {

	@XStreamAsAttribute 
	private String code;
	@XStreamAsAttribute 
	private String text;
	@XStreamAsAttribute 
	private String sort;
	@XStreamAsAttribute 
	private String type;
	@XStreamAsAttribute 
	private String isvoid;
	@XStreamAsAttribute 
	private String role;
	@XStreamAsAttribute 
	private String range;
	private BootStrapModel bootStrap;
	
	
	public StoreConfigSub() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StoreConfigSub(String code, String text, String type, String sort, String isvoid, String role,String range) {
		super();
		this.code = code;
		this.text = text;
		this.sort = sort;
		this.type = type;
		this.isvoid = isvoid;
		this.role = role;
		this.range = range;
	}



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getIsvoid() {
		return isvoid;
	}

	public void setIsvoid(String isvoid) {
		this.isvoid = isvoid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public BootStrapModel getBootStrap() {
		return bootStrap;
	}

	public void setBootStrap(BootStrapModel bootStrap) {
		this.bootStrap = bootStrap;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Map<String, String> getRangeMap() {
		if(range == null || range.equals(""))return null;
		Map<String, String> map = new HashMap<String, String>();
		JSONArray jsonArray = JSONArray.fromObject(range);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject o = jsonArray.getJSONObject(i);
			map.put(o.getString("key"), o.getString("value"));
		}
		return map;
	}
}
