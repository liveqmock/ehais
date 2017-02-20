package org.ehais.model.role;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class RolePersonGroup {

	@XStreamImplicit
	private List<RolePerson> group;

	public List<RolePerson> getGroup() {
		return group;
	}

	public void setGroup(List<RolePerson> group) {
		this.group = group;
	}


	
	
	
}
