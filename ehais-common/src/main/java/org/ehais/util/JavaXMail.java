package org.ehais.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.ehais.model.MailAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class JavaXMail {
	// 日志记录
    private static Logger logger = LoggerFactory.getLogger(JavaXMail.class);

    public static MailAuthenticator authenticator;
    private MimeMessage message;
    private Session session;
    private Transport transport;
    private Properties properties = new Properties();

//    private String mailHost = null;
//    private String sender_username = null;
//    private String sender_password = null;
    
    
    /**
     * 供外界调用的发送邮件接口
     */
    public boolean sendEmail(
    		String mailHost,
    		String mailAccount,
    		String mailZh,
    		String mailPassword,
    		String title, 
    		String content, 
    		List<String> receivers, 
    		List<File> fileList) {
        try {
            // 初始化smtp发送邮件所需参数
            initSmtpParams(mailHost,mailAccount,mailZh,mailPassword);

            // 发送邮件
            doSendHtmlEmail(mailHost,mailAccount,mailZh,mailPassword,title, content, receivers, fileList);

        } catch (Exception e) {
            logger.error("", e);
        }
        return true;
    }
    
    
    /**
     * 初始化smtp发送邮件所需参数
     */
    private boolean initSmtpParams(
    		String mailHost,
    		String mailAccount,
    		String mailZh,
    		String mailPassword) {

    	if( message == null){	        
	
	        properties.put("mail.smtp.host", mailHost);// mail.envisioncitrix.com
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.transport.protocol", "smtp");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.ssl.checkserveridentity", "false");
	        properties.put("mail.smtp.ssl.trust", mailHost);
	
	        authenticator = new MailAuthenticator(mailAccount, mailPassword);
	        session = Session.getInstance(properties, authenticator);
	        session.setDebug(true);// 开启后有调试信息
	        message = new MimeMessage(session);
    	}
        return true;
    }

    /**
     * 发送邮件
     */
    private boolean doSendHtmlEmail(
    		String mailHost,
    		String mailAccount,
    		String mailZh,
    		String mailPassword,
    		String title, 
    		String htmlContent, 
    		List<String> receivers, 
    		List<File> fileList) {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(mailAccount,mailZh,"UTF-8");
            message.setFrom(from);

            // 收件人(多个)
            InternetAddress[] sendTo = new InternetAddress[receivers.size()];
            for (int i = 0; i < receivers.size(); i++) {
                sendTo[i] = new InternetAddress(receivers.get(i),"", "UTF-8");
            }
            message.setRecipients(MimeMessage.RecipientType.TO, sendTo);

            // 邮件主题
            message.setSubject(title);

            // 添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(htmlContent, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 遍历添加附件
            if (fileList != null && fileList.size() > 0) {
                for (File file : fileList) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(MimeUtility.encodeText(file.getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            // 将多媒体对象放到message中
            message.setContent(multipart);

            // 保存邮件
            message.saveChanges();

            // SMTP验证，就是你用来发邮件的邮箱用户名密码
            transport = session.getTransport("smtp");
            transport.connect(mailHost, mailAccount, mailPassword);

            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println(title + " Email send success!");
        } catch (Exception e) {
            logger.error("",e);
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    logger.error("",e);
                }
            }
        }
        return true;
    }

    /**
     * 测试main
     */
    public static void main(String[] args) {
    	
    	String mailHost = "smtp.qq.com"; // 邮箱类型不同值也会不同
    	String mailAccount = "346799512@qq.com";
    	String mailPassword = "lgjun628819ok";
    	String mailZh = "广州中恒软件科技有限公司";
        
        
        // 邮件主题
        String title = "广州中恒软件科技有限公司面试题";

        // 邮件正文
        String htmlContent = "tyler:<p>您好，我司(广州中恒软件科技有限公司)已经收到您发来的应聘资料，由于人数众多，我们也为进一步增加双方间的了解，希望您能够抽空完成一下我们的试题，然后再按排面谈时间。<p><p>应聘职位:java开发工程师<p>如果有问题，可以联系下面联系人<p><p>tyler<p>联系电话 : 13560217994<p>联系QQ : 346799512";

        // 收件人
        List<String> receivers = new ArrayList<String>();
        receivers.add("lgj628@126.com");
        receivers.add("lgj628@qq.com");

        // 附件
        String fileName1 = "E:/LGJ/123BABY/java面试机试试题.docx";
        File file1 = new File(fileName1);
        String fileName2 = "E:/LGJ/123BABY/V5智能客服产品简介/V5智能客服产品简介/【01】V5智能客服系统介绍.pdf";
        File file2 = new File(fileName2);
        String fileName3 = "E:/TDDOWNLOAD/qrcode_for_gh_0c33b4e244d6_258.jpg";
        File file3 = new File(fileName3);
        
        List<File> fileList = new ArrayList<File>();
        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);

        // 执行发送
        new JavaXMail().sendEmail(mailHost,mailAccount,mailZh,mailPassword,title, htmlContent, receivers, fileList);
    }
    
}
