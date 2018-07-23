package org.ehais.enums;

import java.util.HashMap;
import java.util.Map;

public class EArticleModuleEnum {
	public final static String ARTICLE = "article";//文章
	public final static String INFORMATEION = "information";//资讯新闻
	public final static String NEWS = "news";//资讯新闻
	public final static String ACTIVITY = "activity";//资讯新闻
	public final static String INDUSTRY = "industry";//行业动态 
	public final static String JOB = "job";//招聘信息
	public final static String ABOUT = "about";//关于我们
	public final static String AFFIRM = "affirm" ;//资质认定
	public final static String BESTADC = "bestadc"; //最佳广告公司		
	public final static String EVALUATE = "evaluate"; //信用等级评价	
	public final static String BESTAPD  = "bestadp";  //杰出广告人
	public final static String APTITUDE = "aptitude"; //认定资讯
	public final static String BRANDSTORY = "brand_story"; //品牌故事
	public final static String DISTRIBUTIONINTRO = "distribution_intro"; //分销介绍
	public final static String MEDIA = "media";//视频
	
	public final static String DISCOUNTS = "discounts";//优惠促销
	public final static String APPOINTMENT = "appointment";//预约维修
	public final static String RESCUE = "rescue";//道路救援
	public final static String SKILL = "skill";//用车技巧
	public final static String FEATURE = "feature";//特色服务
	
	
	
	public static Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
	
	private static EArticleModuleEnum initAME = null;
	
	public synchronized static EArticleModuleEnum getArticleModuleEnum(){
		if(initAME==null){
			initAME = new EArticleModuleEnum();
    		initAME.createMap(ARTICLE, "文章");
    		initAME.createMap(INFORMATEION, "资讯");
    		initAME.createMap(NEWS, "资讯新闻");
    		initAME.createMap(ACTIVITY, "资讯新闻");
    		initAME.createMap(INDUSTRY, "行业动态 ");
    		initAME.createMap(JOB, "招聘信息");
    		initAME.createMap(ABOUT, "关于我们");
    		initAME.createMap(AFFIRM, "资质认定");
    		initAME.createMap(BESTADC, "最佳广告公司");
    		initAME.createMap(EVALUATE, "信用等级评价");
    		initAME.createMap(BESTAPD, "杰出广告人");
    		initAME.createMap(APTITUDE, "认定资讯");
    		initAME.createMap(BRANDSTORY, "品牌故事");
    		initAME.createMap(DISTRIBUTIONINTRO, "分销介绍");
    		initAME.createMap(MEDIA, "视频");
    		initAME.createMap(DISCOUNTS, "优惠促销");
    		
    		initAME.createMap(APPOINTMENT, "预约维修");
    		initAME.createMap(RESCUE, "道路救援");
    		initAME.createMap(SKILL, "用车技巧");
    		initAME.createMap(FEATURE, "特色服务");
    		
    		System.out.println("初始化 Article Module 信息 **********");
        }
		
		return initAME;
	}
	
	public static String getModuleName(String str){
		
		return map.get(str).get("name").toString();
	}
	
	private static void createMap(String module,String Name) {
		Map<String,Object> module_map = new HashMap<String,Object>();
		module_map.put("name", Name);
		map.put(module, module_map);
	}
	
	
}
