package org.ehais.Junit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.gdrc.GdrcCompany;
import org.ehais.model.gdrc.GdrcCompanyList;
import org.ehais.model.gdrc.GdrcJob;
import org.ehais.model.gdrc.GdrcJobList;
import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

import com.google.gson.Gson;

public class GdrcJUnit {
	
	/**
	 * 单元测试
	 * 公司职位列表
	 * 提供方：各省[广东人才网示例代表]
	 */
	@Test
	public void companyList(){
		String 
		url = "http://www.gdrc.com/talentApi/getCompanyListByDate.do";//广东人才网
//		url = "http://fzlw.gxrc.com/FzData/GetCompanys";//广西人才网[ok]
//		url = "";//江西人才网
//		url = "";//福建人才网
//		url = "";//湖南人才网
//		url = "";//海南人才网
//		url = "http://192.168.1.113/xml/wl/wldao.php?datatype=company";//云南人才网[ok]
//		url = "http://www.gzrc.gov.cn/test/company.php";//贵州人才网
//		url = "http://www.scrc168.com:8082/wljk/IscrcGetCompanyInfo.aspx";//四州人才网[ok]
//		url = "http://www.hxrc.com/DWHZ/hzqydata.ashx";//海峡人才网[ok]
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", "2016-06-01");
		paramsMap.put("endDate", "2016-06-03");
		
		String req = EHttpClientUtil.httpPost(url, paramsMap);
		
		System.out.println("测试请求返回："+req);
		
		GdrcCompanyList gcl = new GdrcCompanyList();
		Gson gson = new Gson();
		gcl = gson.fromJson(req, GdrcCompanyList.class);
		List<GdrcCompany> list = gcl.getDataList();
		for (GdrcCompany gc : list) {
			System.out.println("公司名："+gc.getCompanyName());
		}
        
//		List<GdrcCompanyList> list = new ArrayList<GdrcCompanyList>();
//        
//        Gson gson = new Gson();
//        list = gson.fromJson(req, new TypeToken<List<GdrcCompanyList>>() {
//        }.getType());
        
	}
	
	
	/**
	 * 单元测试
	 * 职位列表
	 * 提供方：各省[广东人才网示例代表]
	 */
	@Test
	public void jobList(){
		String url = "http://www.gdrc.com/talentApi/getJobListByDate.do";
		url = "http://www.hxrc.com/DWHZ/hzjobsdata.ashx";//中国海峡人才市场[ok]
		url = "http://192.168.1.113/xml/wl/wldao.php?datatype=job";//云南【ok】
		url = "";//四川
//		url = "http://fzlw.gxrc.com/FzData/Getjobs";//广西[ok]
		url = "http://www.gzrc.gov.cn/test/zp.php";//贵州[ok]
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", "2016-05-30");
		paramsMap.put("endDate", "2016-05-31");
		
		String req = EHttpClientUtil.httpPost(url, paramsMap);
		
		System.out.println("请求返回："+req);
		
		GdrcJobList gjl = new GdrcJobList();
		Gson gson = new Gson();
		gjl = gson.fromJson(req, GdrcJobList.class);
		List<GdrcJob> list = gjl.getDataList();
		for (GdrcJob gc : list) {
			System.out.println("职位名："+gc.getJobName());
		}
		
	}
	
	/**
	 * 单元测试
	 * 获取简历数据
	 * 提供方：各省[广东人才网示例代表]
	 */
	@Test
	public void getResumeListByDate(){
		String url = "http://www.gdrc.com/talentApi/getResumeListByDate.do";
		url = "http://192.168.1.113/xml/wl/wldao.php?datatype=resume";//云南人才网[ok]
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", "2015-01-01");
		paramsMap.put("endDate", "2016-06-01");
		
		String req = EHttpClientUtil.httpPost(url, paramsMap);
		
		System.out.println("请求返回："+req);
		
	}
	
	/**
	 * 单元测试
	 * 简历工作经验
	 * 提供方：各省[广东人才网示例代表]
	 */
	@Test
	public void getResumeWorkExpListByDate(){
		String url = "http://www.gdrc.com/talentApi/getResumeWorkExpListByDate.do";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("startDate", "2015-01-01");
		paramsMap.put("endDate", "2015-06-01");
		
		String req = EHttpClientUtil.httpPost(url, paramsMap);
		
		System.out.println("请求返回："+req);
		
	}

}
