package org.ehais.weixin.controller.action;

import org.ehais.epublic.controller.admin.EAdPositionController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class  AdPositionController extends EAdPositionController {

//	private static Logger log = LoggerFactory.getLogger(AdPositionController.class);
//
//	@Autowired
//	private AdPositionService adpositionService;
//	
//	
//	@RequestMapping("/adposition_list")
//	public String adposition_list(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response ) {	
//		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
//		try{
//			modelMap.addAttribute("wxid", user_id);
//			modelMap.addAttribute("action", "adposition_list_json");
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return "/action/adposition/list";
//	}
//	
//	@ResponseBody
//	@RequestMapping("/adposition_list_json")
//	public String adposition_list_json(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "page", required = true) Integer page,
//			@RequestParam(value = "len", required = true) Integer len) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_list_json(request, page, len);
//			return this.writeJson(rm);
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return null;
//	}
//	
//	
//
//	@RequestMapping("/adposition_insert")
//	public String adposition_insert(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response
//			) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_insert(request);
//			rm.setAction("adposition_insert_submit");
//			modelMap.addAttribute("rm", rm);
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return "/action/adposition/info";
//	}
//	
//	@RequestMapping(value="/adposition_insert_submit",method=RequestMethod.POST)
//	public String adposition_insert_submit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute HaiAdPosition adposition
//			) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_insert_submit(request,adposition);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "adposition_insert");
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return "redirect:adposition_insert";
//	}
//	
//	@RequestMapping("/adposition_update")
//	public String adposition_update(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "positionId", required = true) Integer positionId
//			) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_update(request, positionId);
//			rm.setAction("adposition_update_submit");
//			modelMap.addAttribute("rm", rm);
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return "/action/adposition/info";
//	}
//	
//	@RequestMapping(value="/adposition_update_submit",method=RequestMethod.POST)
//	public String adposition_update_submit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute HaiAdPosition adposition
//			) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_update_submit(request,adposition);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "adposition_list");
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return "/action/adposition/info";
//	}
//	
//	
//	@RequestMapping("/adposition_delete")
//	public String adposition_delete(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "positionId", required = false) Integer positionId,
//			@RequestParam(value = "code", required = false) String code
//			) {
//		try{
//			ReturnObject<HaiAdPosition> rm = adpositionService.adposition_delete(request, positionId);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "adposition_list");
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("adposition", e);
//		}
//		return null;
//	}
//	
	
}


