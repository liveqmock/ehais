package org.ehais.shop.controller.admin;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.controller.wine.ResourceUtil;
import org.ehais.shop.service.IXiangmengService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;



@EPermissionController(intro="象盟管理功能",value="iXiangmengController")
@Controller
@RequestMapping("/")
public class  IXiangmengAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(IXiangmengAdminController.class);
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String wxdev_token = ResourceUtil.getProValue("wxdev_token");
	public static String website = ResourceUtil.getProValue("website");
	
	@Autowired
	private IXiangmengService iXiangmengService;
	
	private Integer store_id = 3;
	private Integer inviteCode = 2688981;
	
	@EPermissionMethod(intro="打开象盟管理页面",value="iXiangmengView",type=PermissionProtocol.URL)
	@RequestMapping("/ixiangmeng")
	public String iXiangmengView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_list(request);
			modelMap.addAttribute("rm", rm);
			return "/admin/xiangmeng/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回象盟管理数据",value="iXiangmengListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/iXiangmengListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String iXiangmengListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_list_json(request, condition,keySubId,title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增象盟管理",value="iXiangmengAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/iXiangmengAddDetail",method=RequestMethod.GET)
	public String iXiangmengAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/admin/xiangmeng/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交象盟管理",value="iXiangmengAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/iXiangmengAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String iXiangmengAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("xiangmeng") EHaiArticle xiangmeng,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_insert_submit(request, xiangmeng);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑象盟管理",value="iXiangmengEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/iXiangmengEditDetail",method=RequestMethod.GET)
	public String iXiangmengEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_update(request,articleId);
			modelMap.addAttribute("rm", rm);
			return "/admin/xiangmeng/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交象盟管理",value="iXiangmengEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/iXiangmengEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String iXiangmengEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("xiangmeng") EHaiArticle xiangmeng,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(iXiangmengService.xiangmeng_update_submit(request,xiangmeng));
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除象盟管理",value="iXiangmengDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/iXiangmengDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String iXiangmengDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			return this.writeJson(iXiangmengService.xiangmeng_delete(request, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJSON(e);
		}
	}
	
	
	@EPermissionMethod(name="编辑",intro="编辑象盟管理",value="iXiangmengEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/wxIXiangmeng",method=RequestMethod.GET)
	public String iXiangmeng(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_update(request,articleId);
			modelMap.addAttribute("rm", rm);
			
			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, weixin_appid, weixin_appsecret, null);
			signature.setTitle(rm.getModel().getTitle());
//			signature.setLink(rm.getModel().getLink());
			signature.setLink(request.getScheme()+"://"+ request.getServerName()+"/wxixm"+rm.getModel().getArticleId());
			signature.setDesc(rm.getModel().getDescription());
			signature.setImgUrl(rm.getModel().getArticleThumb());
			List<String> jsApiList = new ArrayList<String>();
			jsApiList.add("onMenuShareTimeline");
			jsApiList.add("onMenuShareAppMessage");
			jsApiList.add("onMenuShareQQ");
			jsApiList.add("onMenuShareWeibo");
			jsApiList.add("onMenuShareQZone");
			signature.setJsApiList(jsApiList);
			modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
			
			
			return "/admin/xiangmeng/wx";
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/wxixm{articleId}")
	public String wxixm(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "articleId", required = true) Integer articleId
			) {
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
			ReturnObject<EHaiArticle> rm = iXiangmengService.xiangmeng_update(request,articleId);
			
			return "redirect:"+rm.getModel().getLink();
		}catch(Exception e){
			e.printStackTrace();
			log.error("xiangmeng", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	
}


