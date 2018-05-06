package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiVote;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiVoteService extends CommonService{
	public ReturnObject<HaiVote> vote_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiVote> vote_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String vodeName) throws Exception;
	public ReturnObject<HaiVote> vote_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiVote> vote_insert_submit(HttpServletRequest request,HaiVote model) throws Exception;
	public ReturnObject<HaiVote> vote_update(HttpServletRequest request,Integer vodeId) throws Exception;
	public ReturnObject<HaiVote> vote_update_submit(HttpServletRequest request,HaiVote model) throws Exception;
	public ReturnObject<HaiVote> vote_info(HttpServletRequest request,Integer vodeId) throws Exception;
	public ReturnObject<HaiVote> vote_find(HttpServletRequest request,Integer vodeId) throws Exception;
	public ReturnObject<HaiVote> vote_delete(HttpServletRequest request,Integer vodeId) throws Exception;
	


	

}

