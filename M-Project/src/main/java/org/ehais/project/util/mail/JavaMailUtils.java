package org.ehais.project.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.ehais.util.ResourceUtil;

public class JavaMailUtils {

	public static void createSimpleMail(String RecipientEmail, String title, String content) throws Exception {

//		MyAuthenticator authenticator = new MyAuthenticator(ResourceUtil.getProValue("mail.account"), "lgjun628819ok"); 
//		Properties prop = new Properties();
//		prop.setProperty("mail.host", ResourceUtil.getProValue("mail.host"));
//		prop.setProperty("mail.smtp.port", ResourceUtil.getProValue("mail.smtp.port"));		
//		prop.setProperty("mail.transport.protocol", ResourceUtil.getProValue("mail.transport.protocol"));
//		prop.setProperty("mail.smtp.auth", ResourceUtil.getProValue("mail.smtp.auth"));
		
//		MyAuthenticator authenticator = new MyAuthenticator("346799512@qq.com", "lgjun628819ok"); 
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.smtp.port", "25");		
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
//		Session session = Session.getDefaultInstance(prop,authenticator);
		
		Session session = Session.getInstance(prop, new Authenticator(){
	          protected PasswordAuthentication getPasswordAuthentication() {
	              return new PasswordAuthentication("346799512@qq.com", "lgjun628819ok");
	          }});
		
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(false);
//		
		// 2、通过session得到transport对象
//		Transport ts = session.getTransport();
		// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
//		ts.connect(ResourceUtil.getProValue("mail.host"), ResourceUtil.getProValue("mail.account"), ResourceUtil.getProValue("mail.password"));
		
		
		// 创建邮件对象
		MimeMessage mailMessage = new MimeMessage(session);
		mailMessage.setSentDate(new Date());  // 设置邮件消息发送的时间   
		// 指明邮件的发件人
		mailMessage.setFrom(new InternetAddress("346799512@qq.com"));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(RecipientEmail));
		// 邮件的标题
		mailMessage.setSubject(title);
		// 邮件的文本内容
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象   
		Multipart mainPart = new MimeMultipart();   
		// 创建一个包含HTML内容的MimeBodyPart   
		BodyPart html = new MimeBodyPart();
//		mailMessage.setContent(content, "text/html;charset=UTF-8");
		html.setContent(content, "text/html;charset=UTF-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容 
		mailMessage.setContent(mainPart);
		// 返回创建好的邮件对象
		Transport.send(mailMessage);  
//		ts.sendMessage(message, message.getAllRecipients());
//		ts.close();
		
	}

}
