package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiExtendValue;

public interface ExtendsFieldsService extends CommonService{

	public ReturnObject<Object> ExtendsFieldsInfo(HttpServletRequest request,String code,String table,Integer tableId) throws Exception;
	
	public ReturnObject<HaiExtendValue> ExtendsFieldsSave(HttpServletRequest request,String code,String table,Integer tableId) throws Exception;
	
}
