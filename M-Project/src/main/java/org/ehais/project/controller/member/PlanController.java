package org.ehais.project.controller.member;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.ProPlan;
import org.ehais.project.model.ProPlanWithBLOBs;
import org.ehais.project.model.ProTask;
import org.ehais.project.service.PlanService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class PlanController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(PlanController.class);

	@Autowired
	private PlanService planService;
	
	private Integer proId ;
	
	
	@RequestMapping("/plan_list")
	public String plan_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		try{
			modelMap.addAttribute("proId", proId);
			modelMap.addAttribute("action", "plan_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/plan/list";
	}
	
	@ResponseBody
	@RequestMapping("/plan_list_json")
	public String plan_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProPlan> rm = planService.plan_list_json(proId, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/plan_insert")
	public String plan_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProPlanWithBLOBs> rm = planService.plan_insert(proId);
			rm.setAction("plan_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "plan_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/plan/info";
	}
	
	@RequestMapping(value="/plan_insert_submit",method=RequestMethod.POST)
	public String plan_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProPlanWithBLOBs plan
			) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			plan.setProId(proId);
			ReturnObject<ProPlanWithBLOBs> rm = planService.plan_insert_submit(plan);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "plan_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/plan_update")
	public String plan_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "planId", required = true) Integer planId
			) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProPlanWithBLOBs> rm = planService.plan_update(proId, planId);
			rm.setAction("plan_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/plan/info";
	}
	
	@RequestMapping(value="/plan_update_submit",method=RequestMethod.POST)
	public String plan_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProPlanWithBLOBs plan
			) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			plan.setProId(proId);
			ReturnObject<ProPlanWithBLOBs> rm = planService.plan_update_submit(plan);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "plan_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/plan/info";
	}
	
	
	@RequestMapping("/plan_delete")
	public String plan_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "planId", required = false) Integer planId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProPlan> rm = planService.plan_delete(proId, planId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "plan_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/plan_export_excel")
	public String task_export_excel(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		String fileName="plan.xls";
		// 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "UTF-8"));
			
	        proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);

			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);

			ByteArrayOutputStream excelStream = planService.plan_export_excel(proId);
			byte[] bytes = excelStream.toByteArray();
			response.getOutputStream().write(bytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
