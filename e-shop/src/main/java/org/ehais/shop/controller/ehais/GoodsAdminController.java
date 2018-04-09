package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsDistribution;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.service.CategoryService;
import org.ehais.shop.service.GoodsGalleryService;
import org.ehais.shop.service.GoodsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="商品信息功能",value="ehaisGoodsController")
@Controller
@RequestMapping("/ehais")
public class  GoodsAdminController extends EhaisCommonController {

	private static Logger log = LoggerFactory.getLogger(GoodsAdminController.class);

	@Autowired
	private GoodsService ehaisGoodsService;
	@Autowired
	private CategoryService ehaisCategoryService;
	@Autowired
	private GoodsGalleryService goodsGalleryService; 
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
	@EPermissionMethod(intro="打开商品信息页面",value="ehaisGoodsView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/ehaisGoodsView")
	public String ehaisGoodsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGoods> rm = ehaisGoodsService.goods_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/goods/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回商品信息数据",value="ehaisGoodsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisGoodsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "goods_name", required = false) String goods_name) {
		try{
			ReturnObject<HaiGoods> rm = ehaisGoodsService.goods_list_json(request, condition ,cat_id , goods_name,true);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增商品信息",value="ehaisGoodsAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisGoodsAddDetail",method=RequestMethod.GET)
	public String ehaisGoodsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = ehaisGoodsService.goods_insert(request);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			
			return "/"+this.getStoreTheme(request)+"/goods/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商品信息",value="ehaisGoodsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisGoodsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("goodsDistribution") HaiGoodsDistribution goodsDistribution,
			@Validated({EInsertValidator.class,EUniqueValidator.class}) @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result,
			@RequestParam(value = "imgOriginal", required = false) String[] imgOriginal
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			
			ReturnObject<HaiGoodsWithBLOBs> rm = ehaisGoodsService.ehais_goods_insert_submit(request, goods,imgOriginal,goodsDistribution);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑商品信息",value="ehaisGoodsEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisGoodsEditDetail",method=RequestMethod.GET)
	public String ehaisGoodsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = ehaisGoodsService.goods_update(request,goodsId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			
			return "/"+this.getStoreTheme(request)+"/goods/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商品信息",value="ehaisGoodsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisGoodsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("goodsDistribution") HaiGoodsDistribution goodsDistribution,
			@Validated({EUpdateValidator.class}) @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result,
			@RequestParam(value = "imgOriginal", required = false) String[] imgOriginal
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisGoodsService.ehais_goods_update_submit(request,goods,imgOriginal,goodsDistribution));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商品信息",value="ehaisGoodsDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisGoodsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisGoodsService.goods_delete(request, goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	

	@ResponseBody
	@EPermissionMethod(intro="返回商品信息数据",value="ehaisCategoryListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisCategoryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisCategoryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<HaiCategory> rm = ehaisCategoryService.category_list_json(request, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商品信息",value="ehaisCategoryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisCategoryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisCategoryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiCategoryWithBLOBs> rm = ehaisCategoryService.category_insert_submit(request, category);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑FunctionName",value="ehaisCategoryEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisCategoryEditDetail",method=RequestMethod.POST)
	public String ehaisCategoryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = ehaisCategoryService.category_update(request, catId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("tempSublate", e);
			return this.errorJSON(e);
		}
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商品信息",value="ehaisCategoryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisCategoryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisCategoryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisCategoryService.category_update_submit(request,category));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	

	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商品信息",value="ehaisCategoryDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisCategoryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisCategoryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisCategoryService.category_delete(request, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@RequestMapping(value="/manage/goods_qrcode",method=RequestMethod.POST)
	public void manage_article_qrcode(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "download", required = false) Integer download
			
			) {	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			
			this.goods_qrcode(
					request,
					response,
					haiGoodsMapper,
					store_id,
					Integer.valueOf(0),
					Long.valueOf(0l),
					Long.valueOf(0l),
					goodsId,
					download					
					);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
}


