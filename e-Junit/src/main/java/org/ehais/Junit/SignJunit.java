package org.ehais.Junit;

import java.util.Properties;

import org.ehais.util.EncryptUtils;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinUnifiedOrder;
import org.ehais.weixin.utils.WeiXinUtil;
import org.junit.Test;
import org.springframework.core.JdkVersion;

public class SignJunit {

	@Test
	public void wx_md5_sign() throws Exception{
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid("wx60d40f5c4010dc27");
		order.setMch_id("1297649401");
		order.setNonce_str("du7qmq6i6u7s4cby4ztjjil273rdapx7");
		order.setBody("环境公益维权专项基金");
		order.setOut_trade_no("20160414162518907961");
		order.setTotal_fee(100);
//		order.setSpbill_create_ip(IpUtil.getIpAddrV2(request));
		order.setSpbill_create_ip("121.201.3.4");//120.27.35.142
		order.setNotify_url("http://wx.epfgp.org/weixin/notify_pay");
		order.setTrade_type("JSAPI");
		order.setOpenid("oOY6kxLWfQnySw5uNom1X5iVZWwY");
		
		order.setSign(SignUtil.getSign(order.toMap(), "t1hbajtoi3k05bpc5xllb7fl8hsyfrqb"));
		
		String postDataXML = WeiXinUtil.fromUnifiedOrderXml(order);
        System.out.println(postDataXML);
        
        String before = "appid=wx60d40f5c4010dc27&body=环境公益维权专项基金&mch_id=1297649401&nonce_str=du7qmq6i6u7s4cby4ztjjil273rdapx7&notify_url=http://wx.epfgp.org/weixin/notify_pay&openid=oOY6kxLWfQnySw5uNom1X5iVZWwY&out_trade_no=20160414162518907961&spbill_create_ip=121.201.3.4&total_fee=100&trade_type=JSAPI&key=t1hbajtoi3k05bpc5xllb7fl8hsyfrqb";
        before = "appid=wx60d40f5c4010dc27&body=感恩东江公益活动捐款&mch_id=1297649401&nonce_str=xqcxqwfpfbo44dlw4btbzento3lnn3av&notify_url=http://wx.epfgp.org/weixin/notify_pay&openid=oOY6kxLWfQnySw5uNom1X5iVZWwY&out_trade_no=20160414161015727689&spbill_create_ip=120.27.35.142&total_fee=100&trade_type=JSAPI&key=t1hbajtoi3k05bpc5xllb7fl8hsyfrqb";
        String result = EncryptUtils.md5(before).toUpperCase();
        System.out.println(result);

        
	}
	
	
	@Test
	public void jdkVer(){
		JdkVersion jdkVersion = new JdkVersion(){};
        System.out.println(jdkVersion.getJavaVersion()); 
        System.out.println(System.getProperty("java.version"));
	}
	
	
	//java环境 
	@Test
    public  void all(){ 
        Properties props=System.getProperties(); 
        System.out.println("Java的运行环境版本："+props.getProperty("java.version")); 
        System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor")); 
        System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url")); 
        System.out.println("Java的安装路径："+props.getProperty("java.home")); 
        System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version")); 
        System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor")); 
        System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name")); 
        System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version")); 
        System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor")); 
        System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name")); 
        System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version")); 
        System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender")); 
        System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name")); 
        System.out.println("Java的类格式版本号："+props.getProperty("java.class.version")); 
        System.out.println("Java的类路径："+props.getProperty("java.class.path")); 
        System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path")); 
        System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir")); 
        System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs")); 
        System.out.println("操作系统的名称："+props.getProperty("os.name")); 
        System.out.println("操作系统的构架："+props.getProperty("os.arch")); 
        System.out.println("操作系统的版本："+props.getProperty("os.version")); 
        System.out.println("文件分隔符："+props.getProperty("file.separator"));//在 unix 系统中是＂／＂ System.out.println("路径分隔符："+props.getProperty("path.separator"));//在 unix 系统中是＂:＂ System.out.println("行分隔符："+props.getProperty("line.separator"));//在 unix 系统中是＂/n＂ System.out.println("用户的账户名称："+props.getProperty("user.name"));  
        System.out.println("用户的主目录："+props.getProperty("user.home")); 
        System.out.println("用户的当前工作目录："+props.getProperty("user.dir")); 
    } 
    
    
}
