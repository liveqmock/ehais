package org.ehais.task;

import java.util.Date;
import java.util.TimeZone;

import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.util.DateUtil;
import org.ehais.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeiXinPayTaskAnnotation {

	protected static Logger log = LoggerFactory.getLogger(WeiXinPayTaskAnnotation.class); 

	@Autowired
	private WeiXinPayService weiXinPayService;
	protected String weixin_cert_p12 = ResourceUtil.getProValue("weixin.cert.p12");//证书路径
	
	
	@Scheduled(cron = "0 0 0/2  * * ? ")
	public void transfers() {
		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
		TimeZone.setDefault(tz);
		
		Date date = new Date();
		log.info("提现计划任务....."+DateUtil.formatDate(date, DateUtil.FORMATSTR_1));

		try {
			weiXinPayService.transfersTask(weixin_cert_p12);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
