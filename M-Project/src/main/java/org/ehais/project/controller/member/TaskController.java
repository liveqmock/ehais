package org.ehais.project.controller.member;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProTask;
import org.ehais.project.model.ProTaskWithBLOBs;
import org.ehais.project.service.TaskService;
import org.ehais.project.service.HaiUserService;
import org.ehais.project.service.ProWbsWorkService;
import org.ehais.project.util.mail.JavaMailUtils;
import org.ehais.tools.ReturnObject;
import org.json.JSONObject;
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
public class TaskController  extends CommonController{

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProWbsWorkService proWbsWorkService;
	
	@Autowired
	private HaiUserService haiUserService;
	
	private Integer proId ;
	
	
	@RequestMapping(value="/taskList",method = RequestMethod.GET)
	public String taskList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro = proWbsWorkService.userProjectList(proId);
			
			if(ro != null)request.setAttribute("userProjectList", ro.getRows());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("taskList", e);
		}
		
		return "/admin/task/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/taskListJson",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String taskListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "user_id", required = false) Integer user_id,
			@RequestParam(value = "progress", required = false) Integer progress
			) {
		
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
//			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProTask> ro = taskService.ListTask( proId ,user_id ,progress , page, rows);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list json", e);
		}
		
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/taskToWorkLog",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String taskToWorkLog(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "taskId", required = true) Integer taskId
			) {
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			ReturnObject<ProTask> ro = taskService.taskToWorkLog(proId, userId, taskId);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/taskInsert",method = RequestMethod.GET)
	public String taskInsert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		ProTask task = new ProTask();
		
		modelMap.addAttribute("model", task);
		modelMap.addAttribute("action", "taskInsertSubmit");
		
		
		try{
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro = proWbsWorkService.userProjectList(proId);
			
			if(ro != null)request.setAttribute("userProjectList", ro.getRows());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert", e);
		}
		
		return "/admin/task/info";
	}
	
	@RequestMapping(value="/taskInsertSubmit",method = RequestMethod.POST)
	public String taskInsertSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProTaskWithBLOBs task,
			@RequestParam(value = "is_send_email", required = false) boolean is_send_email			
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			if(task.getUserId() == null)task.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			task.setProId(proId);
			ReturnObject<ProTask> ro = taskService.InsertTask(task);
			
			if(is_send_email && task.getUserId()!=null && !task.getUserId().equals("")){
				
				HaiUsers user = haiUserService.getUserById(task.getUserId());
				if(user != null){
					JavaMailUtils.createSimpleMail(
							user.getEmail(), 
							task.getTaskTitle(), 
							this.getEmailContent(task, request)
							);
				}
				
			}
			
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "taskInsert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/taskUpdate",method = RequestMethod.GET)
	public String taskUpdate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProTask task
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		task.setProId(proId);
		try{
			task.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProTask> ro = taskService.FindTask(task.getUserId() ,task.getTaskId(),task.getProId());
			modelMap.addAttribute("model", ro.getModel());
			modelMap.addAttribute("action", "taskUpdateSubmit");
			
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro2 = proWbsWorkService.userProjectList(proId);
			
			if(ro2 != null)request.setAttribute("userProjectList", ro2.getRows());
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update", e);
		}
		
		return "/admin/task/info";
	}
	
	@RequestMapping(value="/taskUpdateSubmit",method = RequestMethod.POST)
	public String taskUpdateSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProTaskWithBLOBs task,
			@RequestParam(value = "is_send_email", required = false) boolean is_send_email
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			if(task.getUserId() == null)task.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProTask> ro = taskService.UpdateTask(task);
			
			if(is_send_email && task.getUserId()!=null && !task.getUserId().equals("")){
				
				HaiUsers user = haiUserService.getUserById(task.getUserId());
				if(user != null){
					JavaMailUtils.createSimpleMail(
							user.getEmail(), 
							task.getTaskTitle(), 
							this.getEmailContent(task, request)
							);
				}
				
			}
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "taskList");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/taskRecover",method = RequestMethod.GET)
	public String taskRecover(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProTask task
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			task.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			task.setProId(proId);
			ReturnObject<ProTask> ro = taskService.RecoverTask(task);
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "taskList");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log recover", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	private String getEmailContent(ProTaskWithBLOBs task,HttpServletRequest request){
		return "<h3>任务内容:</h3><p></p>" +
				task.getTaskContent()+
				"<h3>结果:</h3><p></p>" +
				task.getTaskResult()+
				"<h3>备注:</h3><p></p>" +
				task.getRemark()+
				"<h5><a href='"+request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+"/member/index' target='_blank'>点击链接查看</a></h5>";
	}
	
	
	@RequestMapping("/task_export_excel")
	public String task_export_excel(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = false) Integer user_id,
			@RequestParam(value = "progress", required = false) Integer progress
			) {
		String fileName="task.xls";
		// 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "UTF-8"));
			
	        proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
	        ReturnObject<ProTask> ro = taskService.ListTask( proId ,user_id ,progress , null, null);
	        
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			
			ByteArrayOutputStream excelStream = taskService.exportExcel(ro.getRows(),proId,userId);
			byte[] bytes = excelStream.toByteArray();
			response.getOutputStream().write(bytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
