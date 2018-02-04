package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiCouponsService extends CommonService{
	public ReturnObject<HaiCoupons> coupons_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCoupons> coupons_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String couponsName) throws Exception;
	public ReturnObject<HaiCoupons> coupons_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCoupons> coupons_insert_submit(HttpServletRequest request,HaiCoupons model) throws Exception;
	public ReturnObject<HaiCoupons> coupons_update(HttpServletRequest request,Integer couponsId) throws Exception;
	public ReturnObject<HaiCoupons> coupons_update_submit(HttpServletRequest request,HaiCoupons model) throws Exception;
	public ReturnObject<HaiCoupons> coupons_info(HttpServletRequest request,Integer couponsId) throws Exception;
	public ReturnObject<HaiCoupons> coupons_find(HttpServletRequest request,Integer couponsId) throws Exception;
	public ReturnObject<HaiCoupons> coupons_delete(HttpServletRequest request,Integer couponsId) throws Exception;
	


	/**
	 * 获取代理合伙的对应下属非餐饮商家的所有优惠券
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<HaiCoupons> partner_coupons_list(HttpServletRequest request,Integer partnerId)throws Exception;
	
	

}

