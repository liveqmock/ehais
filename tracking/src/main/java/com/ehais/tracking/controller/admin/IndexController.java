package com.ehais.tracking.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.model.eMenu.eMenuItem;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.util.FSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehais.tracking.service.tracking.QuestionnaireService;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("/admin")
public class IndexController extends CommonController{

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			String menuxml = this.config_file(request,"menu.xml");
			String menu_content = FSO.BufferedReader(menuxml);
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
		return "/admin/index_eui";
	}
	
	@RequestMapping("/index_eui")
	public String index_eui(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{

			String menuxml = this.config_file(request,"menu.xml");
			String menu_content = FSO.BufferedReader(menuxml);
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true); 
			xStream.alias("xml",eMenuXml.class);
			xStream.alias("Parent",eParent.class);
			xStream.alias("MenuItem",eMenuItem.class);
			eMenuXml menux = (eMenuXml) xStream.fromXML(menu_content);
			modelMap.addAttribute("menux", menux);
			
//			List<eParent> parent = menux.getMenu();
//			for (eParent eParent : parent) {
//				List<eMenuItem> menuitem = eParent.getMenuItems();
//				for (eMenuItem eMenuItem : menuitem) {
//					System.out.println(eMenuItem.getTitle());
//				}
//			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/index_eui";
	}
	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{

		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/main";
	}
	
	@RequestMapping("/menu_xml")
	public String menu(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			String menuxml = this.config_file(request,"menu.xml");
			String menu_content = FSO.BufferedReader(menuxml);
			
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
		return "/admin/menu_xml";
	}
	
	
}
