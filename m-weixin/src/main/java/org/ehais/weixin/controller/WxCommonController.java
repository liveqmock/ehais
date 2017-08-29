package org.ehais.weixin.controller;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.WpPublic;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.service.wx.WeiXinService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class WxCommonController extends CommonController{

	@Autowired
	protected WeiXinService weiXinService;
	
	protected String getOpenId(Integer wxid,String code,String openid) throws Exception{
		String openid_ = null;
		if(code != null && !code.equals("")){
			WpPublic wpPublic = this.getWpPublic(wxid);
			if(wpPublic!=null && wpPublic.getAppid() != null && wpPublic.getSecret() != null){
				OpenidInfo open = WeiXinUtil.getOpenid(code,wpPublic.getAppid(),wpPublic.getSecret());
				if(open != null && open.getOpenid() != null){
					openid_ = open.getOpenid();
				}
			}
			
		}else if(openid != null && !openid.equals("")){
			openid_ = openid;
		}else{
			openid_ = "oT2uMs6nAQ50yZor9BngdLqcyCMI";
		}
		
		return openid_;
	}
	
	protected WpPublic getWpPublic(Integer wxid)throws Exception{
		return weiXinService.getWpPublic(wxid);
	}
}
