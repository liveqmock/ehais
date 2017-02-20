package org.ehais.model.role;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class RoleControllerGroup {

	@XStreamImplicit
	private List<RoleController> group;

	public List<RoleController> getGroup() {
		return group;
	}

	public void setGroup(List<RoleController> group) {
		this.group = group;
	}
	
	
	
}
