package org.ehais.project.controller.member;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProExamine;
import org.ehais.project.model.ProExamineWithBLOBs;
import org.ehais.project.model.ProWorkLogWithBLOBs;
import org.ehais.project.service.ExamineService;
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
public class ExamineController  extends CommonController{

	@Autowired
	private ExamineService examineService;
	
	@Autowired
	private ProWbsWorkService proWbsWorkService;
	
	@Autowired
	private HaiUserService haiUserService;
	
	private Integer proId ;
	
	
	@RequestMapping(value="/examineList",method = RequestMethod.GET)
	public String examineList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro = proWbsWorkService.userProjectList(proId);
			
			if(ro != null)request.setAttribute("userProjectList", ro.getRows());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("examineList", e);
		}
		
		return "/admin/examine/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/examineListJson",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String examineListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "user_id", required = false) Integer user_id,
			@RequestParam(value = "progress", required = false) Integer progress
			) {
		
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
//			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProExamine> ro = examineService.ListExamine( proId ,user_id ,progress , page, rows);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list json", e);
		}
		
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/examineToWorkLog",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String examineToWorkLog(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "examineId", required = true) Integer examineId
			) {
		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			ReturnObject<ProExamine> ro = examineService.examineToWorkLog(proId, userId, examineId);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/examineInsert",method = RequestMethod.GET)
	public String examineInsert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		ProExamine examine = new ProExamine();
		
		modelMap.addAttribute("model", examine);
		modelMap.addAttribute("action", "examineInsertSubmit");
		
		
		try{
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro = proWbsWorkService.userProjectList(proId);
			
			if(ro != null)request.setAttribute("userProjectList", ro.getRows());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert", e);
		}
		
		return "/admin/examine/info";
	}
	
	@RequestMapping(value="/examineInsertSubmit",method = RequestMethod.POST)
	public String examineInsertSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProExamineWithBLOBs examine,
			@RequestParam(value = "is_send_email", required = false) boolean is_send_email			
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			if(examine.getUserId() == null)examine.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			examine.setProId(proId);
			ReturnObject<ProExamine> ro = examineService.InsertExamine(examine);
			
			if(is_send_email && examine.getUserId()!=null && !examine.getUserId().equals("")){
				
				HaiUsers user = haiUserService.getUserById(examine.getUserId());
				if(user != null){
					JavaMailUtils.createSimpleMail(
							user.getEmail(), 
							examine.getExamineTitle(), 
							this.getEmailContent(examine, request)
							);
				}
				
			}
			
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "examineInsert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/examineUpdate",method = RequestMethod.GET)
	public String examineUpdate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProExamine examine
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		examine.setProId(proId);
		try{
			examine.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProExamine> ro = examineService.FindExamine(examine.getUserId() ,examine.getExamineId(),examine.getProId());
			modelMap.addAttribute("model", ro.getModel());
			modelMap.addAttribute("action", "examineUpdateSubmit");
			
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro2 = proWbsWorkService.userProjectList(proId);
			
			if(ro2 != null)request.setAttribute("userProjectList", ro2.getRows());
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update", e);
		}
		
		return "/admin/examine/info";
	}
	
	@RequestMapping(value="/examineUpdateSubmit",method = RequestMethod.POST)
	public String examineUpdateSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProExamineWithBLOBs examine,
			@RequestParam(value = "is_send_email", required = false) boolean is_send_email
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			if(examine.getUserId() == null)examine.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProExamine> ro = examineService.UpdateExamine(examine);
			
			if(is_send_email && examine.getUserId()!=null && !examine.getUserId().equals("")){
				
				HaiUsers user = haiUserService.getUserById(examine.getUserId());
				if(user != null){
					JavaMailUtils.createSimpleMail(
							user.getEmail(), 
							examine.getExamineTitle(), 
							this.getEmailContent(examine, request)
							);
				}
				
			}
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "examineList");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/examineRecover",method = RequestMethod.GET)
	public String examineRecover(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProExamine examine
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			examine.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			examine.setProId(proId);
			ReturnObject<ProExamine> ro = examineService.RecoverExamine(examine);
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "examineList");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log recover", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	private String getEmailContent(ProExamineWithBLOBs examine,HttpServletRequest request){
		return "<h3>测试内容:</h3><p></p>" +
				examine.getExamineContent()+
				"<h3>结果:</h3><p></p>" +
				examine.getExamineResult()+
				"<h3>备注:</h3><p></p>" +
				examine.getRemark()+
				"<h5><a href='"+request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+"/member/index' target='_blank'>点击链接查看</a></h5>";
	}
	
	
	@RequestMapping("/examine_export_excel")
	public String examine_export_excel(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_id", required = false) Integer user_id,
			@RequestParam(value = "progress", required = false) Integer progress
			) {
		String fileName="examine.xls";
		// 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "UTF-8"));
			
	        proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
	        ReturnObject<ProExamine> ro = examineService.ListExamine( proId ,user_id ,progress , null, null);
	        
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			
			ByteArrayOutputStream excelStream = examineService.exportExcel(ro.getRows(),proId,userId);
			byte[] bytes = excelStream.toByteArray();
			response.getOutputStream().write(bytes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
