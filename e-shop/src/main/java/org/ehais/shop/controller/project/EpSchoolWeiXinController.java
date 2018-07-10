package org.ehais.shop.controller.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.project.HaiBegOffMapper;
import org.ehais.shop.model.project.HaiBegOff;
import org.ehais.shop.model.project.HaiBegOffExample;
import org.ehais.shop.model.project.HaiBegOffStatistics;
import org.ehais.shop.model.project.HaiBegOffUser;
import org.ehais.shop.service.ProjectBegOffService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ExcelUtils;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.utils.WeiXinTemplateMessageUtils;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**临时项目，所以这里使用旧表，不创建新表，故用了奇怪的字段表示
 * hai_users表
 * user_name:学号
 * nickname:卡号
 * realname:姓名
 * question:班级
 * answer:上级工号
 * alias:身份
 * @author lgj628
 *
 */

@Controller
@RequestMapping("/")
public class EpSchoolWeiXinController extends EhaisCommonController {

//	protected Integer default_store_id = Integer.valueOf(ResourceUtil.getProValue("default_store_id"));
//	protected Integer default_public_id = Integer.valueOf(ResourceUtil.getProValue("default_public_id"));
//	protected String website = ResourceUtil.getProValue("website");
//	protected String defaultimg = ResourceUtil.getProValue("defaultimg");
//	protected String weixin_appid = ResourceUtil.getProValue("weixin_appid");
//	protected String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
//	protected String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiBegOffMapper haiBegOffMapper;
	@Autowired
	private ProjectBegOffService projectBegOffService;
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=EPDDmenjin;";
	private String sa = "ThirdPartyWXApplication";
	private String pwd = "WXApplication123";
	private String DRIVE_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	
	//http://442b4df3.ngrok.io/ep_school_bind
	@RequestMapping("/ep_school_bind")
	public String bind(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_bind" , "snsapi_base");
				}else if(StringUtils.isNotBlank(code)){
					
					WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(default_store_id);
					OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
					if(open == null || open.getOpenid() == null) return "/ep_school/web/subscribe";
					
					request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
					
					AccessToken token = WeiXinUtil.getAccessToken(default_store_id, wp.getAppid(), wp.getSecret());
					WeiXinUserInfo wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
					
					if(wxUser.getSubscribe() == null || wxUser.getSubscribe().intValue() != 1){
						//进入关注页面
						return "/ep_school/web/subscribe";
					}
					
					
					
					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,open.getOpenid());
					if(users != null){//已绑定的学生，显示学生信息
						modelMap.addAttribute("users", users);
					}
					
