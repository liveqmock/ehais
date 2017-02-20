package org.ehais.project.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.project.model.Menu;
import org.ehais.project.model.ProProject;
import org.ehais.project.service.ProWbsWorkService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/admin")
public class IndexAdminController {
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
//			String menu_path = request.getRealPath("/WEB-INF/classes/config/admin_menu.json");
			String menu_path = "D:/workspace_luna/ehais/M-Project/src/main/resources/config/admin_menu.json";
			String json = FSO.BufferedReader(menu_path);			
			Gson gson = new Gson();
			List<Menu> menuList = gson.fromJson(json, new TypeToken<List<Menu>>(){}.getType());
			modelMap.addAttribute("menuList", menuList);
			
//			for (Menu menu : ps) {
//				System.out.println(menu.getId()+"|"+menu.getTitle()+"|"+menu.getParent_id()+"|"+menu.getUrl()+"|"+menu.getNote());
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/index";
	}
	
}
