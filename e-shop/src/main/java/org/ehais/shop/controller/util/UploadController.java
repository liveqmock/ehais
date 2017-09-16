package org.ehais.shop.controller.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.util.ResourceUtil;
import org.ehais.weixin.WXConstants;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class UploadController extends CommonController {

	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	@Autowired
	protected EWPPublicService eWPPublicService;

	@ResponseBody
	@RequestMapping(value = "/qiniu_upload", method = RequestMethod.POST)
	public JSONObject qiniu_upload(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(accessKey, secretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		// System.out.println(upToken);
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String key = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 先判断request中是否包涵multipart类型的数据，
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile filedata = multiRequest.getFile((String) iter.next());
					if (filedata != null && !filedata.isEmpty()) {
						byte[] uploadBytes = filedata.getBytes();
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
						Response qiniuResponse = uploadManager.put(byteInputStream, key, upToken, null, null);
						// System.out.println(qiniuResponse.bodyString());
						// 解析上传成功的结果
						DefaultPutRet putRet = new Gson().fromJson(qiniuResponse.bodyString(), DefaultPutRet.class);
						// System.out.println(putRet.key);
						// System.out.println(putRet.hash);

						JSONObject json = JSONObject.fromObject(qiniuResponse.bodyString());

						// 获取图片的文件名
						String fileName = filedata.getOriginalFilename();
						// 获取图片的扩展名
						String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
						map.put("state", "SUCCESS");
						map.put("title", putRet.hash);
						map.put("original", fileName);
						map.put("type", "." + extensionName);
						map.put("url", domain + putRet.key);
						map.put("size", json.getString("fsize"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JSONObject.fromObject(map);
	}

	@ResponseBody
	@RequestMapping(value = "/download_to_qiniu", method = RequestMethod.POST)
	public JSONObject download_to_qiniu(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pic", required = true) String pic) {
		
		try {
			return this.downToQiniu(pic);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	@ResponseBody
	@RequestMapping(value = "/weixin_media_to_qiniu", method = RequestMethod.POST)
	public JSONObject weixin_media_to_qiniu(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "media_id", required = true) String media_id) {
		System.out.println("media_id:"+media_id);
		try {
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			
			AccessToken accessToken = WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret());
			String url = WXConstants.download_media
					.replace("ACCESS_TOKEN", accessToken.getAccess_token())
					.replace("MEDIA_ID", media_id);
			System.out.println("media_id:"+url);
			return this.downToQiniu(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private JSONObject downToQiniu(String pic) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String key = null;
		// 构造URL
		URL url = new URL(pic);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为120s
		con.setConnectTimeout(120 * 1000);
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		// byte[] bs = new byte[1024];

		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(accessKey, secretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);

		// ByteArrayInputStream byteInputStream=new ByteArrayInputStream(bs);
		Response qiniuResponse = uploadManager.put(is, key, upToken, null, null);
		// System.out.println(qiniuResponse.bodyString());
		// 解析上传成功的结果
		DefaultPutRet putRet = new Gson().fromJson(qiniuResponse.bodyString(), DefaultPutRet.class);

		JSONObject json = JSONObject.fromObject(qiniuResponse.bodyString());

		// 获取图片的扩展名
		map.put("state", "SUCCESS");
		map.put("title", putRet.hash);
		map.put("url", domain + putRet.key);
		map.put("size", json.getString("fsize"));

		return JSONObject.fromObject(map);
		
	}

}
