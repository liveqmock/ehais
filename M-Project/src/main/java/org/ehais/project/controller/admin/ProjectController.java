package org.ehais.project.controller.admin;

import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class ProjectController extends CommonController{

	@RequestMapping("/projectList")
	public String projectList(){
		
		return "/admin/project/list";
		
	}
}
