package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiCategory;
import org.ehais.weixin.model.HaiCategoryWithBLOBs;




public interface HaiCategoryService extends CommonService{
	public ReturnObject<HaiCategory> haicategory_list(HttpServletRequest request,String cat_code,Integer store_id) throws Exception;
	public ReturnObject<HaiCategory> haicategory_list_json(HttpServletRequest request,String cat_code,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_insert(HttpServletRequest request,String cat_code,Integer store_id) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_insert_submit(HttpServletRequest request,String cat_code,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_update(HttpServletRequest request,String cat_code,Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_update_submit(HttpServletRequest request,String cat_code,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_find(HttpServletRequest request,String cat_code,Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiCategory> haicategory_delete(HttpServletRequest request,String cat_code,Integer store_id,Integer key) throws Exception;
	
	

}