					return "/ep_school/web/begbind";
					
				}
			}else{
//				if(this.isLocalHost(request)){
//					String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
//					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
//					if(users != null){//已绑定的学生，显示学生信息
//						modelMap.addAttribute("users", users);
//					}
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ep_school/web/begbind";
	}
	
	
	@ResponseBody
	@RequestMapping("/ep_school_bind_submit")
	public String bind_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "user_name", required = true) String user_name,
			@RequestParam(value = "realname", required = true) String realname
			) throws Exception {	
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		if(StringUtils.isBlank(openid)){
			rm.setMsg("102提示：网络延时，请稍后微信进入再试！");
			return this.writeJson(rm);
		}
		try{
			EHaiUsersExample exp = new EHaiUsersExample();
			exp.createCriteria()
			.andUserNameEqualTo(user_name.trim())
			.andRealnameEqualTo(realname.trim())
			.andStoreIdEqualTo(default_store_id);
			List<EHaiUsers> list = eHaiUsersMapper.selectByExample(exp);
			if(list == null || list.size() == 0){
				rm.setMsg("不存在你的学生信息<br>请联系学校管理员录入您的信息后再绑定");
				return this.writeJson(rm);
			}
			EHaiUsers user = list.get(0);
			user.setOpenid(openid);
			eHaiUsersMapper.updateByPrimaryKey(user);
			rm.setCode(1);
			rm.setMsg("帐号信息绑定成功");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "101提示：网络延时请稍后再试");}});
	}
	
	
	//http://442b4df3.ngrok.io/ep_school_begoff
	@RequestMapping("/ep_school_begoff")
	public String begoff(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code) {	
	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begoff" , "snsapi_base");
				}else if(StringUtils.isNotBlank(code)){
					WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(default_store_id);
					OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
					if(open == null || open.getOpenid() == null) return "/ep_school/web/subscribe";
					
					request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
					
					AccessToken token = WeiXinUtil.getAccessToken(default_store_id, wp.getAppid(), wp.getSecret());
					WeiXinUserInfo wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
					
					if(wxUser.getSubscribe() == null || wxUser.getSubscribe().intValue() != 1){
						//进入关注页面
						return "/ep_school/web/subscribe";
					}
					
					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,open.getOpenid());
					if(users == null){//未绑定的学生，去到绑定页面
						return "/ep_school/web/begbind";
					}
					modelMap.addAttribute("users", users);
					System.out.println("用户身份："+users.getAlias());
					if(!users.getAlias().equals("学生")) {
						return "/ep_school/web/begbind";
					}
					
					
					
					HaiBegOffExample exp = new HaiBegOffExample();
					exp.createCriteria()
					.andUserIdEqualTo(users.getUserId())
					.andTeacherUserIdIsNull()
					.andStoreIdEqualTo(default_store_id);
					exp.setOrderByClause("begoff_id desc");
					
					List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
					if(list!=null && list.size()>0){
						HaiBegOff begOff = list.get(0);
						Date createDate = begOff.getCreateDate();
						if(System.currentTimeMillis() - createDate.getTime() < 3 * 60 * 60 * 1000){
							modelMap.addAttribute("begOff", list.get(0));
						}
						
						
					}
					
					
					return "/ep_school/web/begoff";
				}
			}else{
				if(this.isLocalHost(request)){
					String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
					Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
					
					HaiBegOffExample exp = new HaiBegOffExample();
					exp.createCriteria()
					.andUserIdEqualTo(userId)
					.andTeacherUserIdIsNull()
					.andStoreIdEqualTo(default_store_id);
					
					List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
					if(list!=null && list.size()>0){
						modelMap.addAttribute("begOff", list.get(0));
					}
					
					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
					modelMap.addAttribute("users", users);
					return "/ep_school/web/begoff";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/ep_school/web/subscribe";
	}
	
	
	@ResponseBody
	@RequestMapping("/ep_school_begoff_submit")
	public String begoff_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "number", required = true) Integer number,
			@RequestParam(value = "reason", required = false) String reason) {	
		
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		if(StringUtils.isBlank(openid)){
			rm.setMsg("102提示：网络延时，请稍后再试！");
			return this.writeJson(rm);
		}
		try{
			EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
			if(users == null){
				rm.setMsg("未绑定学生信息");
				return this.writeJson(rm);
			}
			
			if(StringUtils.isBlank(users.getAnswer())){
				rm.setMsg("您无对应的班主任，请联系管理员完善信息");
				return this.writeJson(rm);
			}
			
			EHaiUsers teacher = eHaiUsersMapper.userNameByStore(default_store_id, users.getAnswer());
			if(teacher == null){
				rm.setMsg("班主任信息未设置");
				return this.writeJson(rm);
			}
			
			if(StringUtils.isBlank(teacher.getOpenid())){
				rm.setMsg("班主任信息未绑定微信，请告知");
				return this.writeJson(rm);
			}
			
			
			HaiBegOffExample exp = new HaiBegOffExample();
			exp.createCriteria()
			.andUserIdEqualTo(users.getUserId())
			.andTeacherUserIdIsNull()
			.andStoreIdEqualTo(default_store_id);
			
			List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
			if(list!=null && list.size()>0){
				rm.setMsg("存在未审批的请假申请");
				return this.writeJson(rm);
			}
			
			EHaiUsers teacherModel = this.getUserByStudentNo(users.getAnswer());
			EHaiUsers departmentModel = null;
			EHaiUsers leaderModel = null;
			
			if(teacherModel!=null)departmentModel = this.getUserByStudentNo(teacherModel.getAnswer());
			if(departmentModel!=null)leaderModel = this.getUserByStudentNo(departmentModel.getAnswer());
			
			
			HaiBegOff begoff = new HaiBegOff();
			begoff.setUserId(users.getUserId());
			begoff.setNumber(number);
			begoff.setReason(reason);
			begoff.setCreateDate(new Date());
			
			if(teacherModel!=null)begoff.setTeacherUserId(teacherModel.getUserId());
			begoff.setTeacherApprove(null);
			begoff.setTeacherApproveTime(null);
			
			if(departmentModel!=null)begoff.setDepartmentUserId(departmentModel.getUserId());
			begoff.setDepartmentApprove(null);
			begoff.setDepartmentApproveTime(null);
			
			if(leaderModel!=null)begoff.setLeaderUserId(leaderModel.getUserId());
			begoff.setLeaderApprove(null);
			begoff.setLeaderApproveTime(null);
			
			
			begoff.setStoreId(default_store_id);
			
			haiBegOffMapper.insert(begoff);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("keyword1", users.getRealname());
			map.put("keyword2", DateUtil.formatDate(begoff.getCreateDate(), DateUtil.FORMATSTR_2));
			
			//发送微信推送通知
			WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
					"z5twR49G4wwb7esa_wpNrmB9mDudOnEjQ65rnCRd1hE", 
					teacher.getOpenid(), 
					request.getScheme()+"://"+request.getServerName()+"/ep_school_begapprove!"+begoff.getBegoffId()
					, reason, map, "请假天数:"+begoff.getNumber());
			
			rm.setCode(1);
			rm.setMsg("请假申请已成功");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "101提示：网络延时请稍后再试");}});
	}
	
	
	//http://442b4df3.ngrok.io/ep_school_begapprove_list
	/**
	 * 班主任，部长，学生处的用户查看针对自己的请假信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ep_school_begapprove_list")
	public String begapprove_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begapprove_list" , "snsapi_base");
				}else if(StringUtils.isNotBlank(code)){
					WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(default_store_id);
					OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
					if(open == null || open.getOpenid() == null) return "/ep_school/web/subscribe";
					
					request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
					
					AccessToken token = WeiXinUtil.getAccessToken(default_store_id, wp.getAppid(), wp.getSecret());
					WeiXinUserInfo wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
					
					if(wxUser.getSubscribe() == null || wxUser.getSubscribe().intValue() != 1){
						//进入关注页面
						return "/ep_school/web/subscribe";
					}
					
					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,open.getOpenid());
					if(users == null){//未绑定的学生，去到绑定页面
						return "/ep_school/web/begbind";
					}
					modelMap.addAttribute("users", users);
					
					//判断用户身份
					String alias = users.getAlias();
					if(StringUtils.isBlank(alias)){//身份为空，则不是合法用户
						return "/ep_school/web/begbind";
					}
					System.out.println("用户身份："+users.getAlias());
					if(users.getAlias().equals("学生")) {
						return "/ep_school/web/begbind";
					}
					//查找对应身份的未审批请假信息
					HaiBegOffExample exp = new HaiBegOffExample();
					HaiBegOffExample.Criteria boe = exp.createCriteria();
					boe.andStoreIdEqualTo(default_store_id);
					
					if(alias.equals("班主任")){
						boe.andTeacherUserIdEqualTo(users.getUserId()).andTeacherApproveIsNull();
					}else if(alias.equals("部长")){
						boe.andTeacherApproveEqualTo(1);
						boe.andNumberGreaterThan(1);
						boe.andDepartmentUserIdEqualTo(users.getUserId()).andDepartmentApproveIsNull();
					}else if(alias.equals("学生处")){
						boe.andTeacherApproveEqualTo(1);
						boe.andDepartmentApproveEqualTo(1);
						boe.andNumberGreaterThan(2);
						boe.andLeaderUserIdEqualTo(users.getUserId()).andLeaderApproveIsNull();
					}else{
						return "/ep_school/web/subscribe";//非法身份进入
					}
					
					
					exp.setOrderByClause("begoff_id desc");
					
					List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
					List<Long> userList = new ArrayList<Long>();
					for (HaiBegOff haiBegOff : list) {
						userList.add(haiBegOff.getUserId());
					}
					List<HaiBegOffUser> listBegOffUser = new ArrayList<HaiBegOffUser>();
					
					if(userList.size()>0){
						EHaiUsersExample expu = new EHaiUsersExample();
						expu.createCriteria().andUserIdIn(userList).andStoreIdEqualTo(default_store_id);
						List<EHaiUsers> user_list = eHaiUsersMapper.selectByExample(expu);
						if(user_list.size() == 0){
							
						}
						
						for (HaiBegOff h : list) {
							HaiBegOffUser b = new HaiBegOffUser();
							b.setUserId(h.getUserId());
							b.setNumber(h.getNumber());
							b.setReason(h.getReason());
							b.setBegoffId(h.getBegoffId());
							for(EHaiUsers u : user_list){
								if(u.getUserId().longValue() == h.getUserId().longValue()){
									b.setUsername(u.getRealname());
									break;
								}
							}
							listBegOffUser.add(b);
						}
						modelMap.addAttribute("listBegOffUser", listBegOffUser);
						
					}
					
					
					
					return "/ep_school/web/begapprove_list";
				}
			}else{
				if(this.isLocalHost(request)){
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/ep_school/web/subscribe";
	}
	
	
	@RequestMapping("/ep_school_begapprove!{begid}")
	public String begapprove(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "begid") Integer begid,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begapprove!"+begid , "snsapi_base");
				}else if(StringUtils.isNotBlank(code)){
					
					
					WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(default_store_id);
					OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
					if(open == null || open.getOpenid() == null) return "/ep_school/web/subscribe";
					
					request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
					
					AccessToken token = WeiXinUtil.getAccessToken(default_store_id, wp.getAppid(), wp.getSecret());
					WeiXinUserInfo wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
					
					if(wxUser.getSubscribe() == null || wxUser.getSubscribe().intValue() != 1){
						//进入关注页面
						return "/ep_school/web/subscribe";
					}
					

					EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,open.getOpenid());
					if(users == null){//未绑定的学生，去到绑定页面
						return "/ep_school/web/begbind";
					}
					
					HaiBegOffExample exp = new HaiBegOffExample();
					exp.createCriteria()
					.andBegoffIdEqualTo(begid)
					.andStoreIdEqualTo(default_store_id);
					
					List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
					if(list != null && list.size() > 0){
						HaiBegOff haiBegOff = list.get(0);
						modelMap.addAttribute("haiBegOff", haiBegOff);
						
						EHaiUsers student = eHaiUsersMapper.get_hai_users_info(default_store_id, haiBegOff.getUserId());
						modelMap.addAttribute("student", student);
						
						
						Long teacherUserId = haiBegOff.getTeacherUserId();
						Long departmentUserId = haiBegOff.getDepartmentUserId();
						Long leaderUserId = haiBegOff.getLeaderUserId();
						
						List<Long> userIds = new ArrayList<Long>();
						if(teacherUserId!=null && teacherUserId.longValue()>0 && haiBegOff.getTeacherApprove() != null)userIds.add(teacherUserId);
						if(departmentUserId!=null && departmentUserId.longValue()>0 && haiBegOff.getDepartmentApprove() != null)userIds.add(departmentUserId);
						if(leaderUserId!=null && leaderUserId.longValue()>0 && haiBegOff.getLeaderApprove() != null)userIds.add(leaderUserId);
						
						if(userIds.size()>0){
							
							List<EHaiUsers> user_list =  eHaiUsersMapper.inUserIdList(StringUtils.join(userIds.toArray(), ","));
							
							for (EHaiUsers eHaiUsers : user_list) {
								if(teacherUserId!=null && teacherUserId.longValue()>0 && teacherUserId.longValue() == eHaiUsers.getUserId().longValue()){
									modelMap.addAttribute("teacher", eHaiUsers.getRealname()+eHaiUsers.getAlias()+"审核"+((haiBegOff.getTeacherApprove() != null && haiBegOff.getTeacherApprove() == 1)?"通过":"不通过"));
								}
								if(departmentUserId!=null && departmentUserId.longValue()>0 && departmentUserId.longValue() == eHaiUsers.getUserId().longValue()){
									modelMap.addAttribute("department", eHaiUsers.getRealname()+eHaiUsers.getAlias()+"审核"+((haiBegOff.getDepartmentApprove() != null && haiBegOff.getDepartmentApprove() == 1)?"通过":"不通过"));
								}
								if(leaderUserId!=null && leaderUserId.longValue()>0 && leaderUserId.longValue() == eHaiUsers.getUserId().longValue()){
									modelMap.addAttribute("leader", eHaiUsers.getRealname()+eHaiUsers.getAlias()+"审核"+((haiBegOff.getLeaderApprove() != null && haiBegOff.getLeaderApprove() == 1)?"通过":"不通过"));
								}
							}
						}
						
						if(users.getAlias().equals("班主任") && haiBegOff.getTeacherApprove() == null){
							modelMap.addAttribute("permit","permit");
						}
						
						if(users.getAlias().equals("部长") && haiBegOff.getDepartmentApprove() == null && haiBegOff.getTeacherApprove() != null && haiBegOff.getTeacherApprove().intValue() == 1){
							modelMap.addAttribute("permit","permit");
						}
					
						if(users.getAlias().equals("学生处") && haiBegOff.getLeaderApprove() == null && haiBegOff.getDepartmentApprove() != null && haiBegOff.getDepartmentApprove().intValue() == 1){
							modelMap.addAttribute("permit","permit");
						}
						
					}
					
					return "/ep_school/web/begapprove";
					
					
				}
			}else{
				
//				String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
//				EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
				
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ep_school/web/begapprove";
	}
	
	
	
	private String BegOffApprove(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			ReturnObject<HaiBegOff> rm ,
			EHaiUsers users ,
			HaiBegOff begoff,Integer approve) throws Exception {
		
		EHaiUsers student = eHaiUsersMapper.selectByPrimaryKey(begoff.getUserId());
		if(student==null){
			rm.setMsg("不存在请假申请的学生");
			return this.writeJson(rm);
		}
		
		if(users.getAlias().equals("班主任")){
			begoff.setTeacherUserId(users.getUserId());
			begoff.setTeacherApprove(approve);
			begoff.setTeacherApproveTime(new Date());
		}else if(users.getAlias().equals("部长")){
			begoff.setDepartmentUserId(users.getUserId());
			begoff.setDepartmentApproveTime(new Date());
			begoff.setDepartmentApprove(approve);
		}else if(users.getAlias().equals("学生处")){
			begoff.setLeaderApprove(approve);
			begoff.setLeaderApproveTime(new Date());
			begoff.setLeaderUserId(users.getUserId());
		}
		
		haiBegOffMapper.updateByPrimaryKey(begoff);
		
		if(users.getAlias().equals("班主任") ){
			
			
			if(approve == 1){//审批通过
				
				if(begoff.getNumber() == 1){					

					//微信推送通知学生结果
					Map<String,String> map = new HashMap<String,String>();
					map.put("keyword1", student.getRealname());
					map.put("keyword2", begoff.getNumber().toString());
					map.put("keyword3", begoff.getReason());
					map.put("keyword4", users.getRealname());
					map.put("keyword5", "通过");
					
					//发送微信推送通知
					WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
							"pSX75qkBnjI9eseADMZGSbxDXd-zn98H220LLV_-QyU", 
							student.getOpenid(), 
							""
							, "请假审批通知", map, "");
					
					//通知开闸
					this.approve_open_door(request, student);
				}else{
					//大于2天通知部长审批
					EHaiUsers depart = eHaiUsersMapper.userNameByStore(default_store_id, users.getAnswer());
					if(depart != null){
						//微信推送通知部长审批
						
						Map<String,String> map = new HashMap<String,String>();
						map.put("keyword1", student.getRealname());
						map.put("keyword2", DateUtil.formatDate(begoff.getCreateDate(), DateUtil.FORMATSTR_2));
						
						//发送微信推送通知
						WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
								"z5twR49G4wwb7esa_wpNrmB9mDudOnEjQ65rnCRd1hE", 
								depart.getOpenid(), 
								request.getScheme()+"://"+request.getServerName()+"/ep_school_begapprove!"+begoff.getBegoffId()
								, begoff.getReason(), map, "请假天数:"+begoff.getNumber());
						
						
					}
					
				}
				
			}else{//审批不通过
				//微信推送通知学生结果
				Map<String,String> map = new HashMap<String,String>();
				map.put("keyword1", student.getRealname());
				map.put("keyword2", begoff.getNumber().toString());
				map.put("keyword3", begoff.getReason());
				map.put("keyword4", users.getRealname());
				map.put("keyword5", "不通过");
				
				//发送微信推送通知
				WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
						"pSX75qkBnjI9eseADMZGSbxDXd-zn98H220LLV_-QyU", 
						student.getOpenid(), 
						""
						, "请假审批通知", map, "");
			}
			
			
			
			
		}else if(users.getAlias().equals("部长")){
			
			if(approve == 1){//复审批通过
				if(begoff.getNumber() == 2){
					//微信推送通知学生结果
					Map<String,String> map = new HashMap<String,String>();
					map.put("keyword1", student.getRealname());
					map.put("keyword2", begoff.getNumber().toString());
					map.put("keyword3", begoff.getReason());
					map.put("keyword4", users.getRealname());
					map.put("keyword5", approve == 1 ? "通过" : "不通过");
					
					//发送微信推送通知
					WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
							"pSX75qkBnjI9eseADMZGSbxDXd-zn98H220LLV_-QyU", 
							student.getOpenid(), 
							""
							, "请假审批通知", map, "");
					
					//通知开闸
					this.approve_open_door(request, student);
				}else{
					//大于3天通知部长审批
					EHaiUsers leader = eHaiUsersMapper.userNameByStore(default_store_id, users.getAnswer());
					if(leader != null){
						//微信推送通知部长审批

						Map<String,String> map = new HashMap<String,String>();
						map.put("keyword1", student.getRealname());
						map.put("keyword2", DateUtil.formatDate(begoff.getCreateDate(), DateUtil.FORMATSTR_2));
						
						//发送微信推送通知
						WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
								"z5twR49G4wwb7esa_wpNrmB9mDudOnEjQ65rnCRd1hE", 
								leader.getOpenid(), 
								request.getScheme()+"://"+request.getServerName()+"/ep_school_begapprove!"+begoff.getBegoffId()
								, begoff.getReason(), map, "请假天数:"+begoff.getNumber());
						
						
						
					}
					
				}
			}else{//复审不通过
				//微信推送通知学生结果
				Map<String,String> map = new HashMap<String,String>();
				map.put("keyword1", student.getRealname());
				map.put("keyword2", begoff.getNumber().toString());
				map.put("keyword3", begoff.getReason());
				map.put("keyword4", users.getRealname());
				map.put("keyword5", "不通过");
				
				//发送微信推送通知
				WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
						"pSX75qkBnjI9eseADMZGSbxDXd-zn98H220LLV_-QyU", 
						student.getOpenid(), 
						""
						, "请假审批通知", map, "");
			}
			
		}else if(users.getAlias().equals("学生处")){
			
			if(begoff.getNumber() >= 3){
				//微信推送通知学生结果
				Map<String,String> map = new HashMap<String,String>();
				map.put("keyword1", student.getRealname());
				map.put("keyword2", begoff.getNumber().toString());
				map.put("keyword3", begoff.getReason());
				map.put("keyword4", users.getRealname());
				map.put("keyword5", approve == 1 ? "通过" : "不通过");
				
				//发送微信推送通知
				WeiXinTemplateMessageUtils.sendTemplateMessage(default_store_id, weixin_appid, weixin_appsecret, 
						"pSX75qkBnjI9eseADMZGSbxDXd-zn98H220LLV_-QyU", 
						student.getOpenid(), 
						""
						, "请假审批通知", map, "");
				
				//通知开闸
				this.approve_open_door(request, student);
			}
		}
		
		
		return null;
	}
	
	
	
	
	/**
	 * 单个审核
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param begid
	 * @param approve审批结果：1通过，2不通过
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ep_school_begapprove_submit")
	public String begapprove_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "begid", required = true) Integer begid,
			@RequestParam(value = "approve", required = true) Integer approve) {	
		
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		if(StringUtils.isBlank(openid)){
			rm.setMsg("102提示：网络延时，请稍后再试！");
			return this.writeJson(rm);
		}
		
		modelMap.addAttribute("begid", begid);
		
		try{
			EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
			if(users == null){
				rm.setMsg("未绑定工号信息");
				return this.writeJson(rm);
			}
			
						
			HaiBegOffExample exp = new HaiBegOffExample();
			exp.createCriteria()
			.andBegoffIdEqualTo(begid)
			.andStoreIdEqualTo(default_store_id);
			
			List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
			if(list==null || list.size()==0){
				rm.setMsg("不存在请假申请信息");
				return this.writeJson(rm);
			}
			
			HaiBegOff begoff = list.get(0);
			//请假公共方法
			String msg = this.BegOffApprove(modelMap, request, response,rm,users, begoff,approve);
			if(msg!=null) {
				return msg;
			}
			
			
			
			
			rm.setCode(1);
			rm.setMsg("请假审批已成功");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "101提示：网络延时请稍后再试");}});
	}
	
	/**
	 * 批量审批通过
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param approve
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ep_school_begapprove_submit_batch")
	public String begapprove_submit_batch(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "approve", required = true) Integer approve) {	
		ReturnObject<HaiBegOff> rm = new ReturnObject<HaiBegOff>();
		rm.setCode(0);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		if(StringUtils.isBlank(openid)){
			rm.setMsg("102提示：网络延时，请稍后再试！");
			return this.writeJson(rm);
		}
		
		
		try{
			EHaiUsers users = eHaiUsersMapper.userInfoOpenIdStore(default_store_id ,openid);
			if(users == null){
				rm.setMsg("未绑定工号信息");
				return this.writeJson(rm);
			}
			
			
			modelMap.addAttribute("users", users);
			
			//判断用户身份
			String alias = users.getAlias();
			if(StringUtils.isBlank(alias)){//身份为空，则不是合法用户
				rm.setMsg("非法身份信息");
				return this.writeJson(rm);
			}
			//查找对应身份的未审批请假信息
			HaiBegOffExample exp = new HaiBegOffExample();
			HaiBegOffExample.Criteria boe = exp.createCriteria();
			boe.andStoreIdEqualTo(default_store_id);
			
			if(alias.equals("班主任")){
				boe.andTeacherUserIdEqualTo(users.getUserId()).andTeacherApproveIsNull();
			}else if(alias.equals("部长")){
				boe.andDepartmentUserIdEqualTo(users.getUserId()).andDepartmentApproveIsNull();
			}else if(alias.equals("学生处")){
				boe.andLeaderUserIdEqualTo(users.getUserId()).andLeaderApproveIsNull();
			}else{
				rm.setMsg("非法身份信息");
				return this.writeJson(rm);
			}
			
			
			exp.setOrderByClause("begoff_id desc");
			
			List<HaiBegOff> list = haiBegOffMapper.selectByExample(exp);
			List<Long> userList = new ArrayList<Long>();
			for (HaiBegOff haiBegOff : list) {
				this.BegOffApprove(modelMap, request, response,rm,users, haiBegOff,approve);
			}
			
			
			
			
			rm.setCode(1);
			rm.setMsg("批量审批已成功");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "101提示：网络延时请稍后再试");}});
	}
	
	
	@RequestMapping("/admin/ep_school_user_list")
	public String user_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		//统计班级
		List<String> className = eHaiUsersMapper.distinctUsers(default_store_id, "question");
		modelMap.addAttribute("className", className);
		
		return "/ep_school/user/user_list";
	}
	
	
	@ResponseBody
	@RequestMapping("/admin/ep_school_user_list_json")
	public String user_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "className", required = false) String className
			) {
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		try{
			List<EHaiUsers> list = eHaiUsersMapper.selectUsersLike(default_store_id, keyword, "user_id desc", condition.getStart(), condition.getRows());
			Integer total = eHaiUsersMapper.selectUsersLikeCount(default_store_id, keyword);
			rm.setCode(1);
			rm.setRows(list);
			rm.setTotal(total);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "fail");}});
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/admin/ep_school_excel.upd", method = RequestMethod.POST)
	public String ep_school_excel(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {

		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		try {
			
			String req = UploadUtils.upload_file(request, response);
			System.out.println("上传路径："+req);
			JSONObject json = JSONObject.fromObject(req);
			System.out.println(json.getString("msg"));
			
	        int iIndex = json.getString("msg").lastIndexOf(".");
	        String ext = (iIndex < 0) ? "" : json.getString("msg").substring(iIndex + 1).toLowerCase();
	        if (!"xls,xlsx".contains(ext) || "".contains(ext)) {
	            System.out.println("文件类型不是EXCEL！");
	        }
	        
	        boolean isE2007 = false;    //判断是否是excel2007格式 
	        if(ext.endsWith("xlsx")) isE2007 = true;  
	        InputStream input = new FileInputStream(new File(json.getString("msg")));  //建立输入流  
	        Workbook wb  = null;  
            //根据文件格式(2003或者2007)来初始化 
	        System.out.println(isE2007);
            if(isE2007){
            	wb = new XSSFWorkbook(input);  
            }else{
            	wb = new HSSFWorkbook(input); 
            }
            DecimalFormat    df   = new DecimalFormat("0");   
            
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器 
            int rowNum=sheet.getLastRowNum();//有多少行
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                if(row.getRowNum() == 0||row.getRowNum() == 1)continue;//排除第一行，第二行
//                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
//                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                EHaiUsers users = new EHaiUsers();
                if(row.getCell(1)!=null && StringUtils.isNotEmpty(String.valueOf(row.getCell(1))) && StringUtils.isNotBlank(String.valueOf(row.getCell(1)))){
                	
                	if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                		users.setUserName(df.format(row.getCell(1).getNumericCellValue()).trim());//学号
                	}else if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_STRING){
                		users.setUserName(row.getCell(1).getStringCellValue().trim());//学号
                	}
                	
                    if(row.getCell(2)!=null){
                    	if(row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                    		users.setNickname(df.format(row.getCell(2).getNumericCellValue()).trim());//卡号
                    	}else if(row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING){
                    		users.setNickname(row.getCell(2).getStringCellValue().trim());//卡号
                    	}
                    }else{
                    	users.setNickname("");
                    }
                    
                    //内外宿舍
                    if(row.getCell(3)!=null && row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
                    	users.setFlag(Byte.valueOf("1"));
                    }else {
                    	users.setFlag(Byte.valueOf("2"));
                    }
                    
                    
                	if(row.getCell(4)!=null){
                		users.setRealname(row.getCell(4).getStringCellValue().trim());//姓名
                	}else{
                		users.setRealname("");
                	}
                	
                	if(row.getCell(5)!=null){
                		users.setQuestion(row.getCell(5).getStringCellValue().trim());//班级
                	}else{
                		users.setQuestion("");
                	}
                    
                	if(row.getCell(6)!=null){
                		if(row.getCell(6).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                    		users.setAnswer(df.format(row.getCell(6).getNumericCellValue()).trim());//上级工号
                    	}else if(row.getCell(6).getCellType() == HSSFCell.CELL_TYPE_STRING){
                    		users.setAnswer(row.getCell(6).getStringCellValue().trim());//上级工号
                    	}
                	}else{
                		users.setAnswer("");
                	}
                	users.setAlias("学生");
                	EHaiUsers ss= eHaiUsersMapper.getdusername(users.getUserName());
                	if(ss==null){
                		 users.setStoreId(default_store_id);
                		int nym=eHaiUsersMapper.insertSelective(users);
                		System.out.println(nym);
                	}
                	
                	
                	/////////////////////////////////////////////////////////班主任插入
                	EHaiUsers ss1= eHaiUsersMapper.getdusername(users.getAnswer());
                	
                	EHaiUsers bangzhuren=new EHaiUsers();
                	if(row.getCell(7)!=null){                		
                		bangzhuren.setRealname(row.getCell(7).getStringCellValue().trim());//姓名                	
                	}else{
                		bangzhuren.setRealname("");
                	}
                	if(row.getCell(8)!=null){
                		if(row.getCell(8).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                			bangzhuren.setAnswer(df.format(row.getCell(8).getNumericCellValue()).trim());//上级工号
                    	}else if(row.getCell(8).getCellType() == HSSFCell.CELL_TYPE_STRING){
                    		bangzhuren.setAnswer(row.getCell(8).getStringCellValue().trim());//上级工号
                    	}
                		
                	}else{
                		bangzhuren.setAnswer("");
                	}
                	if(ss1==null){
                		bangzhuren.setStoreId(default_store_id);
                		bangzhuren.setUserName(users.getAnswer());//工号
                		bangzhuren.setAlias("班主任");
                		int nym=eHaiUsersMapper.insertSelective(bangzhuren);
                		System.out.println(bangzhuren);
                	}
                	
                	
                	
                	
                	//////////////////////////////////////////////////////////////////部长插入
                	EHaiUsers bangzhuren1=new EHaiUsers();
                	if(row.getCell(9)!=null){
                		
                		bangzhuren1.setRealname(row.getCell(9).getStringCellValue().trim());//姓名
                		
                		System.out.println(bangzhuren);
                	}else{
                		bangzhuren1.setRealname("");
                	}
                	if(row.getCell(10)!=null){
                		if(row.getCell(10).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                			bangzhuren1.setAnswer(df.format(row.getCell(10).getNumericCellValue()).trim());//上级工号
                    	}else if(row.getCell(10).getCellType() == HSSFCell.CELL_TYPE_STRING){
                    		bangzhuren1.setAnswer(row.getCell(10).getStringCellValue().trim());//上级工号
                    	}
                		
                	}else{
                		bangzhuren1.setAnswer("");
                	}
                	
                	EHaiUsers ss2= eHaiUsersMapper.getdusername(bangzhuren.getAnswer());
                	if(ss2==null){
                		bangzhuren1.setStoreId(default_store_id);
                		bangzhuren1.setUserName(bangzhuren.getAnswer());//工号
                		bangzhuren1.setAlias("部长");
                		int nym=eHaiUsersMapper.insertSelective(bangzhuren1);
                		System.out.println(bangzhuren1);
                	
                	}
                	
                	
                	
                	/////////////////////////////////////////////////////////////////////学生处插入
                	EHaiUsers bangzhuren2=new EHaiUsers();
                	if(row.getCell(10)!=null){
                		
                		bangzhuren2.setRealname(row.getCell(11).getStringCellValue().trim());//姓名
                	}else{
                		bangzhuren2.setRealname("");
                	}
                	if(row.getCell(10)!=null){
                		
                	}else{
                		bangzhuren2.setAnswer("");
                	}
                	
                	EHaiUsers ss3= eHaiUsersMapper.getdusername(bangzhuren1.getAnswer());
                	//
                	if(ss3==null){
                	    bangzhuren2.setAlias("学生处");
                		bangzhuren2.setStoreId(default_store_id);
                		bangzhuren2.setUserName(bangzhuren1.getAnswer());//工号
                		int nym=eHaiUsersMapper.insertSelective(bangzhuren2);
                		System.out.println(bangzhuren2);
                	}
                	
                	
               /*     if(StringUtils.isNotBlank(users.getUserName())){
                    	EHaiUsersExample exp = new EHaiUsersExample();
                        exp.createCriteria()
                        .andUserNameEqualTo(users.getUserName())
                        .andStoreIdEqualTo(default_store_id);
                        List<EHaiUsers> list = eHaiUsersMapper.selectByExample(exp);
                        if(list == null || list.size() == 0){
                        	eHaiUsersMapper.insert(users);
                        }else{
                        	EHaiUsers bean = list.get(0);
                        	bean.setNickname(users.getNickname());
                        	bean.setRealname(users.getRealname());
                        	bean.setQuestion(users.getQuestion());
                        	bean.setAnswer(users.getAnswer());
                        	bean.setAlias(users.getAlias());
                        	eHaiUsersMapper.updateByPrimaryKey(bean);
                        }
                    }*/
                    
                }
                
                
                
                
                
                /* 
                * user_name:学号
                * nickname:卡号
                * realname:姓名
                * question:班级
                * answer:上级工号
                * alias:身份
                * salt:1内宿，2外宿
                */
                
                /*
                while (cells.hasNext()) {  
                    Cell cell = cells.next();  
                    System.out.print("|Cell #" + cell.getColumnIndex()+"|"+"");  
                    
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                    case HSSFCell.CELL_TYPE_NUMERIC:  
                        System.out.print(df.format(cell.getNumericCellValue()));  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING:  
                        System.out.print(cell.getStringCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN:  
                        System.out.print(cell.getBooleanCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA:  
                        System.out.print(cell.getCellFormula());  
                        break;  
                    default:  
                        System.out.print("unsuported sell type");  
                    break;  
                    } 
                    System.out.println("");
                }  
                */
                
            }  
            
	        rm.setCode(1);
	        rm.setMsg("导入成功");
			return this.writeJson(rm);
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/admin/ep_school_user_delete")
	public String ep_school_user_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userId", required = true) Long userId
			) {
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		try{
			EHaiUsersExample exp = new EHaiUsersExample();
            exp.createCriteria()
            .andUserIdEqualTo(userId)
            .andStoreIdEqualTo(default_store_id);
            eHaiUsersMapper.deleteByExample(exp);
			rm.setCode(1);
			rm.setMsg("删除成功");
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "fail");}});
	}
	
	@ResponseBody
	@RequestMapping("/testdb")
	public String testdb(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		try{
			EHaiUsersExample ue = new EHaiUsersExample();
			ue.setOrderByClause("user_id asc");
			ue.setLimitStart(0);
			ue.setLimitEnd(1);
			List<EHaiUsers> user = eHaiUsersMapper.selectByExample(ue);
			this.approve_open_door(request, user.get(0));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "success";
	}
	
	/**
	 * 对接开门的应用
	 * @param request
	 * @param cardNo
	 */
	private void approve_open_door(HttpServletRequest request,EHaiUsers student){
		log.info("======对接开门禁======================");
		try{
			Class.forName(DRIVE_NAME);
			// 连接数据库
            conn = DriverManager.getConnection(url, sa, pwd);
            // 建立Statement对象
            stmt = conn.createStatement();
            
            String sql = "insert into ThirdPartyTempAccess(CardHolderName,StudentNumberColumnName,StudentNumber,StartTime,EndTime,AccessLevelID,ProcessFlag)values(?,?,?,?,?,?,?);";
            PreparedStatement psstmt = conn.prepareStatement(sql);// 加载sql
            psstmt.setString(1, student.getRealname());
            psstmt.setString(2, "Note1");
            psstmt.setString(3, student.getUserName());
            java.sql.Date sdate = new java.sql.Date(System.currentTimeMillis());
            psstmt.setDate(4, sdate);
            java.sql.Date edate = new java.sql.Date(System.currentTimeMillis()+24*60*60*1000);
            psstmt.setDate(5, edate);
            psstmt.setInt(6, 4);
            psstmt.setInt(7, 0);
            int rowN = psstmt.executeUpdate();// 执行sql
            
            if (rowN == 1) {// 执行成功时
            	log.info("======对接成功======================");
            }else {
            	log.info("======对接失败======================");
            }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@EPermissionMethod(name="查询",intro="打开请假页面",value="projectBegOffView",relation="projectBegOffListJson",type=PermissionProtocol.URL)
	@RequestMapping("/admin/projectBegOffView")
	public String projectBegOffView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_list(request);
			modelMap.addAttribute("rm", rm);
			
			Date date = new Date();
			String startDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String endDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("startDate", startDate);
			modelMap.addAttribute("endDate", endDate);
			//统计班级
			List<String> className = eHaiUsersMapper.distinctUsers(default_store_id, "question");
			modelMap.addAttribute("className", className);
			
			
			return "/ep_school/begoff/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回请假数据",value="projectBegOffListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/admin/projectBegOffListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String projectBegOffListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "className", required = false) String className) {
		try{
			ReturnObject<HaiBegOff> rm = projectBegOffService.begoff_list_json(request, condition,null,className);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("begoff", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@RequestMapping("/admin/statisticsBegOff")
	public String statisticsBegOff(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Date date = new Date();
			String startDate =  DateUtil.formatDate(DateUtils.addDays(date, -30), DateUtil.FORMATSTR_3);
			String endDate =  DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
			modelMap.addAttribute("startDate", startDate);
			modelMap.addAttribute("endDate", endDate);
			//统计班级
			List<String> className = eHaiUsersMapper.distinctUsers(default_store_id, "question");
			modelMap.addAttribute("className", className);
			
			return "/ep_school/report/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("statisticsBegOff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/admin/statisticsBegOffJSON",method=RequestMethod.POST)
	public String statisticsBegOffJSON(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "start_date", required = true) String start_date,
			@RequestParam(value = "end_date", required = true) String end_date,
			@RequestParam(value = "className", required = false) String className
			) {	
		ReturnObject<HaiBegOffStatistics> rm = new ReturnObject<HaiBegOffStatistics>();
		rm.setCode(0);
		try{
			List<HaiBegOffStatistics> list = haiBegOffMapper.statisticsBegOff(default_store_id, start_date, end_date);
			
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return this.writeJson(rm);
	}
	
	
	

	@RequestMapping(value="/admin/statisticsBegOffExport",method=RequestMethod.POST)
	public void statisticsBegOffExport(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate
			) {	
		ReturnObject<HaiBegOffStatistics> rm = new ReturnObject<HaiBegOffStatistics>();
		rm.setCode(0);
		try{
			List<HaiBegOffStatistics> list = haiBegOffMapper.statisticsBegOff(default_store_id, startDate, endDate);
			JSONArray arr = JSONArray.fromObject(list);
			Map<String,String> headMap = new HashMap<String,String>();
			headMap.put("question", "班级");
			headMap.put("count", "请假人数");
			
			ExcelUtils.downloadExcelFile("请假统计", headMap, arr, response);
			
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
		}
		
	}
	
	@RequestMapping(value="/admin/recordBegOffExport",method=RequestMethod.POST)
	public void recordBegOffExport(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate
			) {	
		try{
			ReturnObject<HaiBegOffUser> rm = projectBegOffService.begoff_list_json(request, default_store_id, startDate, endDate);
			HaiBegOffUser bo = new HaiBegOffUser();
			ExcelUtils<HaiBegOffUser> eu = new ExcelUtils<HaiBegOffUser>(HaiBegOffUser.class);
			
			  
			eu.exportExcel(rm.getRows(), "请假记录", response);
			
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
			log.error("report", e);
		}
		
	}
	
	
	
	//通过学号/工号找用户
	private EHaiUsers getUserByStudentNo(String user_name){
		if(StringUtils.isBlank(user_name))return null;
		try{
			EHaiUsersExample ue = new EHaiUsersExample();
			ue.createCriteria().andStoreIdEqualTo(default_store_id).andUserNameEqualTo(user_name);
			List<EHaiUsers> list = eHaiUsersMapper.selectByExample(ue);
			if(list.size()>0){
				return list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/admin/epUserAdd")
	public String ep_user_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			EHaiUsers user = new EHaiUsers();
			ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
			rm.setCode(1);
			rm.setModel(user);
			rm.setAction("epUserAddSubmit");
			modelMap.addAttribute("rm", rm);
			return "/ep_school/user/user_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("statisticsBegOff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/epUserAddSubmit")
	public String ep_user_add_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiUsers users) {
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(StringUtils.isBlank(users.getUserName())){
			rm.setMsg("学号/工号不能为空");
			return this.writeJson(rm);
		}
		if(StringUtils.isBlank(users.getRealname())){
			rm.setMsg("姓名不能为空");
			return this.writeJson(rm);
		}
		try{
			EHaiUsersExample ue = new EHaiUsersExample();
			ue.createCriteria().andUserNameEqualTo(users.getUserName());//判断学号是否存在
			if(StringUtils.isNotBlank(users.getNickname()))ue.or().andNicknameEqualTo(users.getNickname()).andRealnameEqualTo(users.getRealname());
			if(StringUtils.isNotBlank(users.getQuestion()))ue.or().andRealnameEqualTo(users.getRealname()).andQuestionEqualTo(users.getQuestion());
			List<EHaiUsers> list = eHaiUsersMapper.selectByExample(ue);
			if(list.size()>0){
				rm.setMsg("存在相同的学号、工号、卡号、姓名");
				return this.writeJson(rm);
			}
			
			users.setStoreId(default_store_id);
			eHaiUsersMapper.insertSelective(users);
			
			rm.setCode(1);
			rm.setMsg("添加成功");
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("statisticsBegOff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@RequestMapping("/admin/epUserEdit")
	public String ep_user_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userId", required = true) Long userId
			) {	
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		try{
			EHaiUsersExample ue = new EHaiUsersExample();
			ue.createCriteria().andStoreIdEqualTo(default_store_id).andUserIdEqualTo(userId);
			List<EHaiUsers> list = eHaiUsersMapper.selectByExample(ue);
			rm.setModel(list.get(0));
			rm.setCode(1);
			rm.setAction("epUserEditSubmit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("readonly", "readonly='readonly'");
			
			return "/ep_school/user/user_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("statisticsBegOff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/admin/epUserEditSubmit")
	public String ep_user_edit_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiUsers users) {	
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		try{
			EHaiUsersExample ue = new EHaiUsersExample();
			ue.createCriteria().andStoreIdEqualTo(default_store_id).andUserIdEqualTo(users.getUserId());
			List<EHaiUsers> list = eHaiUsersMapper.selectByExample(ue);
			if(list.size() == 0){
				rm.setMsg("用户不存在");
				return this.writeJson(rm);
			}
			
			EHaiUsers bean = list.get(0);
			bean.setNickname(users.getNickname());
			bean.setRealname(users.getRealname());
			bean.setAlias(users.getAlias());
			bean.setQuestion(users.getQuestion());
			bean.setAnswer(users.getAnswer());
			eHaiUsersMapper.updateByPrimaryKey(bean);
			rm.setCode(1);
			rm.setMsg("更新成功");
			
			return this.writeJson(rm);
					
		}catch(Exception e){
			e.printStackTrace();
			log.error("statisticsBegOff", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
}
