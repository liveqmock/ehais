package org.ehais.util;

import com.qiniu.util.Auth;

public class QiniuUtil {

	
	
	public static String getUpToken(String accessKey, String secretKey , String bucket){
		Auth auth = Auth.create(accessKey, secretKey);
		return auth.uploadToken(bucket);
	}
	
	
}
