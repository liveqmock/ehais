package org.ehais.shop.controller.project;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiForumMapper;
import org.ehais.shop.mapper.HaiGuestbookMapper;
import org.ehais.shop.model.HaiForum;
import org.ehais.shop.model.HaiForumExample;
import org.ehais.shop.model.HaiGuestbook;
import org.ehais.shop.model.HaiGuestbookExample;
import org.ehais.shop.model.HaiGuestbookWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class KintenglifeWeiXinController extends EhaisCommonController{

	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private HaiForumMapper haiForumMapper;
	@Autowired
	private HaiGuestbookMapper haiGuestbookMapper;
	
	
	private Integer store_id = 56;
	
	@RequestMapping("/index!{module}.kintenglife")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "module") String module,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria()
			.andModuleEqualTo(module)
			.andStoreIdEqualTo(store_id);
			List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(artExp);
			if(list == null || list.size() == 0){
				//跳转未定义图片
			}
			
			EHaiArticle article = list.get(0);
			
			modelMap.addAttribute("article", article);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/kintenglife/h5/company";
	}
	
	
	
	@RequestMapping("/list!{module}.kintenglife")
	public String list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "module") String module,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id).andModuleEqualTo(module);
			
			List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(ace);
			if(list.size()>0){
				modelMap.addAttribute("articleCat", list.get(0));
			}
			
			modelMap.addAttribute("module", module);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/kintenglife/h5/article";
	}
	
	
	@ResponseBody
	@RequestMapping("/list_json!{module}.kintenglife.json")
	public String list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "module") String module,
			@ModelAttribute EConditionObject condition ) throws Exception {	
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		try{
			
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria()
			.andModuleEqualTo(module)
			.andStoreIdEqualTo(store_id);
			artExp.setLimitStart(condition.getStart());
			artExp.setLimitEnd(condition.getRows());
			List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(artExp);
			rm.setCode(1);
			rm.setRows(list);
			return this.writeJson(rm);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
	@RequestMapping("/detail!{articleId}.kintenglife")
	public String detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "articleId") Integer articleId,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria()
			.andArticleIdEqualTo(articleId)
			.andStoreIdEqualTo(store_id);
			List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(artExp);
			
			if(list == null || list.size() == 0){
				//跳转未定义图片
			}
			
			EHaiArticle article = list.get(0);
			
			modelMap.addAttribute("article", article);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/kintenglife/h5/company";
	}
	
	
	
	
	@RequestMapping("/guestbook.kintenglife")
	public String guestbook(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/guestbook.kintenglife" ,"snsapi_userinfo");
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, store.getStoreId());
					request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
					return "/kintenglife/h5/guestbook";
				}
			}else{
				//请用微信打开
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/kintenglife/h5/guestbook";
	}
	
	
	@ResponseBody
	@RequestMapping("/guestbookSubmit.kintenglife")
	public String guestbookSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "content", required = true) String content ) throws Exception {	
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		try{
			Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(userId == null){
				rm.setMsg("请使用微信重新打开");
				return this.writeJson(rm);
			}
			
			if(StringUtils.isBlank(content)){
				rm.setMsg("请填写留言内容");
				return this.writeJson(rm);
			}
			
			//一个用户30分钟只能留言一次
			HaiForumExample fe = new HaiForumExample();
			fe.createCriteria().andStoreIdEqualTo(store_id).andUserIdEqualTo(userId);
			fe.setOrderByClause("create_date desc");
			fe.setLimitStart(0);
			fe.setLimitEnd(1);
			
			List<HaiForum> fl = haiForumMapper.selectByExample(fe);
			if(fl.size() > 0){
				HaiForum f = fl.get(0);
				long create = f.getCreateDate().getTime();
				long now = System.currentTimeMillis();
				if(now - create < 1800000){//30 * 60 * 1000
					rm.setMsg("已留过言，请30分钟后再留言！");
					return this.writeJson(rm);
				}
			}
			
			HaiForum f = new HaiForum();
			f.setContent(content);
			f.setStoreId(store_id);
			f.setUserId(userId);
			f.setCreateDate(new Date());
			f.setTableId(null);
			f.setTableName("kintenglife");
			haiForumMapper.insert(f);
			
			rm.setCode(1);
			rm.setMsg("感谢你的留言！");
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
	
	
	
	
	@RequestMapping("/business.kintenglife")
	public String Business(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/business.kintenglife" ,"snsapi_userinfo");
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, store.getStoreId());
					request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
					return "/kintenglife/h5/business";
				}
			}else{
				//请用微信打开
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/kintenglife/h5/business";
	}
	
	
	@ResponseBody
	@RequestMapping("/businessSubmit.kintenglife")
	public String businessSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiGuestbookWithBLOBs guestbook ) throws Exception {	
		ReturnObject<HaiGuestbook> rm = new ReturnObject<HaiGuestbook>();
		rm.setCode(0);
		try{
			Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(userId == null){
				rm.setMsg("请使用微信重新打开");
				return this.writeJson(rm);
			}
			
			if(StringUtils.isBlank(guestbook.getContent())){
				rm.setMsg("请填写留言内容");
				return this.writeJson(rm);
			}
			
			//一个用户30分钟只能留言一次
			HaiGuestbookExample fe = new HaiGuestbookExample();
			fe.createCriteria().andStoreIdEqualTo(store_id).andUserIdEqualTo(userId);
			fe.setOrderByClause("create_date desc");
			fe.setLimitStart(0);
			fe.setLimitEnd(1);
			
			List<HaiGuestbook> fl = haiGuestbookMapper.selectByExample(fe);
			if(fl.size() > 0){
				HaiGuestbook f = fl.get(0);
				long create = f.getCreateDate().getTime();
				long now = System.currentTimeMillis();
				if(now - create < 1800000){//30 * 60 * 1000
					rm.setMsg("已提交意向，请30分钟后再提交！");
					return this.writeJson(rm);
				}
			}
			
			guestbook.setStoreId(store_id);
			guestbook.setUserId(userId);
			guestbook.setCreateDate(new Date());
			haiGuestbookMapper.insert(guestbook);
			
			rm.setCode(1);
			rm.setMsg("感谢你的申请！");
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
	
}
