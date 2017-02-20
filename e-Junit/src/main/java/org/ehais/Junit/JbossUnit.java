package org.ehais.Junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ehais.util.FSO;
import org.junit.Test;

public class JbossUnit {

	private String br = "\r\n";
	
	@Test
	public void xml(){
		String filepath = "D:/workspace_luna/patents/p-portal/target/p-portal/WEB-INF/lib";
		String targetpath = "D:/Program Files/jboss-as-7.1.1.Final/modules/patents";
		//找到所有包名
		try {
//			List<String> list =  FSO.ReadfileList(new ArrayList<String>(),filepath);
//			for (String string : list) {
//				System.out.println(string);
//			}
			File file = new File(filepath);
			String[] filelist = file.list();
			String module_slot = "<subsystem xmlns=\"urn:jboss:domain:ee:1.0\">"+br+"	<global-modules>"+br;
			for (int i = 0; i < filelist.length; i++) {
//				System.out.println("<resource-root path=\""+filelist[i]+"\"/>");
				String filejar = this.splitFolder(filelist[i],targetpath);
				System.out.println("复制文件："+targetpath+"/"+filejar+"/"+ filelist[i]);
				FSO.copyFile(filepath+"/"+ filelist[i], targetpath+"/"+filejar+"/"+ filelist[i]);
//				
				String module_name = "patents."+filejar.replaceAll("/", ".").replace(".main", "");
				String mainxmlcenrent = this.mainXml(module_name, filelist[i]);
				FSO.WriteTextFileZh(targetpath+"/"+filejar+"/module.xml", mainxmlcenrent);
				
				module_slot += "<module name=\""+module_name+"\" slot=\"main\"/>"+br;
//				
			}
			
			module_slot+="	</global-modules>"+br+"</subsystem>";
			
			
			System.out.println(module_slot);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//整理成语句
		System.out.println("完成所有");
	}
	
	private String splitFolder(String jar,String path){
		jar = jar.replace(".jar", "");
		String[] jarpath = jar.split("-");
		String subPath = "";
		for (String string : jarpath) {
			subPath += "/"+string.replaceAll("\\.", "_");			
			
			FSO.createDir(path+subPath);
		}
		subPath += "/main";
		FSO.createDir(path+subPath);
//		System.out.println(subPath);
		return subPath.substring(1);
	}
	
	private String mainXml(String module_name,String jar_name){
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+br+
							"<module xmlns=\"urn:jboss:module:1.1\" name=\""+module_name+"\">"+br+
							"    <resources>"+br+
							"		<resource-root path=\""+jar_name+"\"/>"+br+
							"    </resources>"+br+
							"    <dependencies>"+br+
							"        <module name=\"javax.api\"/>"+br+
							"        <module name=\"javax.transaction.api\"/>"+br+
							"        <module name=\"javax.servlet.api\" optional=\"true\"/>"+br+
							"    </dependencies>"+br+
							"</module>";
		return content;
	}
	
	
}
