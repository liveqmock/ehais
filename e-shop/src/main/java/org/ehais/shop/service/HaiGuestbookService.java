package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiGuestbook;
import org.ehais.shop.model.HaiGuestbookWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiGuestbookService extends CommonService{
	public ReturnObject<HaiGuestbook> guestbook_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGuestbook> guestbook_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_insert_submit(HttpServletRequest request,HaiGuestbookWithBLOBs model) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_update(HttpServletRequest request,Integer guestbookId) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_update_submit(HttpServletRequest request,HaiGuestbookWithBLOBs model) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_info(HttpServletRequest request,Integer guestbookId) throws Exception;
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_find(HttpServletRequest request,Integer guestbookId) throws Exception;
	public ReturnObject<HaiGuestbook> guestbook_delete(HttpServletRequest request,Integer guestbookId) throws Exception;
	


	

}

