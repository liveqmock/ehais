package org.ehais.task;

import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;  
  
/** 
 * 基于注解的定时器 
 * @author tyler
 */  
@Component  
public class MyTaskAnnotation {  
      
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
//    @Scheduled(cron="0/5 * *  * * ? ")
//    public void taskCycle(){
//        System.out.println("=========================================a");
//    }
    
    
}  
