package org.ehais.shop.service.impl;

import org.ehais.shop.service.QiniuService;
import org.ehais.util.ResourceUtil;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuServiceImpl implements QiniuService{
	
	
	
	public static void main(String[] args) {
		String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
		String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
		String bucket = "ehaischildren";
		

		
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		String localFilePath = "d:/加盟二维码.png";
		String key = null;
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		System.out.println(upToken);
		StringMap putPolicy = new StringMap();
		
		try {
		    Response response = uploadManager.put(localFilePath, key, upToken);
		    //解析上传成功的结果
		    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		    System.out.println(putRet.key);
		    System.out.println(putRet.hash);
		} catch (QiniuException ex) {
		    Response r = ex.response;
		    System.err.println(r.toString());
		    try {
		        System.err.println(r.bodyString());
		    } catch (QiniuException ex2) {
		        //ignore
		    }
		}

	}
	
	
}
