package org.ehais.epublic.controller.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.model.eMenu.eMenuItem;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.util.FSO;
import org.ehais.util.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.XStream;


@Controller
@RequestMapping("/admin")
public class EIndexAdminController extends CommonController{

	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{

			String ROLE = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
			
			if(ROLE!=null && !ROLE.equals("")) {
				ROLE="_"+ROLE;
			}else{
				ROLE = "";
			}
			//当此权限用户的菜单不存在，则用统一的菜单
			String menu_path = this.config_file(request,"menu"+ROLE+".xml");
			File file = new File(menu_path);
			if (!file.exists() || file.isDirectory()){
				menu_path = this.config_file(request,"menu.xml");
				file = new File(menu_path);
			}
			String menu_content = FSO.BufferedReader(file);			

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
		return "/admin/main/index";
	}
	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/main/main";
	}
	
}
