package org.ehais.weixin.controller.member;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.model.Menu;
import org.ehais.model.eMenu.eMenuItem;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.FSO;
import org.ehais.weixin.controller.ManagerController;
import org.ehais.weixin.model.WpPublicWithBLOBs;
import org.ehais.weixin.service.wx.PublicService;
import org.ehais.weixin.service.wx.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;


@Controller
@RequestMapping("/member")
public class IndexMemberController extends ManagerController{

	@Autowired
	private PublicService publicService;
	@Autowired
	private WeiXinService weixinService;
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			String username = (String)request.getSession().getAttribute(EConstants.SESSION_USER_NAME);
			
			
			
			//判断用户是否在wp_public注册了信息，如果没有，给他添加一条
			ReturnObject<WpPublicWithBLOBs> rm = publicService.public_by_user(userId);
			WpPublicWithBLOBs wpPublic = rm.getModel();
			request.getSession().setAttribute(EConstants.SESSION_WX_ID,wpPublic.getId());
			weixinService.setWpPublic(wpPublic.getId(), wpPublic);
			
			String menu_path = this.config_file(request,"menu_"+username+".xml");
			String menu_content = FSO.BufferedReader(menu_path);			
//			Gson gson = new Gson();
//			List<Menu> menuList = gson.fromJson(menu_content, new TypeToken<List<Menu>>(){}.getType());
			
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true); 
			xStream.alias("xml",eMenuXml.class);
			xStream.alias("Parent",eParent.class);
			xStream.alias("MenuItem",eMenuItem.class);
			eMenuXml menux = (eMenuXml) xStream.fromXML(menu_content);
			modelMap.addAttribute("menux", menux);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/member/index";
	}
	
	

	@RequestMapping("/encode")
	public String encode(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{


			
//			String menu_path = this.menu_xml_path(request,6);
//			log.info("menu_path:"+menu_path);
//			String menu_content = this.BufferedReaderV(menu_path);	
//			log.info("menu_content:"+menu_content);
//			menu_content=new String(menu_content.getBytes("iso8859-1"),"utf-8");
//			log.info("3 menu_content:"+menu_content);
			
			String before = "appid=wx60d40f5c4010dc27&body=环境公益维权专项基金&mch_id=1297649401&nonce_str=du7qmq6i6u7s4cby4ztjjil273rdapx7&notify_url=http://wx.epfgp.org/weixin/notify_pay&openid=oOY6kxLWfQnySw5uNom1X5iVZWwY&out_trade_no=20160414162518907961&spbill_create_ip=121.201.3.4&total_fee=100&trade_type=JSAPI&key=t1hbajtoi3k05bpc5xllb7fl8hsyfrqb";
	        before = "appid=wx60d40f5c4010dc27&body=感恩东江公益活动捐款&mch_id=1297649401&nonce_str=xqcxqwfpfbo44dlw4btbzento3lnn3av&notify_url=http://wx.epfgp.org/weixin/notify_pay&openid=oOY6kxLWfQnySw5uNom1X5iVZWwY&out_trade_no=20160414161015727689&spbill_create_ip=120.27.35.142&total_fee=100&trade_type=JSAPI&key=t1hbajtoi3k05bpc5xllb7fl8hsyfrqb";
	        String result = EncryptUtils.md5(before).toUpperCase();
	        System.out.println(result);
	        
			modelMap.addAttribute("content", before+"\r\n"+result);
			
//			System.out.println("Default Charset=" + Charset.defaultCharset());  
//	        System.out.println("file.encoding=" + System.getProperty("file.encoding"));  
//	        System.out.println("Default Charset=" + Charset.defaultCharset());  
//	        System.out.println("Default Charset in Use=" + getDefaultCharSet()); 
	        
//	        
//	        System.out.println("1."+"中文");
//	        System.out.println("2."+"中文".getBytes());
//	        System.out.println("3."+"中文".getBytes("GB2312"));
//	        System.out.println("4."+"中文".getBytes("ISO-8859-1"));
//	        System.out.println("5."+new String("中文".getBytes()));
//	        System.out.println("6."+new String("中文".getBytes(), "GB2312"));
//	        System.out.println("7."+new String("中文".getBytes(), "ISO-8859-1"));
//	        System.out.println("8."+new String("中文".getBytes("GB2312")));
//	        System.out.println("9."+new String("中文".getBytes("GB2312"), "GB2312"));
//	        System.out.println("10."+new String("中文".getBytes("GB2312"), "ISO-8859-1"));
//	        System.out.println("11."+new String("中文".getBytes("ISO-8859-1")));
//	        System.out.println("12."+new String("中文".getBytes("ISO-8859-1"), "GB2312"));
//	        System.out.println("13."+new String("中文".getBytes("ISO-8859-1"), "ISO-8859-1"));
//	        System.out.println("14."+"中文".getBytes("UTF-8"));
//	        
//	        
//	        System.out.println("1."+menu_content);
//	        System.out.println("2."+menu_content.getBytes());
//	        System.out.println("3."+menu_content.getBytes("GB2312"));
//	        System.out.println("4."+menu_content.getBytes("ISO-8859-1"));
//	        System.out.println("5."+new String(menu_content.getBytes()));
//	        System.out.println("6."+new String(menu_content.getBytes(), "GB2312"));
//	        System.out.println("7."+new String(menu_content.getBytes(), "ISO-8859-1"));
//	        System.out.println("8."+new String(menu_content.getBytes("GB2312")));
//	        System.out.println("9."+new String(menu_content.getBytes("GB2312"), "GB2312"));
//	        System.out.println("10."+new String(menu_content.getBytes("GB2312"), "ISO-8859-1"));
//	        System.out.println("11."+new String(menu_content.getBytes("ISO-8859-1")));
//	        System.out.println("12."+new String(menu_content.getBytes("ISO-8859-1"), "GB2312"));
//	        System.out.println("13."+new String(menu_content.getBytes("ISO-8859-1"), "ISO-8859-1"));
//	        System.out.println("14."+menu_content.getBytes("UTF-8"));
//	        System.out.println("15."+new String(menu_content.getBytes("GB18030"), "UTF-8"));
//	        
	        
	        
//	        Properties props=System.getProperties(); 
//	        System.out.println("Java的运行环境版本："+props.getProperty("java.version")); 
//	        System.out.println("Java的运行环境供应商："+props.getProperty("java.vendor")); 
//	        System.out.println("Java供应商的URL："+props.getProperty("java.vendor.url")); 
//	        System.out.println("Java的安装路径："+props.getProperty("java.home")); 
//	        System.out.println("Java的虚拟机规范版本："+props.getProperty("java.vm.specification.version")); 
//	        System.out.println("Java的虚拟机规范供应商："+props.getProperty("java.vm.specification.vendor")); 
//	        System.out.println("Java的虚拟机规范名称："+props.getProperty("java.vm.specification.name")); 
//	        System.out.println("Java的虚拟机实现版本："+props.getProperty("java.vm.version")); 
//	        System.out.println("Java的虚拟机实现供应商："+props.getProperty("java.vm.vendor")); 
//	        System.out.println("Java的虚拟机实现名称："+props.getProperty("java.vm.name")); 
//	        System.out.println("Java运行时环境规范版本："+props.getProperty("java.specification.version")); 
//	        System.out.println("Java运行时环境规范供应商："+props.getProperty("java.specification.vender")); 
//	        System.out.println("Java运行时环境规范名称："+props.getProperty("java.specification.name")); 
//	        System.out.println("Java的类格式版本号："+props.getProperty("java.class.version")); 
//	        System.out.println("Java的类路径："+props.getProperty("java.class.path")); 
//	        System.out.println("加载库时搜索的路径列表："+props.getProperty("java.library.path")); 
//	        System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir")); 
//	        System.out.println("一个或多个扩展目录的路径："+props.getProperty("java.ext.dirs")); 
//	        System.out.println("操作系统的名称："+props.getProperty("os.name")); 
//	        System.out.println("操作系统的构架："+props.getProperty("os.arch")); 
//	        System.out.println("操作系统的版本："+props.getProperty("os.version")); 
//	        System.out.println("文件分隔符："+props.getProperty("file.separator"));//在 unix 系统中是＂／＂ System.out.println("路径分隔符："+props.getProperty("path.separator"));//在 unix 系统中是＂:＂ System.out.println("行分隔符："+props.getProperty("line.separator"));//在 unix 系统中是＂/n＂ System.out.println("用户的账户名称："+props.getProperty("user.name"));  
//	        System.out.println("用户的主目录："+props.getProperty("user.home")); 
//	        System.out.println("用户的当前工作目录："+props.getProperty("user.dir")); 
	        
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/member/encode";
	}
	
	public String BufferedReaderV(String path) throws IOException {
//		File file = new File(path,"UTF-8");
//		if (!file.exists() || file.isDirectory())
//			throw new FileNotFoundException();
//		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8")); 
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
//			temp = common.toUTF8(temp);
//			temp = new String(temp.getBytes("UTF-8"),  "ISO-8859-1");
//			temp = new String(temp.getBytes("UTF-8"),  "GB18030");
			
			sb.append(temp + "\r\n");
			temp = br.readLine();
		}
		return sb.toString();
	}
	
	 private static String getDefaultCharSet() {  
	        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());  
	        String enc = writer.getEncoding();  
	        return enc;  
	    }
	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/member/main";
	}
}
