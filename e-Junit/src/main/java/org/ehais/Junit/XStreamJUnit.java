package org.ehais.Junit;

import java.io.IOException;
import java.util.List;

import org.ehais.epublic.model.weixin.WxTransfersResult;
import org.ehais.model.eApi.ApiParameter;
import org.ehais.model.eApi.eParameter;
import org.ehais.util.Bean2Utils;
import org.ehais.util.FSO;
import org.ehais.util.XStreamUtil;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class XStreamJUnit {

	@Test
	public void apiP(){
		String eApiParameterContent = "D:/workspace_luna/ehais/tracking/src/main/resources/config/ApiParameter.xml";
		try {
			String menu_content = FSO.BufferedReader(eApiParameterContent);
//			System.out.println(menu_content);
			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true); 
			xStream.alias("xml",eParameter.class);
			xStream.alias("api",ApiParameter.class);
			eParameter p = (eParameter)xStream.fromXML(menu_content);
			List<ApiParameter> apiP = p.getApiParameter();
			for (ApiParameter apiParameter : apiP) {
				System.out.println(apiParameter.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void payre(){
		String xml = "<xml>" + 
				"<return_code><![CDATA[SUCCESS]]></return_code>" + 
				"<return_msg><![CDATA[参数错误：输入的商户订单号有误]]></return_msg>" + 
				"<result_code><![CDATA[FAIL]]></result_code>" + 
				"<err_code><![CDATA[PARAM_ERROR]]></err_code>" + 
				"<err_code_des><![CDATA[参数错误：输入的商户订单号有误]]></err_code_des>" + 
				"</xml>";
		
		WxTransfersResult wxtr = XStreamUtil.toBean(xml, WxTransfersResult.class);
		
		Bean2Utils.printEntity(wxtr);
		
	}
}
