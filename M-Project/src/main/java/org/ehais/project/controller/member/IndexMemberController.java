package org.ehais.project.controller.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
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
@RequestMapping("/member")
public class IndexMemberController extends CommonController{
	@Autowired
	private ProWbsWorkService proWbsWorkService;
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProProject> roProject = proWbsWorkService.projectList(userId);
			List<ProProject> projectList = roProject.getRows();
			
			request.getSession().setAttribute(Constants.SESSION_PROJECT_ID,roProject.getCode());

			request.setAttribute("projectList", projectList);
			String menu_path = this.config_file(request,"member_menu.json");
//			System.out.println("菜单menu_path:"+menu_path);
			String json = FSO.BufferedReader(menu_path);	
//			System.out.println("菜单json:"+json);
			Gson gson = new Gson();
			List<Menu> menuList = gson.fromJson(json, new TypeToken<List<Menu>>(){}.getType());
			
			if(projectList==null || projectList.size() == 0){
				for (Menu menu : menuList) {
					if(menu.getNote().equals("project")){
						menuList.remove(menu);
						break;
					}
				}
			}
			
			modelMap.addAttribute("menuList", menuList);
				

		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/index";
	}
	
}
