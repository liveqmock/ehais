package cn.org.test;

import org.ehais.util.EHttpClientUtil;

public class MTest {
	
	
	public static void main(String[] args) {
		
		try{

			String url = "http://localhost:81/wx1.do?signature=788f7b755153a4db0296c3b08ee9de33524fb6a7&timestamp=1490857451&nonce=999843842&echostr=1623628383118163451";
			
			String result = EHttpClientUtil.methodGet(url);
			System.out.println("mtest返回来的："+result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
