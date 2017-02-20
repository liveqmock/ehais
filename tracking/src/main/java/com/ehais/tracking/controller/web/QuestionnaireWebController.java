package com.ehais.tracking.controller.web;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.HTMLSpirit;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.tracking.entity.QuestionGroup;
import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.entity.QuestionnaireGather;
import com.ehais.tracking.entity.Student;
import com.ehais.tracking.service.tracking.QuestionnaireService;
import com.ehais.tracking.service.tracking.StudentService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/")
public class QuestionnaireWebController extends CommonController{
	private static Logger log = LoggerFactory.getLogger(QuestionnaireWebController.class);
	
	Locale locale = Locale.getDefault();  
	ResourceBundle localResource = ResourceBundle.getBundle("config/config", locale);  
	String wxdev_token = localResource.getString("wxdev_token");  
	String weixin_appid = localResource.getString("weixin_appid");  
	String weixin_appsecret = localResource.getString("weixin_appsecret"); 
	
	
	@Autowired
	private QuestionnaireService questionnaireService;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/web/q{id}")
	public String questionnaire_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id") Integer id
			) {
		try{
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_find(null, id);
			ReturnObject<QuestionGroup> rmgroup = questionnaireService.question_group_list(null, id);
			rm.setAction("/web/questionnaire_detail_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("questionsList", rmgroup.getModel().getQuestionsList());
			modelMap.addAttribute("answerList", rmgroup.getModel().getAnswerList());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/questionnaire/detail";
	}
	
	@RequestMapping("/web/questionnaire_detail_submit")
	public String questionnaire_detail_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "questionnaireId", required = true) Integer questionnaireId
			) {
		try{
			

			Map map = new HashMap();  
			Enumeration paramNames = request.getParameterNames();  
		    while (paramNames.hasMoreElements()) {  
		    	String paramName = (String) paramNames.nextElement();  
		 
		    	String[] paramValues = request.getParameterValues(paramName);  
		    	if (paramValues.length == 1) {  
		    		String paramValue = paramValues[0];  
		    		if (paramValue.length() != 0) {  
//		    			System.out.println("参数：" + paramName + "=" + paramValue);  
		    			map.put(paramName, paramValue);  
		    		}  
		    	}  
		    }
		    JSONObject json = JSONObject.fromObject(map);
		    QuestionnaireGather qg = new QuestionnaireGather();
		    qg.setGatherContent(json.toString());
		    qg.setCreateDate(new Date());
		    qg.setQuestionnaireId(questionnaireId);
		    
		    questionnaireService.Gather(qg);
//		    System.out.println(json.toString());
		    
//		    for (Iterator iter = json.keys(); iter.hasNext();) { //先遍历整个 people 对象
//		        String key = (String)iter.next();
	//
//		        System.out.println("json:" + key + " = " + json .getString(key));
	//
//		    }
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		
	   
       
		return "/web/questionnaire/success";
	}
	
	
	@RequestMapping("/web/{wxidx}/h{qidx}/{openidx}")
	public String questionnaire_detail_h5(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxidx") Integer wxid,
			@PathVariable("qidx") Integer qid,
			@PathVariable("openidx") String openid
			) {
		try{
			//如果session为空,则返回关注页面
//			Integer studentId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_find(null, qid);
			ReturnObject<QuestionGroup> rmgroup = questionnaireService.question_group_list(null, qid);
			rm.setAction("/web/questionnaire_detail_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("questionsList", rmgroup.getModel().getQuestionsList());
			modelMap.addAttribute("answerList", rmgroup.getModel().getAnswerList());
			String url = request.getRequestURL().toString();
			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, wxid, weixin_appid, weixin_appsecret, url);
			signature.setTitle(rm.getModel().getTitle());
			signature.setLink(request.getScheme()+"://"+request.getServerName()+"/weixin/go-"+wxid+"-"+qid);
			if(rm.getModel().getInstructions().length() > 0)signature.setDesc(HTMLSpirit.delHTMLTag(rm.getModel().getInstructions()).substring(0, 100) );
			signature.setImgUrl(request.getScheme()+"://"+request.getServerName()+"/images/logo.jpg");
			modelMap.addAttribute("signature", signature);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/questionnaire/detail_h5";
	}
	
	
	
	@RequestMapping("/weixin/go-{wxid}-{qid}")
	public String goWeiXin(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("qid") Integer qid){
		try{
			//整理要回调给自己的地址
			String url = request.getScheme()+"://"+request.getServerName()+"/weixin/openid/"+wxid+"/"+qid;
			log.info("url:"+url);
			url = URLEncoder.encode(url, "utf-8");
			//整理请求获取openid的微信链接
			String wxurl = WeiXinUtil.authorize_snsapi(weixin_appid, "snsapi_base", url);
			log.info("wxurl:"+wxurl);
			//滚去请求，即response.sendR***(wxurl);
			response.sendRedirect( wxurl );
			
			
//			response.sendRedirect( "/weixin/openid/"+wxid+"/"+qid+"?code=1" );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/weixin/openid/{wxid}/{qid}")
	public String getOpenid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("qid") Integer qid
			){
		log.info("openid:==wxid:"+wxid+"/qid="+qid+"/code="+code);
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			//根据code获取openid信息，一样的请求方式
			OpenidInfo open = WeiXinUtil.getOpenid(code, weixin_appid, weixin_appsecret);
			String openid = open.getOpenid();	
			log.info( "openid:" + openid );
			System.out.println("system openid : " + openid);
			AccessToken accessToken = WeiXinUtil.getAccessToken(wxid,weixin_appid, weixin_appsecret);
			WeiXinUserInfo userInfo = WeiXinUtil.getUserInfo(accessToken.getToken(),openid);
			map.put("userInfo", userInfo);		
			if(userInfo == null){
				
			}
			
//			String openid = "ksdfsdfjlwerwe-sdfsd";
//			WeiXinUserInfo userInfo = new WeiXinUserInfo();
//			userInfo.setOpenid(openid);userInfo.setSex(1);userInfo.setNickname("tyler");
			
			//保存用户信息于我们的数据库中
			ReturnObject<Student> rm = studentService.weixin_student_save(request, userInfo);
			
			if(rm.getCode() != 1 || rm.getModel() == null){
				//返回错误页面
				
				return null;
			}
			
			//保存session
			Integer studentId = rm.getModel().getId();
			request.getSession().setAttribute(Constants.SESSION_USER_ID, studentId);
			request.getSession().setAttribute("openid", openid);
			request.getSession().setAttribute("wxid", wxid);
			request.getSession().setAttribute("qid", qid);
			
			String controllurl = "/web/"+wxid+"/h"+qid+"/"+openid;
			//对于未设置学校院系专业班级的用户，需要让他设置先
			if(rm.getModel().getSchoolId() == null || rm.getModel().getSchoolId() == 0 ||
				rm.getModel().getDepartmentId() == null || rm.getModel().getDepartmentId() == 0 ||
				rm.getModel().getProfessionalId() == null || rm.getModel().getProfessionalId() == 0 ||
				rm.getModel().getGradesId() == null || rm.getModel().getGradesId() == 0
					){
				controllurl = "/web/student_bind/"+wxid+"/"+qid+"/"+openid;
				response.sendRedirect( controllurl );
				return null;
			}
			
			

			log.info(weixin_appid+"log openid controllurl "+controllurl);
			
			response.sendRedirect( controllurl );
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOpenid web page", e);
			
		}	
		
		return null;		
	}
	
	
	@RequestMapping("/web/student_bind/{wxidx}/{qidx}/{openidx}")
	public String student_bind(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxidx") Integer wxid,
			@PathVariable("qidx") Integer qid,
			@PathVariable("openidx") String openid
			) {
		try{
			//如果session为空,则返回关注页面
//			modelMap.addAttribute("wxid", wxid);
//			modelMap.addAttribute("id", id);
//			modelMap.addAttribute("openid", openid);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/questionnaire/student_bind";
	}
	
	@ResponseBody
	@RequestMapping("/web/student_bind/submit")
	public String student_bind_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "qid", required = true) Integer qid,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "school", required = true) Integer school,
			@RequestParam(value = "department", required = true) Integer department,
			@RequestParam(value = "professional", required = true) Integer professional,
			@RequestParam(value = "grades", required = true) Integer grades,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "studentId", required = true) String studentId,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "email", required = true) String email
			) {
		try{
			String controllurl = "";
			ReturnObject<Student> rm = studentService.student_bind_save(request, wxid,qid,openid,school,department,professional,grades,name,studentId,mobile,email);
			if(rm.getCode() == 1){//返向成功
				controllurl = "/web/"+wxid+"/h"+qid+"/"+openid;
			}
			rm.setAction(controllurl);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
