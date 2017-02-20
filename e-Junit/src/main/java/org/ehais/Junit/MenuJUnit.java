package org.ehais.Junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.model.eMenu.eMenuItem;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.util.FSO;

import com.thoughtworks.xstream.XStream;

public class MenuJUnit {

	public static void main(String[] args) {
		String menu_path = "D:/workspace_luna/ehais/tracking/src/main/resources/config/menu.xml";
		try {
			String content = FSO.BufferedReader(menu_path);
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true); 
			xStream.alias("xml",eMenuXml.class);
			xStream.alias("Parent",eParent.class);
			xStream.alias("MenuItem",eMenuItem.class);
			
			eMenuXml menuv = new eMenuXml();
			List<eParent> menuL = new ArrayList<eParent>();
			eParent ep = new eParent();
			ep.setId("");
			ep.setTitle("");
			ep.setClassName("");
			ep.setIconClassName("");
			ep.setModule("");
			String[] roles = new String[1];
			roles[0] = "";
			ep.setRoles(roles);
			List<eMenuItem> menuItemsL = new ArrayList<eMenuItem>();
			eMenuItem em = new eMenuItem();
			em.setClassName("");
			em.setIconClassName("");
			em.setId("");
			em.setModule("");
			em.setTitle("");
			em.setUrl("");
			String[] roles2 = new String[1];
			roles2[0] = "";
			em.setRoles(roles2);
			menuItemsL.add(em);
			ep.setMenuItems(menuItemsL);
			menuL.add(ep);
			menuv.setMenu(menuL);
			
			String xml = xStream.toXML(menuv);
			System.out.println(xml);
			
			eMenuXml menux = (eMenuXml) xStream.fromXML(content);
			List<eParent> menu = menux.getMenu();
			for (eParent parent : menu) {
				System.out.println(parent.getTitle());
				if(parent.getMenuItems()!=null){
					List<eMenuItem> item = parent.getMenuItems();
					for (eMenuItem menuItem : item) {
						System.out.println(menuItem.getTitle());
						if(menuItem.getRoles()!=null){
							String[] role = menuItem.getRoles();
							for (String role2 : role) {
								System.out.println("role2:"+role2.toString());
							}
						}
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
