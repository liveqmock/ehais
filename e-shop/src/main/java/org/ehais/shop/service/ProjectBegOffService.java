package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.project.HaiBegOff;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface ProjectBegOffService extends CommonService{
	public ReturnObject<HaiBegOff> begoff_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBegOff> begoff_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String begOffName) throws Exception;
	public ReturnObject<HaiBegOff> begoff_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBegOff> begoff_insert_submit(HttpServletRequest request,HaiBegOff model) throws Exception;
	public ReturnObject<HaiBegOff> begoff_update(HttpServletRequest request,Integer begOffId) throws Exception;
	public ReturnObject<HaiBegOff> begoff_update_submit(HttpServletRequest request,HaiBegOff model) throws Exception;
	public ReturnObject<HaiBegOff> begoff_info(HttpServletRequest request,Integer begOffId) throws Exception;
	public ReturnObject<HaiBegOff> begoff_find(HttpServletRequest request,Integer begOffId) throws Exception;
	public ReturnObject<HaiBegOff> begoff_delete(HttpServletRequest request,Integer begOffId) throws Exception;
	


	

}

