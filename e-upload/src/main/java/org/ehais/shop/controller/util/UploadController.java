package org.ehais.shop.controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.controller.CommonController;
import org.ehais.util.IpUtil;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/upload")
public class UploadController extends CommonController {

	
	//视频保存路径配置
	protected String media_path = ResourceUtil.getProValue("media.path");
	//视频是否中转
	protected boolean media_transfer_bool = Boolean.parseBoolean(ResourceUtil.getProValue("media.transfer.bool"));
	//视频中转地址
	protected String media_transfer_posturl = ResourceUtil.getProValue("media.transfer.posturl");
	//视频访问地址
	protected String media_transfer_website = ResourceUtil.getProValue("media.transfer.website");
	//视频上传格式
	protected String media_postfix = ResourceUtil.getProValue("media.postfix");
	//限制当前IP
	protected String media_transfer_ip = ResourceUtil.getProValue("media.transfer.ip");
		
		
		

	
	
	@ResponseBody
	@RequestMapping(value = "/media.upd", method = RequestMethod.POST)
	public String mediaUpload(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {


		String ip = IpUtil.getIpAddr(request);
		System.out.println("当前IP："+ip);
		
		if( StringUtils.isNotBlank(media_transfer_ip) && !ip.equals(media_transfer_ip)){
			return "{'code':0,'msg':'非合法请求101'}";
		}


		try {
			
			return UploadUtils.upload_video(request, response,media_path,media_transfer_bool,media_transfer_posturl,media_postfix,media_transfer_website);

		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	

}
