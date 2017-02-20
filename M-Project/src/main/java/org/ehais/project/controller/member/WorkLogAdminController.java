package org.ehais.project.controller.member;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.ProWbsWorkTree;
import org.ehais.project.model.ProWorkLog;
import org.ehais.project.model.ProWorkLogWithBLOBs;
import org.ehais.project.service.WorkLogService;
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
public class WorkLogAdminController extends CommonController{

	@Autowired
	private WorkLogService workLogService;
	
	
	@RequestMapping(value="/workLogList",method = RequestMethod.GET)
	public String workLogList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list", e);
		}
		
		return "/admin/work_log/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/workLogListJson",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String workLogListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate
			) {
		try{
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProWorkLogWithBLOBs> ro = workLogService.ListWorkLog(userId,proId,startDate,endDate, page, rows);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list json", e);
		}
		
		return null;
	}
	
	
	
	
	@RequestMapping(value="/workLogInsert",method = RequestMethod.GET)
	public String workLogInsert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		ProWorkLog workLog = new ProWorkLog();
		workLog.setWorkDate(new Date());
		modelMap.addAttribute("workLog", workLog);
		modelMap.addAttribute("action", "workLogInsertSubmit");
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert", e);
		}
		
		return "/admin/work_log/info";
	}
	
	@RequestMapping(value="/workLogInsertSubmit",method = RequestMethod.POST)
	public String workLogInsertSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProWorkLogWithBLOBs workLog
			) {
		
		try{
			workLog.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			workLog.setProId((Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID));
			ReturnObject<ProWorkLog> ro = workLogService.InsertWorkLog(workLog);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "workLogInsert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/workLogUpdate",method = RequestMethod.GET)
	public String workLogUpdate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProWorkLog workLog
			) {
		try{
			workLog.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProWorkLog> ro = workLogService.FindWorkLog(workLog.getUserId(),proId, workLog.getWorkId());
			modelMap.addAttribute("workLog", ro.getModel());
			modelMap.addAttribute("action", "workLogUpdateSubmit");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update", e);
		}
		
		return "/admin/work_log/info";
	}
	
	@RequestMapping(value="/workLogUpdateSubmit",method = RequestMethod.POST)
	public String workLogUpdateSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProWorkLogWithBLOBs workLog
			) {
		
		try{
			workLog.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			workLog.setProId((Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID));
			ReturnObject<ProWorkLog> ro = workLogService.UpdateWorkLog(workLog);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "workLogList");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/workLogRecover",method = RequestMethod.GET)
	public String workLogRecover(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProWorkLog workLog
			) {
		
		try{
			workLog.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			workLog.setProId(proId);
			ReturnObject<ProWorkLog> ro = workLogService.RecoverWorkLog(workLog);
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "workLogList");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log recover", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	/**
	 * 导出excel
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/WORK_LOG_EXPORT_EXCEL")
	public String WORK_LOG_EXPORT_EXCEL(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate
			) {
		String fileName="worklog.xls";
		// 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "UTF-8"));
			
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProWorkLogWithBLOBs> ro = workLogService.ListWorkLog(userId,proId,startDate,endDate, 1, 10000);
			ByteArrayOutputStream excelStream = workLogService.exportExcel(ro.getRows(),proId,userId);
			byte[] bytes = excelStream.toByteArray();
			response.getOutputStream().write(bytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
