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

@EPermissionController(name="总表管理管理",value="haiOrderFinanceController")
@Controller
@RequestMapping("/admin")
public class  HaiOrderFinanceAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiOrderFinanceAdminController.class);

	@Autowired
	private HaiOrderFinanceService haiOrderFinanceService;
	
	
	@EPermissionMethod(name="查询",intro="打开总表管理页面",value="haiOrderFinanceView",relation="haiOrderFinanceListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiOrderFinanceView")
	public String haiOrderFinanceView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderfinance/view";
			//return this.view(request, "/orderfinance/view");
			return this.view(request, "/orderfinance/orderfinanceView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回总表管理数据",value="haiOrderFinanceListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiOrderFinanceListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderFinanceListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "financeName", required = false) String financeName) {
		try{
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_list_json(request, condition,keySubId,financeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增总表管理",value="haiOrderFinanceAddDetail",relation="haiOrderFinanceAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiOrderFinanceAddDetail",method=RequestMethod.GET)
	public String haiOrderFinanceAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderfinance/detail";
			return this.view(request, "/orderfinance/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交总表管理",value="haiOrderFinanceAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderFinanceAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderFinanceAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("orderfinance") HaiOrderFinance orderfinance,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_insert_submit(request, orderfinance);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑总表管理",value="haiOrderFinanceEditDetail",relation="haiOrderFinanceEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiOrderFinanceEditDetail",method=RequestMethod.POST)
	public String haiOrderFinanceEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "financeId", required = true) Integer financeId
			) {
		try{
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_update(request,financeId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑总表管理",value="haiOrderFinanceEditDetail",relation="haiOrderFinanceEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiOrderFinanceEditDetail",method=RequestMethod.GET)
	public String haiOrderFinanceEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "financeId", required = true) Integer financeId
			) {
		try{
			ReturnObject<HaiOrderFinance> rm = haiOrderFinanceService.orderfinance_update(request,financeId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderfinance/detail";
			return this.view(request, "/orderfinance/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交总表管理",value="haiOrderFinanceEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderFinanceEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderFinanceEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("orderfinance") HaiOrderFinance orderfinance,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiOrderFinanceService.orderfinance_update_submit(request,orderfinance));
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除总表管理",value="haiOrderFinanceDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiOrderFinanceDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderFinanceDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "financeId", required = true) Integer financeId
			) {
		try{
			return this.writeJson(haiOrderFinanceService.orderfinance_delete(request, financeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderfinance", e);
			return this.errorJSON(e);
		}
	}

	


}


