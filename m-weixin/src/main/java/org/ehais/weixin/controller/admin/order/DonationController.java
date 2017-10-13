package org.ehais.weixin.controller.admin.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.ehais.weixin.model.HaiDonation;
import org.ehais.weixin.service.order.DonationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  DonationController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(DonationController.class);

	@Autowired
	private DonationService donationService;
	
	
	@RequestMapping("/donation_list")
	public String donation_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			modelMap.addAttribute("wxid", store_id);
			modelMap.addAttribute("action", "donation_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return "/admin/donation/list";
	}
	
	@ResponseBody
	@RequestMapping("/donation_list_json")
	public String donation_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiDonation> rm = donationService.donation_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return null;
	}
	
	

	@RequestMapping("/donation_insert")
	public String donation_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{ 
			ReturnObject<HaiDonation> rm = donationService.donation_insert(request);
			rm.setAction("donation_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return "/admin/donation/info";
	}
	
	private void create_json(HttpServletRequest request){
		try {
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			String fileContent = this.writeJson(donationService.donation_list_json(request, store_id, 0, 100));
			FSO fso = new FSO();
			String path = request.getRealPath("")+"/donation.json";
			if(!fso.TextFileExists(path))fso.CreateTextFile(path);
			fso.WriteTextFileZh(path, fileContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/donation_insert_submit",method=RequestMethod.POST)
	public String donation_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiDonation donation
			) {
		try{
			ReturnObject<HaiDonation> rm = donationService.donation_insert_submit(request,donation);
			this.create_json(request);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "donation_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return "redirect:donation_insert";
	}
	
	@RequestMapping("/donation_update")
	public String donation_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "donationId", required = true) Integer donationId
			) {
		try{
			ReturnObject<HaiDonation> rm = donationService.donation_update(request, donationId);
			rm.setAction("donation_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return "/admin/donation/info";
	}
	
	@RequestMapping(value="/donation_update_submit",method=RequestMethod.POST)
	public String donation_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiDonation donation
			) {
		try{
			ReturnObject<HaiDonation> rm = donationService.donation_update_submit(request,donation);
			this.create_json(request);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "donation_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return "/admin/donation/info";
	}
	
	
	@RequestMapping("/donation_delete")
	public String donation_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "donationId", required = true) Integer donationId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiDonation> rm = donationService.donation_delete(request, donationId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "donation_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("donation", e);
		}
		return null;
	}
	
	/**
	

	@ResponseBody
	@RequestMapping("/controller{store_id}")
	public String controller(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@RequestParam(value = "code", required = true) String code){
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	22///////////////////////////////////////////////////	
	@ResponseBody
	@RequestMapping("/_list")
	public String _list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(service._list(request,store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	**/
	
}


