package com.ehais.tracking.service.tracking;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Leader;



public interface LeaderService extends CommonService{
	public ReturnObject<Leader> leader_list(HttpServletRequest request) throws Exception;
	public ReturnObject<Leader> leader_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<Leader> leader_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<Leader> leader_insert_submit(HttpServletRequest request,Leader model) throws Exception;
	public ReturnObject<Leader> leader_update(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Leader> leader_update_submit(HttpServletRequest request,Leader model) throws Exception;
	public ReturnObject<Leader> leader_find(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<Leader> leader_delete(HttpServletRequest request,Integer key) throws Exception;
	
	public ReturnObject<Leader> leader_login(HttpServletRequest request,String username,String password) throws Exception;
	
}
