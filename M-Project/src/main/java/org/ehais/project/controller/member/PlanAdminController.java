package org.ehais.project.controller.member;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProWbsWork;
import org.ehais.project.model.ProWbsWorkTree;
import org.ehais.project.service.ProWbsWorkService;
import org.ehais.tools.ReturnObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/member")
public class PlanAdminController extends CommonController{
	@Autowired
	private ProWbsWorkService proWbsWorkService;
	
	@RequestMapping("/P_WBS")
	public String P_WBS(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			List<ProWbsWorkTree> proWbsWorkTreeList = proWbsWorkService.treeWbsWork(proId);
			request.setAttribute("proWbsWorkTreeList", proWbsWorkTreeList);
			
			//获取此项目的所有参与者
			ReturnObject<HaiUsers> ro = proWbsWorkService.userProjectList(proId);
			
			if(ro != null)request.setAttribute("userProjectList", ro.getRows());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/admin/plan/P_WBS";
	}
	
	@ResponseBody
	@RequestMapping(value="/P_SaveWBS",method = RequestMethod.POST)
	public String P_SaveWBS(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "planStartDate", required = false) String planStartDate,
			@RequestParam(value = "planEndDate", required = false) String planEndDate,
			@RequestParam(value = "truthStartDate", required = false) String truthStartDate,
			@RequestParam(value = "truthEndDate", required = false) String truthEndDate,
			@RequestParam(value = "progress", required = false) Integer progress,
			@RequestParam(value = "wbsId") Integer wbsId
			) {

		try{
			
			ReturnObject<ProWbsWork> ro = proWbsWorkService.proWbsWorkSave(wbsId, planStartDate, planEndDate, truthStartDate, truthEndDate, progress);
//			
			JSONObject obj = new JSONObject(ro);
//			response.getWriter().write(obj.toString());
			return obj.toString();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/P_WBS_Data", produces = "text/html;charset=UTF-8")
	public String P_WBS_Data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			List<ProWbsWork> proWbsWorkList = proWbsWorkService.treeWbsWorkNotNullDate(proId);
			request.setAttribute("proWbsWorkList", proWbsWorkList);
			ReturnObject<ProWbsWork> ro = new ReturnObject<ProWbsWork>();
			ro.setRows(proWbsWorkList);
			JSONObject obj = new JSONObject(ro);
			return obj.toString();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 导出excel
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/P_WBS_EXPORT_EXCEL")
	public String P_WBS_EXPORT_EXCEL(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String fileName="excel.xls";
		// 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "UTF-8"));
	        
	        
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			List<ProWbsWorkTree> proWbsWorkTreeList = proWbsWorkService.treeWbsWork(proId);
			ByteArrayOutputStream excelStream = proWbsWorkService.exportExcel(proWbsWorkTreeList);
			
			byte[] bytes = excelStream.toByteArray();
			
			response.getOutputStream().write(bytes);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@RequestMapping("/P_Plan")
	public String P_Plan(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		return "/admin/plan/P_Plan";
	}
	
	@RequestMapping("/P_Calendar")
	public String P_Calendar(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		return "/admin/plan/P_Calendar";
	}


	@RequestMapping(value="/P_AddWBS",method = RequestMethod.POST)
	public String P_AddWBS(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wbsName") String wbsName ,
			@RequestParam(value = "wbsParentId") Integer wbsParentId,
			@RequestParam(value = "level") Integer level
			) {
		
		try{
			Integer proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
			ReturnObject<ProWbsWork> ro = proWbsWorkService.proWbsWorkAdd(wbsName, wbsParentId, proId);
			ProWbsWork pww = ro.getModel();
			ProWbsWorkTree tree = new ProWbsWorkTree();
			tree.setValueByProWbsWork(pww);
			tree.setLevel(level);
			request.setAttribute("val", tree);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/admin/plan/sub/wbs_work_li";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/P_UpdateWBS",method = RequestMethod.POST)
	public String P_UpdateWBS(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wbsId") Integer wbsId,
			@RequestParam(value = "wbsName") String wbsName
			) {
		
		try{
			ReturnObject<ProWbsWork> ro = proWbsWorkService.proWbsWorkSave(wbsId, wbsName);
			JSONObject obj = new JSONObject(ro);
//			response.getWriter().write(obj.toString());
			return obj.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/P_ChangeProject",method = RequestMethod.POST)
	public String P_ChangeProject(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "proId") Integer proId
			) {
		
		try{
			request.getSession().setAttribute(Constants.SESSION_PROJECT_ID , proId);
			
			//设置默认项目值
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			proWbsWorkService.setDefaultProject(userId, proId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "success";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/P_Participate",method = RequestMethod.POST)
	public String P_Participate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wbsId") Integer wbsId,
			@RequestParam(value = "userId") Integer userId
			) {
		
		try{
			ReturnObject<ProWbsWork> ro = proWbsWorkService.proWbsWorkSave(wbsId,userId);
			JSONObject obj = new JSONObject(ro);
			return obj.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
