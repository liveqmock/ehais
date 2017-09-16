package org.ehais.task;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.service.VtuService;
import org.ehais.util.DateUtil;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;  
  
/** 
 * 基于注解的定时器 
 * @author tyler
 */  
@Component  
public class MyTaskAnnotation { 
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private VtuService vtuService;
      
//    /**  
//     * 定时计算。每天凌晨 01:00 执行一次  
//     */    
//    @Scheduled(cron = "0 0 1 * * *")   
//    public void show(){  
//        System.out.println("Annotation：is show run");  
//    }  
//      
//    /**  
//     * 心跳更新。启动时执行一次，之后每隔2秒执行一次  
//     */    
//    @Scheduled(fixedRate = 1000*2)   
//    public void print(){  
//        System.out.println("Annotation：print run");  
//    }  
//    
    @Scheduled(cron="0 0/10 *  * * ? ")
    public void updateUserNickFace(){
    	Date date = new Date();
        System.out.println(DateUtil.formatDate(date, DateUtil.FORMATSTR_1)+"========================================updateUserNickFace");
        
        EHaiUsersExample example = new EHaiUsersExample();
        example.createCriteria()
        .andOpenidIsNotNull()
        .andNicknameIsNull()
        .andStoreIdIsNotNull();
        List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(example);
        System.out.println("listUsers.size()"+listUsers.size());
        for (EHaiUsers eHaiUsers : listUsers) {
        	try{
            	System.out.println(eHaiUsers.getOpenid());
            	WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(eHaiUsers.getStoreId());
            	AccessToken access_token = WeiXinUtil.getAccessToken(eHaiUsers.getStoreId(), wp.getAppid(), wp.getSecret());
            	WeiXinUserInfo userInfo = WeiXinUtil.getUserInfo(access_token.getAccess_token(), eHaiUsers.getOpenid());
            	System.out.println("userInfo.getNickname()"+userInfo.getNickname());
            	System.out.println("userInfo.getHeadimgurl()"+userInfo.getHeadimgurl());
            	System.out.println("userInfo.getSubscribe()"+userInfo.getSubscribe());
            	eHaiUsers.setNickname(userInfo.getNickname());
            	eHaiUsers.setFaceImage(userInfo.getHeadimgurl());
            	eHaiUsers.setSubscribe(userInfo.getSubscribe());
            	            	
            	eHaiUsersMapper.updateByPrimaryKeySelective(eHaiUsers);
            }catch(Exception e){
            	e.printStackTrace();
            }
        	
		}
        
    }
    
    
    @Scheduled(cron="0 0/5 *  * * ? ")
    public void vtuShareRemind(){
    	Date date = new Date();
        System.out.println(DateUtil.formatDate(date, DateUtil.FORMATSTR_1)+"========vtuShareRemind");
        
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
//        servletContext.getContextPath().
//        servletContext.get
        try {
//			vtuService.vtuMessage(request, DateUtil.formatDate(date, "HH:mm"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
}  
