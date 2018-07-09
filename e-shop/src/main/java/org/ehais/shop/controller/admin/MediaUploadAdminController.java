package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class MediaUploadAdminController extends CommonController {
	
	//视频保存路径配置
	protected String video_path = ResourceUtil.getProValue("video.folder");
	//视频是否中转
	protected boolean video_transfer_bool = Boolean.parseBoolean(ResourceUtil.getProValue("video.transfer.bool"));
	//视频中转地址
	protected String video_transfer_posturl = ResourceUtil.getProValue("video.transfer.posturl");
	//视频访问地址
	protected String video_transfer_website = ResourceUtil.getProValue("video.transfer.website");
	//视频上传格式
	protected String video_postfix = ResourceUtil.getProValue("video.postfix");
	//视频截图尺寸
	protected String video_pic_size = ResourceUtil.getProValue("video.pic.size");
	//ffmpeg的路径
	protected String video_ffmpeg_path = ResourceUtil.getProValue("video.ffmpeg.path");
	//ftp的地址
	protected String video_ftp_path = ResourceUtil.getProValue("video.ftp.folder");
	public final static String MEDIA = "media";//视频
	
	@ResponseBody
	@RequestMapping(value = "/video.mg", method = RequestMethod.POST)
	public String videoUpload(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {


		try {
			
			return UploadUtils.upload_video(request, response,video_path,video_transfer_bool,video_transfer_posturl,video_postfix,video_transfer_website);

		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	

}
