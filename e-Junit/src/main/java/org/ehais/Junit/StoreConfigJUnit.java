package org.ehais.Junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.model.storeconfig.StoreConfigParent;
import org.ehais.model.storeconfig.StoreConfigSub;
import org.ehais.model.storeconfig.StoreConfigXml;
import org.ehais.model.eMenu.eMenuItem;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.util.FSO;

import com.thoughtworks.xstream.XStream;

public class StoreConfigJUnit {

	public static void main(String[] args) {
//		String menu_path = "D:/workspace_luna/ehais/e-Junit/src/main/resources/config/StoreConfig.xml";
		String menu_path = "/Users/gzepro/developer/j2ee/workspace/ehais/e-Junit/src/main/resources/config/StoreConfig.xml";
		
		try {
			String content = FSO.BufferedReader(menu_path);
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true); 
			
			/*
			StoreConfigXml scXml = new StoreConfigXml();
			List<StoreConfigParent> config = new ArrayList<StoreConfigParent>();
			
			List<StoreConfigSub> sub = new ArrayList<StoreConfigSub>();
			StoreConfigSub scs = new StoreConfigSub("info","配置","text","1","1");
			StoreConfigSub scs1 = new StoreConfigSub("info1","配置1","text","1","1");
			sub.add(scs); sub.add(scs1);
			
			StoreConfigParent p = new StoreConfigParent("info1","配置1","text","1","1",sub);
			config.add(p);
			
			scXml.setConfig(config);
			
			String xml = xStream.toXML(scXml);
			System.out.println(xml);
			*/
			
			xStream.alias("xml",StoreConfigXml.class);
			xStream.alias("config",StoreConfigParent.class);
			xStream.alias("item",StoreConfigSub.class);
			
			StoreConfigXml xml = (StoreConfigXml) xStream.fromXML(content);
			List<StoreConfigParent> config = xml.getConfig();
			for (StoreConfigParent parent : config) {
				System.out.println(parent.getCode());
				List<StoreConfigSub> sub = parent.getItem();
				for (StoreConfigSub storeConfigSub : sub) {
					System.out.println("   "+storeConfigSub.getCode());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
