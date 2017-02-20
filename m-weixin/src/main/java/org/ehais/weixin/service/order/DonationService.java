package org.ehais.weixin.service.order;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiDonation;

public interface DonationService extends CommonService{
	public ReturnObject<HaiDonation> donation_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiDonation> donation_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiDonation> donation_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiDonation> donation_insert_submit(HttpServletRequest request,HaiDonation model) throws Exception;
	public ReturnObject<HaiDonation> donation_update(HttpServletRequest request,Integer donationId) throws Exception;
	public ReturnObject<HaiDonation> donation_update_submit(HttpServletRequest request,HaiDonation model) throws Exception;
	public ReturnObject<HaiDonation> donation_find(HttpServletRequest request,Integer donationId) throws Exception;
	public ReturnObject<HaiDonation> donation_detail(HttpServletRequest request,Integer store_id, Integer donationId) throws Exception;
	public ReturnObject<HaiDonation> donation_delete(HttpServletRequest request,Integer donationId) throws Exception;
	
	

}

