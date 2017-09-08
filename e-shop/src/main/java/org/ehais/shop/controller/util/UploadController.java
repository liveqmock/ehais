package org.ehais.shop.controller.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.util.ResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/upload")
public class UploadController extends CommonController{

	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
//	@ResponseBody
//	@RequestMapping(value = "/image.upd", method = RequestMethod.POST)
//	public String image_upload(ModelMap modelMap, HttpServletRequest request,
//			HttpServletResponse response) {
//		String path = request.getRealPath("/eUploads/images");
//
//		try {
//			
//			return UploadUtils.upload_image(request, response,path);
//
//		} catch (Exception e) {
//			log.error("上传文件失败.", e);
//
//		}
//		return null;
//	}
	
	@ResponseBody
	@RequestMapping(value = "/qiniu_upload", method = RequestMethod.POST)
	public JSONObject qiniu_upload(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response){
		
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(accessKey, secretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
//		System.out.println(upToken);
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String key = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			// 先判断request中是否包涵multipart类型的数据，
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile filedata = multiRequest.getFile((String) iter.next());
					if (filedata != null && !filedata.isEmpty()) {
						byte[] uploadBytes = filedata.getBytes();
						ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
						Response qiniuResponse = uploadManager.put(byteInputStream,key,upToken,null, null);
//						System.out.println(qiniuResponse.bodyString());
						//解析上传成功的结果
				        DefaultPutRet putRet = new Gson().fromJson(qiniuResponse.bodyString(), DefaultPutRet.class);
				        System.out.println(putRet.key);
				        System.out.println(putRet.hash);
				        
				        JSONObject json = JSONObject.fromObject(qiniuResponse.bodyString());
				        
				        
				        // 获取图片的文件名
						String fileName = filedata.getOriginalFilename();
						// 获取图片的扩展名
						String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
				        map.put("state", "SUCCESS");
				        map.put("title", putRet.hash);
				        map.put("original", fileName);
				        map.put("type", "."+extensionName);
				        map.put("url", domain+putRet.key);
				        map.put("size", json.getString("fsize"));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return JSONObject.fromObject(map);
	}
	
	
}
