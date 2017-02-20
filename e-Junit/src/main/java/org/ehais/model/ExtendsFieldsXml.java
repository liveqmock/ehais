package org.ehais.model;

import java.util.List;

import org.ehais.model.ExtendsField.ExtendsFieldsGroup;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class ExtendsFieldsXml {

	@XStreamImplicit(itemFieldName="group")
	@XStreamAlias("group")
	private List<ExtendsFieldsGroup> group ;

	public List<ExtendsFieldsGroup> getGroup() {
		return group;
	}

	public void setGroup(List<ExtendsFieldsGroup> group) {
		this.group = group;
	}


	
}
