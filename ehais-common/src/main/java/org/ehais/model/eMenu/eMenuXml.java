package org.ehais.model.eMenu;



import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class eMenuXml {
	private List<eParent> menu;

	public List<eParent> getMenu() {
		return menu;
	}

	public void setMenu(List<eParent> menu) {
		this.menu = menu;
	}
	
}

