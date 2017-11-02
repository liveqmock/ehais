package org.ehais.Junit;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

public class HttpClientJUnit {

	@Test
	public void getm() throws Exception{
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=wx722e9a654b5d58cc&secret=63be13ed69ff195b599d4ccd53b8ca93&code=021zxDLZ1J8I9Y0Cu3NZ1cVzLZ1zxDL8";
		String request = EHttpClientUtil.methodGet(requestUrl);
		System.out.println(request);
	}
	
}
