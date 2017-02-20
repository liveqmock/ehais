package org.ehais.controller.epublic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.util.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/upload")
public class EUploadController extends CommonController {


	@ResponseBody
	@RequestMapping(value = "/image.upd", method = RequestMethod.POST)
	public String saveDriver(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	// @RequestParam(value = "image", required = false) MultipartFile filedata
	) {

		String path = request.getRealPath("/eUploads/images");

		try {
			
			return UploadUtils.upload_image(request, response,path);

		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/file.upd", method = RequestMethod.POST)
	public String saveFile(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {

		String path = request.getRealPath("/eUploads/file");

		try {
			
			return UploadUtils.upload_file(request, response);

		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	

	
	
}
