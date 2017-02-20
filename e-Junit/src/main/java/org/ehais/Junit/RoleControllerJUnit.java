package org.ehais.Junit;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.role.RoleController;
import org.ehais.model.role.RoleControllerGroup;
import org.ehais.model.role.RoleMethod;
import org.ehais.model.role.RolePerson;
import org.ehais.model.role.RolePersonGroup;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class RoleControllerJUnit {

	@Test
	public void controller(){
		RoleControllerGroup xml = new RoleControllerGroup();
		List<RoleController> xmlGroup = new ArrayList<RoleController>();
		
		
		int i = 0;
		RoleController group = new RoleController();
		group.setController("controller"+i);group.setControllerName("controllerName"+i);		
		List<RoleMethod> list = new ArrayList<RoleMethod>();

		RoleMethod action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		
		i=1;
		action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		
		i=2;
		action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		group.setRoleAction(list);
		
		xmlGroup.add(group);
		
		///////////////////////
		i=3;
		group = new RoleController();
		group.setController("controller"+i);group.setControllerName("controllerName"+i);		
		list = new ArrayList<RoleMethod>();

		action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		
		
		action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		
		i=4;
		action = new RoleMethod();
		action.setAction("action"+i);action.setActionCode("actioncode"+i);action.setActionDesc("actiondesc"+i);action.setActionName("actionname"+i);action.setActionUrl("actionurl"+i);
		list.add(action);
		group.setRoleAction(list);
		
		xmlGroup.add(group);
		
		///////////////////////////////////////////////
		
		xml.setGroup(xmlGroup);
		XStream xStream = new XStream();
		
		xStream.autodetectAnnotations(true); 
		
		String xmlStr = xStream.toXML(xml);
		System.out.println(xmlStr);
		
		xStream.processAnnotations(RoleControllerGroup.class);
		xStream.processAnnotations(RoleController.class);
		xStream.processAnnotations(RoleMethod.class);
		
		RoleControllerGroup xmlx = (RoleControllerGroup) xStream.fromXML(xmlStr);
		List<RoleController> xmlGroupx = xmlx.getGroup();
		for (RoleController roleController : xmlGroupx) {
			System.out.println(roleController.getControllerName());
			
			List<RoleMethod> l = roleController.getRoleAction();
			for (RoleMethod roleActionSub : l) {
				System.out.println("    "+roleActionSub.getAction());
			}
		}
		
	}
	
	
	@Test
	public void Role_Person(){
		
		List<String> actionCode = new ArrayList<String>();
		actionCode.add("admin/service/add");
		actionCode.add("admin/service/edit");
		
		RolePersonGroup group = new RolePersonGroup();
		List<RolePerson> groups = new ArrayList<RolePerson>();
		RolePerson person = new RolePerson();
		person.setRolePersonCode("patents");person.setRolePersonName("研究与发展中心");
		person.setActionCode(actionCode);
		groups.add(person);
		
		person = new RolePerson();
		person.setRolePersonCode("patents2");person.setRolePersonName("省知识产权局");
		person.setActionCode(actionCode);
		groups.add(person);
		
		person = new RolePerson();
		person.setRolePersonCode("patents3");person.setRolePersonName("地市知识产权局");
		groups.add(person);
		
		person = new RolePerson();
		person.setRolePersonCode("anget4");person.setRolePersonName("服务代理机构");
		person.setActionCode(actionCode);
		groups.add(person);
		
		
		group.setGroup(groups);
		
		XStream xStream = new XStream();
		
		xStream.autodetectAnnotations(true); 
		
		String xmlStr = xStream.toXML(group);
		System.out.println(xmlStr);
		
		
		
	}
	
}
