package org.ehais.shop.controller.admin;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiActivity;
import org.ehais.shop.model.HaiActivityExample;
import org.ehais.shop.model.HaiActivityParticipation;
import org.ehais.shop.service.HaiActivityService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.MatrixToImageWriter;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@EPermissionModuleGroup(name="应用模组")

@EPermissionController(name="会议管理",intro="会议管理功能",value="haiActivityController")
@Controller
@RequestMapping("/admin")
public class  HaiActivityAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiActivityAdminController.class);
	protected static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	protected static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	protected static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	protected static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	@Autowired
	private HaiActivityService haiActivityService;
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	protected EStoreService eStoreService;
	
	@EPermissionMethod(name="查询",intro="打开活动会议管理页面",value="haiActivityView",relation="haiActivityListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiActivityView")
	public String haiActivityView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module ) {	
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_list(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getAdminProjectFolder(request)+"/activity/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回活动会议管理数据",value="haiActivityListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiActivityListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "catId", required = false) Integer catId,
			@RequestParam(value = "activityName", required = false) String activityName,
			@RequestParam(value = "module", required = true) String module) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_list_json(request,module, condition,catId,activityName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增活动会议管理",value="haiActivityAddDetail",relation="haiActivityAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityAddDetail",method=RequestMethod.GET)
	public String haiActivityAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_insert(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getAdminProjectFolder(request)+"/activity/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交活动会议管理",value="haiActivityAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActivityAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("activity") HaiActivity activity,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiActivity> rm = haiActivityService.activity_insert_submit(request,module, activity);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑活动会议管理",value="haiActivityEditDetail",relation="haiActivityEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityEditDetail",method=RequestMethod.GET)
	public String haiActivityEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<HaiActivity> rm = haiActivityService.activity_update(request,module,activityId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getAdminProjectFolder(request)+"/activity/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交活动会议管理",value="haiActivityEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActivityEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("activity") HaiActivity activity,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiActivityService.activity_update_submit(request,module,activity));
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除活动会议管理",value="haiActivityDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivityDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			return this.writeJson(haiActivityService.activity_delete(request,module, activityId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
	


	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(name="查询分类",intro="打开活动会议管理页面",value="haiActicityCatView",relation="haiActicityCatListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiActicityCatView")
	public String haiActicityCatView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module ) {	
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_list(request,module);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/activity/articlecat_view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回活动会议管理数据",value="haiActicityCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiActicityCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "catName", required = false) String catName,
			@RequestParam(value = "module", required = true) String module) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_list_json(request,module, condition,catName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增活动会议管理",value="haiActicityCatAddDetail",relation="haiActicityCatAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatAddDetail",method=RequestMethod.GET)
	public String haiActicityCatAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_insert(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getAdminProjectFolder(request)+"/activity/articlecat_detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交活动会议管理",value="haiActicityCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActicityCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_insert_submit(request,module, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑活动会议管理",value="haiActicityCatEditDetail",relation="haiActicityCatEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatEditDetail",method=RequestMethod.GET)
	public String haiActicityCatEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticleCat> rm = haiActivityService.articlecat_update(request,module,catId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
			return "/"+this.getAdminProjectFolder(request)+"/activity/articlecat_detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交活动会议管理",value="haiActicityCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiActicityCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result,
			@RequestParam(value = "module", required = true) String module
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiActivityService.articlecat_update_submit(request,module,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除活动会议管理",value="haiActicityCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActicityCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActicityCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			return this.writeJson(haiActivityService.articlecat_delete(request,module, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="预览二维码",intro="活动会议二维码",value="haiActivityQRcode",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityQRcode",method=RequestMethod.GET)
	public String haiActivityQRcode(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		modelMap.addAttribute("module", module);
		modelMap.addAttribute("activityId", activityId);
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(wp == null){
//				return "redirect:wpPublicDetail";
				return this.ReturnJump(modelMap, 1, "请先设置微信公众号信息", "wpPublicDetail");
			}
//			ReturnObject<HaiActivity> rm = haiActivityService.activity_info(request,module,activityId);
//			modelMap.addAttribute("rm", rm);
			String apply_link = request.getScheme() +"://"+request.getServerName()+"/haiActivityApply!"+SignUtil.setAid(store_id, 0, 0L, 0L, activityId, wp.getToken());
			String sign_link = request.getScheme() +"://"+request.getServerName()+"/haiActivitySign!"+SignUtil.setAid(store_id, 0, 0L, 0L, activityId, wp.getToken());
			
			modelMap.addAttribute("apply_link", apply_link);
			modelMap.addAttribute("sign_link", sign_link);
			
			return "/"+this.getAdminProjectFolder(request)+"/activity/qrcode";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@EPermissionMethod(name="下载二维码",intro="活动会议二维码",value="haiActivityQRcode",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivityQRcodeDownLoad!{qrtype}",method=RequestMethod.GET)
	public void haiActivityQRcodeDownLoad(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "qrtype") String qrtype,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		if(StringUtils.isBlank(qrtype) || (!qrtype.equals("Apply") && !qrtype.equals("Sign")))return ;
		try{
			
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(wp == null){
				return ;
			}
			ReturnObject<HaiActivity> rm = haiActivityService.activity_info(request,module,activityId);
			if(rm.getCode() != 1)return ;
			if(rm.getModel() == null)return ;
			String link = request.getScheme() +"://"+request.getServerName()+"/haiActivity"+qrtype+"!"+SignUtil.setAid(store_id, 0, 0L, 0L, activityId, wp.getToken());
			
			///////////////////////////////////////////
			///////////////////////////////////////////
			///////////////////////////////////////////
			
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			@SuppressWarnings("rawtypes")
	        Map hints = new HashMap();
	        
	        //设置UTF-8， 防止中文乱码
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	        //设置二维码四周白色区域的大小
	        hints.put(EncodeHintType.MARGIN,1);
	        //设置二维码的容错性
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
	        
	        //width:图片完整的宽;height:图片完整的高
	        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
	        int width = 400;
	        int height = 500;
	        
	        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
	        BitMatrix bitMatrix = multiFormatWriter.encode(link, BarcodeFormat.QR_CODE, width, height, hints);
	        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//	        
	        Graphics2D g = image.createGraphics();
	        String pressText = rm.getModel().getActivityName();
	        int fontSize = 25; //字体大小
	        int fontStyle = 1; //字体风格
	        
	        int startX = (width-(fontSize*pressText.length()))/2;
	        //y开始的位置：图片高度-（图片高度-图片宽度）/2
	        int startY = height-(height-width)/2 + 5; 
	        g.setColor(Color.BLACK);
	        g.setFont(new Font(null, fontStyle, fontSize));
	        g.drawString(pressText, startX, startY);
	        
	        
	        fontSize = 25; //字体大小
	        g.setFont(new Font(null, fontStyle, fontSize));
	        pressText = qrtype.equals("Apply")?"报名二维码":"签到二维码";
	        startX = (width-(fontSize*pressText.length()))/2;
	        g.drawString(pressText, startX, 20);
	        
	        
	        g.dispose();
	        String qrname = rm.getModel().getActivityName() + pressText;
	        response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(qrname.getBytes("utf-8") , "ISO8859-1")+".jpg");  
            
	         
	        
	        OutputStream stream = response.getOutputStream();
	        ImageIO.write(image, "jpg", stream);
	        
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
		}
	}
	
	
	
	@EPermissionMethod(name="统计",intro="签到统计",value="haiActivitySignStatistics",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiActivitySignStatistics",method=RequestMethod.GET)
	public String haiActivitySignStatistics(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module
			) {
		modelMap.addAttribute("module", module);
		modelMap.addAttribute("activityId", activityId);
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(wp == null){
//				return "redirect:wpPublicDetail";
				return this.ReturnJump(modelMap, 1, "请先设置微信公众号信息", "wpPublicDetail");
			}
			
			ReturnObject<HaiActivity> rm = haiActivityService.activity_info(request,module,activityId);
			modelMap.addAttribute("rm", rm);
			
			return "/"+this.getAdminProjectFolder(request)+"/activity/sign_statistics";
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回活动会议管理数据",value="haiActivityListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiActivitySignStatisticsJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiActivitySignStatisticsJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "activityId", required = true) Integer activityId,
			@RequestParam(value = "module", required = true) String module) {
		try{
			ReturnObject<HaiActivityParticipation> rm = haiActivityService.activity_sign_list_json(request,module, condition,activityId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
			return this.errorJSON(e);
		}
	}
	
}


