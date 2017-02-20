package org.ehais.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.model.storeconfig.StoreConfigParent;
import org.ehais.model.storeconfig.StoreConfigSub;
import org.ehais.model.storeconfig.StoreConfigXml;

import com.thoughtworks.xstream.XStream;

public class StoreConfigUtil {

	//获取配置原始文件内容
	public static StoreConfigXml getStoreConfigXml(String path) throws IOException{
		
		String content = FSO.BufferedReader(path);
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true); 
		
		xStream.alias("xml",StoreConfigXml.class);
		xStream.alias("config",StoreConfigParent.class);
		xStream.alias("item",StoreConfigSub.class);
		
		StoreConfigXml xml = (StoreConfigXml) xStream.fromXML(content);
		
		return xml;
	}
	
	public static List<BootStrapModel> formatBootStrapList(String path) throws IOException{
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		StoreConfigXml xml = getStoreConfigXml(path);
		List<StoreConfigParent> config = xml.getConfig();
		
		for (StoreConfigParent parent : config) {
			bootStrapList.add(new BootStrapModel(parent.getType(), parent.getCode(), parent.getText(), null, "请输入"+parent.getText(), "", "", null,0, Integer.valueOf(parent.getSort())));
			List<StoreConfigSub> item = parent.getItem();
			for (StoreConfigSub storeConfigSub : item) {
				bootStrapList.add(new BootStrapModel(storeConfigSub.getType(), storeConfigSub.getCode(), storeConfigSub.getText(), null, "请输入"+storeConfigSub.getText(), "", "", null,0, Integer.valueOf(parent.getSort())));//最后一个，应该是用上级的序号
			}
		}
		
		return bootStrapList;
	}
	
	
	
}
