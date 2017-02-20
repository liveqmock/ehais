package org.ehais.model.storeconfig;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("xml") 
public class StoreConfigXml {

	private List<StoreConfigParent> config;

	public List<StoreConfigParent> getConfig() {
		return config;
	}

	public void setConfig(List<StoreConfigParent> config) {
		this.config = config;
	}


	
	
}
