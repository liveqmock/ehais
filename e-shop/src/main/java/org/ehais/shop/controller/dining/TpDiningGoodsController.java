package org.ehais.shop.controller.dining;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.tp.TpDining;
import org.ehais.shop.model.tp.TpDiningCategory;
import org.ehais.shop.model.tp.TpDiningWithBLOBs;
import org.ehais.shop.service.TpDiningService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
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


@EPermissionController(intro="Dining功能",value="tpDiningController")
@Controller
@RequestMapping("/dining")
public class  TpDiningGoodsController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(TpDiningGoodsController.class);

	@Autowired
	private TpDiningService tpDiningService;
	
	
	@EPermissionMethod(intro="打开Dining页面",value="tpDiningView",type=PermissionProtocol.URL)
	@RequestMapping("/tpDiningView")
	public String tpDiningView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<TpDining> rm = tpDiningService.dining_list(request);
			modelMap.addAttribute("rm", rm);
			return "/dining/goods/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回Dining数据",value="tpDiningListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/tpDiningListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "keyword", required = false) String keyword) {
		try{
			ReturnObject<TpDining> rm = tpDiningService.dining_list_json(request, condition,cat_id,keyword);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回Dining数据",value="tpDiningListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/tpDiningCategoryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningCategoryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			ReturnObject<TpDiningCategory> rm = tpDiningService.dining_category_list_json(request);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增Dining",value="tpDiningAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningAddDetail",method=RequestMethod.GET)
	public String tpDiningAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<TpDiningWithBLOBs> rm = tpDiningService.dining_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/dining/goods/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交Dining",value="tpDiningAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("dining") TpDiningWithBLOBs dining,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<TpDiningWithBLOBs> rm = tpDiningService.dining_insert_submit(request, dining);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑Dining",value="tpDiningEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningEditDetail",method=RequestMethod.GET)
	public String tpDiningEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<TpDiningWithBLOBs> rm = tpDiningService.dining_update(request,goodsId);
			if(rm.getCode()!=1)return this.wrongJump(modelMap, rm.getMsg());
			modelMap.addAttribute("rm", rm);
			return "/dining/goods/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交Dining",value="tpDiningEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Integer goodsId,
			@Valid @ModelAttribute("dining") TpDiningWithBLOBs dining,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(tpDiningService.dining_update_submit(request,dining));
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除Dining",value="tpDiningDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(tpDiningService.dining_delete(request, goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


