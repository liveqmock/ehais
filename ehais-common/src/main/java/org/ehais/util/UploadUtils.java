package org.ehais.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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


//					String url = request.getScheme() + "://"+ request.getServerName() ;//+ ":"+ request.getLocalPort();
					String url = "/eUploads/images/" + newFileName;
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
					
					
//					String url = request.getScheme() + "://" + request.getServerName() ;//+ ":"+ request.getLocalPort();
					String url = "/eUploads/images/" + newFileName+ "." + extensionName;
					
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
	
	
	
	public static String upload_video(HttpServletRequest request,
			HttpServletResponse response,
			String video_path,boolean transfer,String posturl,String postfix,String website
			) throws IllegalStateException, IOException{
		System.out.println("common upload start");
		String path = video_path;
		String pre_path = "/eUploads/video";
		if(StringUtils.isBlank(video_path)){
			path = request.getRealPath(pre_path);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 解析器解析request的上下文
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			System.out.println("if multipartResolver.isMultipart(request).........");
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				System.out.println("while (iter.hasNext()).............");
				MultipartFile filedata = multiRequest.getFile((String) iter
						.next());
				if (filedata != null && !filedata.isEmpty()) {
					System.out.println("if (filedata != null && !filedata.isEmpty())..............");
					// 获取图片的文件名
					String fileName = filedata.getOriginalFilename();
					System.out.println("fileName:::::"+fileName);
					// 获取图片的扩展名
					String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
					if(StringUtils.isNotBlank(postfix)){
						//判断后缀名是否正确
						boolean post = false;
						List<String> result = Arrays.asList(StringUtils.split(postfix,","));  
						for (String string : result) {
							if(string.equals(extensionName)){
								post = true;
								break;
							}
						}
						if(!post){
							map.put("code", 0);
							map.put("err", "文件格式不正确");
							JSONObject json = JSONObject.fromObject(map);
							return json.toString();
						}
					}
					
					// 新的图片文件名 = 获取时间戳+"."图片扩展名
					String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
					/* 构建文件目录 */
					File fileDir = new File(path);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					System.out.println("path....:"+path + "/" + newFileName);
					File localFile = new File(path + "/" + newFileName);
					// 写文件到本地
					filedata.transferTo(localFile);

//					String url = website;
					String url = "";
					
//					if(StringUtils.isBlank(website)){
//						url = request.getScheme() + "://" + request.getServerName() ;//+ ":" + request.getLocalPort();
//					}
					
//					if(StringUtils.isBlank(video_path)){
//						url += pre_path;
//					}
					
					//需要转到某服务器
					if(transfer && StringUtils.isNotBlank(posturl)){
						System.out.println("中转视频...");
						Map<String, String> fileMap = new HashMap<String, String>();
						fileMap.put(newFileName, path + "/" + newFileName);
						
						Map<String , String > headerMap = new HashMap<String , String >();
//						headerMap.put("Content-type", "application/x-www-form-urlencoded");  
//						headerMap.put("Content-type", "multipart/form-data");
//						headerMap.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
						
						String req = EHttpClientUtil.postHttpClientFile(posturl, null, fileMap, headerMap);
						System.out.println("远程返回："+req);
						JSONObject json = JSONObject.fromObject(req);
						map.put("msg", json.getString("msg"));
						
						FSO.deletefile(path + "/" + newFileName);
						
					}else{
						System.out.println("直接保存。。。"+url + "/" + newFileName);
						map.put("msg", url + "/" + newFileName);
					}

				}
			}

		}
		map.put("code", 1);
		map.put("err", "");
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}
	
	
}
