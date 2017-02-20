package org.ehais.model.ExtendsField;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
public class ExtendsFieldsXml {

	@XStreamImplicit(itemFieldName="group")//隐藏group的标签
	@XStreamAlias("group")
	private List<ExtendsFieldsGroup> group ;

	public List<ExtendsFieldsGroup> getGroup() {
		return group;
	}

	public void setGroup(List<ExtendsFieldsGroup> group) {
		this.group = group;
	}


	
}
