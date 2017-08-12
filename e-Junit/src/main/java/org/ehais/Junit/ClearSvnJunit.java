package org.ehais.Junit;

import java.io.File;

public class ClearSvnJunit {

	
	public static void main(String[] args) {
		File f = new File("D:/workspace");
//		delDotsvn(f);
//		File[] files = f.listFiles();
//		for (File file : files)	{
//			System.out.println("delDotsvn(new File(\""+file.getPath().replaceAll("\\\\", "/")+"\"));");
//		}
		
		delBuild(new File("D:/workspace/19d"));
		delBuild(new File("D:/workspace/BalanceSystem"));
		delBuild(new File("D:/workspace/CampusService2"));
		delBuild(new File("D:/workspace/children"));
		delBuild(new File("D:/workspace/cloudman"));
		delBuild(new File("D:/workspace/cloudmanEJB"));
		delBuild(new File("D:/workspace/destoon"));
		delBuild(new File("D:/workspace/dwz_jui"));
		delBuild(new File("D:/workspace/e1shop"));
		delBuild(new File("D:/workspace/easycart"));
		delBuild(new File("D:/workspace/ecshop"));
		delBuild(new File("D:/workspace/ecshop.zip"));
		delBuild(new File("D:/workspace/ecs_pack.sql"));
		delBuild(new File("D:/workspace/eHaisReptileV1"));
		delBuild(new File("D:/workspace/eshopv2.sql"));
		delBuild(new File("D:/workspace/gdrcb"));
		delBuild(new File("D:/workspace/gzyuanpei.sql"));
		delBuild(new File("D:/workspace/Hai.1.08"));
		delBuild(new File("D:/workspace/Hai.1.09"));
		delBuild(new File("D:/workspace/HaiEJB"));
		delBuild(new File("D:/workspace/Hai_1_10"));
		delBuild(new File("D:/workspace/Hai_Lib"));
		delBuild(new File("D:/workspace/iEasy"));
		delBuild(new File("D:/workspace/index.html"));
		delBuild(new File("D:/workspace/jeecms"));
		delBuild(new File("D:/workspace/jericho-html-3.3"));
		delBuild(new File("D:/workspace/jquery_ad"));
		delBuild(new File("D:/workspace/LISProjectManager"));
		delBuild(new File("D:/workspace/Longfor"));
		delBuild(new File("D:/workspace/lurexshop"));
		delBuild(new File("D:/workspace/marquee1.html"));
		delBuild(new File("D:/workspace/MarryPie"));
		delBuild(new File("D:/workspace/MarryPieEJB"));
		delBuild(new File("D:/workspace/mobile_website"));
		delBuild(new File("D:/workspace/ModelXiu"));
		delBuild(new File("D:/workspace/mondseeService"));
		delBuild(new File("D:/workspace/Movie3D"));
		delBuild(new File("D:/workspace/Nanhu"));
		delBuild(new File("D:/workspace/nc"));
		delBuild(new File("D:/workspace/ncAppDocument"));
		delBuild(new File("D:/workspace/onlineservice"));
		delBuild(new File("D:/workspace/OpenMeetings"));
		delBuild(new File("D:/workspace/phpinfo.php"));
		delBuild(new File("D:/workspace/phpMyAdmin-4.4.0-all-languages"));
		delBuild(new File("D:/workspace/ProuterServer"));
		delBuild(new File("D:/workspace/ProuterTools"));
		delBuild(new File("D:/workspace/qq_online"));
		delBuild(new File("D:/workspace/QWeiboSDK"));
		delBuild(new File("D:/workspace/RemoteSystemsTempFiles"));
		delBuild(new File("D:/workspace/Scnunet"));
		delBuild(new File("D:/workspace/scrollnews"));
		delBuild(new File("D:/workspace/Servers"));
		delBuild(new File("D:/workspace/ShopBox"));
		delBuild(new File("D:/workspace/sina"));
		delBuild(new File("D:/workspace/sina.htm"));
		delBuild(new File("D:/workspace/svn"));
		delBuild(new File("D:/workspace/SystemRuntime"));
		delBuild(new File("D:/workspace/TaobaoManager"));
		delBuild(new File("D:/workspace/texas2.1.0"));
		delBuild(new File("D:/workspace/touch-2.4.0"));
		delBuild(new File("D:/workspace/vanas"));
		delBuild(new File("D:/workspace/wedding"));
		delBuild(new File("D:/workspace/WeddingEJB"));
		delBuild(new File("D:/workspace/weiphp"));
		delBuild(new File("D:/workspace/weiphp3.0"));
		delBuild(new File("D:/workspace/wrzcnet"));
		delBuild(new File("D:/workspace/xiahailed"));
		delBuild(new File("D:/workspace/微信验证的加密"));
		delBuild(new File("D:/workspace/月亮湖win8"));

	}
	
	/**
	 * 删除目录中的.svn目录
	 * 
	 * @param f
	 */
	public static void delDotsvn(File f) {
	    File[] files = f.listFiles();
	    if (files != null)
	        for (File file : files)	    
	            if (".svn".equals(file.getName()))
	                del(file);
	            else
	                delDotsvn(file);
	}
	
	
	public static void delBuild(File f) {
	    File[] files = f.listFiles();
	    if (files != null)
	        for (File file : files)	    
	            if ("build".equals(file.getName()))
	                del(file);
	            else
	                delDotsvn(file);
	}
	
	/**
	 * 删除文件或目录
	 */
	public static void del(File file) {
	    File[] files = file.listFiles();
	    if (files != null)
	        for (File f : files)
	            del(f);
	    System.out.println("delete file name:"+file.getPath());
	    file.delete();
	    
	}
}
