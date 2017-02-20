package org.ehais.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.sf.json.JSONObject;

public class UploadUtils {

	public static String upload_image(HttpServletRequest request,
			HttpServletResponse response,String path) throws IllegalStateException, IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
//		String path = request.getRealPath("/Uploads/file");
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile filedata = multiRequest.getFile((String) iter
						.next());
				if (filedata != null && !filedata.isEmpty()) {
					// 获取图片的文件名
					String fileName = filedata.getOriginalFilename();
					// 获取图片的扩展名
					String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 新的图片文件名 = 获取时间戳+"."图片扩展名
					String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
					/* 构建文件目录 */
					File fileDir = new File(path);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					File localFile = new File(path + "/" + newFileName);
					// 写文件到本地
					filedata.transferTo(localFile);


					String url = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getLocalPort();
					url += "/eUploads/images/" + newFileName;
					map.put("msg", url);

					map.put("location", path + "/" + newFileName);
				}
			}

		}
		map.put("code", 1);
		map.put("err", "");
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	
	
	public static String upload_shop_image(HttpServletRequest request,
			HttpServletResponse response,String path) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
//		String path = request.getRealPath("/Uploads/file");
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile filedata = multiRequest.getFile((String) iter.next());
				if (filedata != null && !filedata.isEmpty()) {
					// 获取图片的文件名
					String fileName = filedata.getOriginalFilename();
					// 获取图片的扩展名
					String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 新的图片文件名 = 获取时间戳+"."图片扩展名
					String newFileName = String.valueOf(System.currentTimeMillis()) ;
					/* 构建文件目录 */
					File fileDir = new File(path);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					File localFile = new File(path + "/" + newFileName+ "." + extensionName);
					// 写文件到本地
					filedata.transferTo(localFile);

					ScaleImageUtils.scaleImage(120,20,
							path + "/" + newFileName+ "." + extensionName,
							path + "/" + newFileName+ "_120_120." + extensionName,extensionName);
					
					
					String url = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getLocalPort();
					url += "/eUploads/images/" + newFileName+ "." + extensionName;
					
					map.put("msg", url);

					map.put("location", path + "/" + newFileName);
				}
			}

		}
		map.put("code", 1);
		map.put("err", "");
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	
	public static String upload_file(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String path = request.getRealPath("/eUploads/file");
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile filedata = multiRequest.getFile((String) iter
						.next());
				if (filedata != null && !filedata.isEmpty()) {
					// 获取图片的文件名
					String fileName = filedata.getOriginalFilename();
					// 获取图片的扩展名
					String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 新的图片文件名 = 获取时间戳+"."图片扩展名
					String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
					/* 构建文件目录 */
					File fileDir = new File(path);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					File localFile = new File(path + "/" + newFileName);
					// 写文件到本地
					filedata.transferTo(localFile);


					map.put("msg", path + "/" + newFileName);

					
				}
			}

		}
		map.put("code", 1);
		map.put("err", "");
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	
}
