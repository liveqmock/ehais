package org.ehais.Junit;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.ehais.util.JavaXMail;
import org.junit.Test;

public class JMailJUnit {

	 // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    public static String myEmailAccount = "346799512@qq.com";
    public static String myEmailPassword = "lgjun628819ok";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.qq.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "lgj628@126.com";
    
    
    public static void main(String[] args) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.host", myEmailSMTPHost);        // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 请求认证，参数名称与具体实现有关

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }
    
    
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "广州中恒软件科技有限公司", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "用户姓名", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("广州中恒软件科技有限公司面试试题通知", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
//        message.setContent("tyler:<p>您好，我司(广州中恒软件科技有限公司)已经收到您发来的应聘资料，由于人数众多，我们也为进一步增加双方间的了解，希望您能够抽空完成一下我们的试题，然后再按排面谈时间。<p><p>应聘职位:java开发工程师\r\n如果有问题，可以联系下面联系人<p><p>tyler<p>联系电话 : 13560217994<p>联系QQ : 346799512", "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());
        
     // 可以装载多个主体部件！可以把它当成是一个集合
        MimeMultipart partList = new MimeMultipart();
        
        
        message.setContent(partList);// 把邮件的内容设置为多部件的集合对象
        
     // 创建一个部件
        Multipart multipart = new MimeMultipart();
     // 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent("tyler:<p>您好，我司(广州中恒软件科技有限公司)已经收到您发来的应聘资料，由于人数众多，我们也为进一步增加双方间的了解，希望您能够抽空完成一下我们的试题，然后再按排面谈时间。<p><p>应聘职位:java开发工程师<p>如果有问题，可以联系下面联系人<p><p>tyler<p>联系电话 : 13560217994<p>联系QQ : 346799512", "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        // 给部件指定内容
//        part1.setContent("tyler:\r\n您好，我司(广州中恒软件科技有限公司)已经收到您发来的应聘资料，由于人数众多，我们也为进一步增加双方间的了解，希望您能够抽空完成一下我们的试题，然后再按排面谈时间。\r\n\r\n应聘职位:java开发工程师\r\n如果有问题，可以联系下面联系人\r\n\r\ntyler\r\n联系电话 : 13560217994\r\n联系QQ : 346799512", "text/html;charset=utf-8");
        
     // 遍历添加附件
//        if (fileList != null && fileList.size() > 0) {
//            for (File file : fileList) {
//                BodyPart attachmentBodyPart = new MimeBodyPart();
//                DataSource source = new FileDataSource(file);
//                attachmentBodyPart.setDataHandler(new DataHandler(source));
//                attachmentBodyPart.setFileName(file.getName());
//                multipart.addBodyPart(attachmentBodyPart);
//            }
//        }
//        
        // 为部件指定附件
        BodyPart attachmentBodyPart = new MimeBodyPart();
        File file = new File("E:/LGJ/123BABY/java面试机试试题.docx");
        DataSource source = new FileDataSource(file);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(MimeUtility.encodeText(file.getName()));
        multipart.addBodyPart(attachmentBodyPart);
        
     // 将多媒体对象放到message中
        message.setContent(multipart);

        
     // 7. 保存设置
        message.saveChanges();

        return message;
    }
    
    
    @Test
    public void topzh(){
    	String mailHost = "web.topzh.com"; // 邮箱类型不同值也会不同
    	String mailAccount = "hr2@topzh.com";
    	String mailPassword = "lgj2017";
    	String mailZh = "广州中恒软件科技有限公司";        
        // 邮件主题
        String title = "广州中恒软件科技有限公司面试题";
        // 邮件正文
        String htmlContent = ":<p>您好，我司(广州中恒软件科技有限公司)已经收到您发来的应聘资料，由于人数众多，我们也为进一步增加双方间的了解，希望您能够抽空完成一下我们的试题，然后再按排面谈时间。<p><p>应聘职位:java开发工程师<p>如果有问题，可以联系下面联系人<p><p>tyler<p>联系电话 : 13560217994<p>联系QQ : 346799512";

    	Map<String,String> map = new HashMap<String,String>();
//    	map.put("邓哲", "dengzhe17@sina.com");
//    	map.put("陈浩斌", "chenhaobin4991@163.com");
//    	map.put("郑清俊", "2511442443@qq.com");
//    	map.put("何铭叶", "378282677@qq.com");
//    	map.put("张进", "995429111@qq.com");
//    	map.put("司栋立", "sdllovehh@outlook.com");
//    	map.put("陈柏青", "smilecbq@163.com");
//    	map.put("莫卡娌", "m13087988841@163.com");
//    	map.put("潘恒飞", "846839741@qq.com");
//    	map.put("柳浩哲", "928776712@qq.com");
//    	map.put("陈艺", "871822917@qq.com");
//    	map.put("李晓鸿", "1614433651@qq.com");
//    	map.put("詹友文", "zhanyouwen008@163.com");
//    	map.put("沈志豪", "714384013@qq.com");
//    	map.put("马腾", "1342424801@qq.com");
//    	map.put("谢文韬", "xwtdeyx@163.com");
//    	map.put("陈子傲", "m13600301716@163.com");
//    	map.put("万文广", "554931084@qq.com");
//    	map.put("彭啟枫", "2528375378@qq.com");
//    	map.put("熊伟", "1435958740@qq.com");
//    	map.put("聂孛", "806353521@qq.com");
//    	map.put("曹万合", "458584881@qq.com");
//    	map.put("彭园园", "15622385282@163.com");
//    	map.put("吴清宏", "260949433@qq.com");
//    	map.put("彭南宁", "454116643@qq.com");
//    	map.put("张彦泽", "841202163@qq.com");
//    	map.put("吴成扬", "609897538@qq.com");
//    	map.put("韦斌", "398022229@qq.com");
//    	map.put("王军", "wangjun2332@163.com");
//    	map.put("彭成学", "13480375727@163.com");
//    	map.put("谭国安", "1056120099@qq.com");
//    	map.put("李志豪", "736074988@qq.com");
//    	map.put("陈浩斌", "18122429589@163.com");
//    	map.put("尹扬", "y_inyang@163.com");
//    	map.put("刘永明", "1310406543@qq.com");
//    	map.put("林立海", "linlihai88@126.com");
//    	map.put("梁万全", "932362851@qq.com");
//    	map.put("陆明坤", "lmk_lw@163.com");
//    	map.put("赖福伟", "1393307257@qq.com");
//    	map.put("李晓航", "184258267@qq.com");
//    	map.put("蔡金龙", "2279139128@qq.com");
//    	map.put("黎峻彰", "376674536@qq.com");
//    	map.put("柯文修", "904358838@qq.com");
//    	map.put("葛佳鑫", "1207047562@qq.com");
//    	map.put("韦尔桂", "463755064@qq.com");
//    	map.put("黄海云", "hhy1118m@163.com");
//    	map.put("李鹏程", "lpc102703@163.com");
//    	map.put("", "");
//    	map.put("", "");
//    	map.put("", "");
//    	map.put("", "");
//    	map.put("", "");
//    	map.put("", "");
//    	map.put("梁国俊", "lgj628@qq.com");
    	
    	
    	for (String key : map.keySet()) {

    	     List<String> receivers = new ArrayList<String>();
    	     receivers.add(map.get(key.trim()).trim());
    	     
    	     String fileName1 = "E:/LGJ/123BABY/java面试机试试题.docx";
    	     File file1 = new File(fileName1);
    	     List<File> fileList = new ArrayList<File>();
    	     fileList.add(file1); 
    	     new JavaXMail().sendEmail(mailHost,mailAccount,mailZh,mailPassword,title, key+htmlContent, receivers, fileList);
    	}
    	
    	
    }
    
}
