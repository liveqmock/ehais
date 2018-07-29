package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.tools.EConditionObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="图片管理管理",value="haiGoodsGalleryController")
@Controller
@RequestMapping("/admin")
public class  HaiGoodsGalleryAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiGoodsGalleryAdminController.class);

	@Autowired
	private HaiGoodsGalleryService haiGoodsGalleryService;
	
	
	@EPermissionMethod(name="查询",intro="打开图片管理页面",value="haiGoodsGalleryView",relation="haiGoodsGalleryListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiGoodsGalleryView")
	public String haiGoodsGalleryView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goodsgallery/view";
			//return this.view(request, "/goodsgallery/view");
			return this.view(request, "/goodsgallery/goodsgalleryView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回图片管理数据",value="haiGoodsGalleryListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiGoodsGalleryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsGalleryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "tableName", required = false) String tableName) {
		try{
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_list_json(request, condition,keySubId,tableName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增图片管理",value="haiGoodsGalleryAddDetail",relation="haiGoodsGalleryAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiGoodsGalleryAddDetail",method=RequestMethod.GET)
	public String haiGoodsGalleryAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goodsgallery/detail";
			return this.view(request, "/goodsgallery/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交图片管理",value="haiGoodsGalleryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsGalleryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsGalleryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("goodsgallery") HaiGoodsGallery goodsgallery,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_insert_submit(request, goodsgallery);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑图片管理",value="haiGoodsGalleryEditDetail",relation="haiGoodsGalleryEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiGoodsGalleryEditDetail",method=RequestMethod.POST)
	public String haiGoodsGalleryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "imgId", required = true) Integer imgId
			) {
		try{
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_update(request,imgId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑图片管理",value="haiGoodsGalleryEditDetail",relation="haiGoodsGalleryEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiGoodsGalleryEditDetail",method=RequestMethod.GET)
	public String haiGoodsGalleryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "imgId", required = true) Integer imgId
			) {
		try{
			ReturnObject<HaiGoodsGallery> rm = haiGoodsGalleryService.goodsgallery_update(request,imgId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goodsgallery/detail";
			return this.view(request, "/goodsgallery/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交图片管理",value="haiGoodsGalleryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsGalleryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsGalleryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("goodsgallery") HaiGoodsGallery goodsgallery,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiGoodsGalleryService.goodsgallery_update_submit(request,goodsgallery));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除图片管理",value="haiGoodsGalleryDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiGoodsGalleryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsGalleryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "imgId", required = true) Integer imgId
			) {
		try{
			return this.writeJson(haiGoodsGalleryService.goodsgallery_delete(request, imgId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsgallery", e);
			return this.errorJSON(e);
		}
	}

	


}


