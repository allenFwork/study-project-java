package com.study.util.email;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailUtil {

    /**
     * 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
     * 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）, 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
     * 联想邮箱没有授权码
     */
    public static String myEmailAccount = "cspd@lenovo.com";
    public static String myEmailPassword = "WCjm-7763";
    /**
     * 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
     * 联想邮箱的 SMTP
     */
    public static String myEmailSMTPHost = "smtpinternal.lenovo.com";


    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱列表
     * @param subject     主题
     * @param content     正文
     * @param CC          抄送人列表
     * @return
     * @throws Exception
     */
    public static MimeMessage createSimpleMessage(Session session, String sendMail, List<String> receiveMail,
                                                  String subject, String content, List<String> CC) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        if (receiveMail.size() > 0) {
            InternetAddress[] Recipients = new InternetAddress[receiveMail.size()];
            for (int i = 0; i < receiveMail.size(); i++) {
                Recipients[i] = new InternetAddress(receiveMail.get(i), receiveMail.get(i), "UTF-8");
            }
            message.setRecipients(MimeMessage.RecipientType.TO, Recipients);
        }
        // 抄送人
        if (CC.size() > 0) {
            InternetAddress[] CCRecipients = new InternetAddress[CC.size()];
            for (int i = 0; i < CC.size(); i++) {
                CCRecipients[i] = new InternetAddress(CC.get(i), CC.get(i), "UTF-8");
            }
            message.setRecipients(MimeMessage.RecipientType.CC, CCRecipients);
        }
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建一封只包含文本的简单邮件(联想版本)
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱列表
     * @param subject     主题
     * @param content     正文
     * @param CC          抄送人列表
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, List<String> receiveMail,
                                                String subject, String content, List<String> CC) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        if (receiveMail.size() > 0) {
            InternetAddress[] Recipients = new InternetAddress[receiveMail.size()];
            for (int i = 0; i < receiveMail.size(); i++) {
                Recipients[i] = new InternetAddress(receiveMail.get(i) + "@lenovo.com", receiveMail.get(i) + "@lenovo.com", "UTF-8");
            }
            message.setRecipients(MimeMessage.RecipientType.TO, Recipients);
        }
        // 抄送人
        if (CC.size() > 0) {
            InternetAddress[] CCRecipients = new InternetAddress[CC.size()];
            for (int i = 0; i < CC.size(); i++) {
                CCRecipients[i] = new InternetAddress(CC.get(i) + "@lenovo.com", CC.get(i) + "@lenovo.com", "UTF-8");
            }
            message.setRecipients(MimeMessage.RecipientType.CC, CCRecipients);
        }
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * @param subject   主题
     * @param content   正文
     * @param recipient 接收人
     * @param CC        抄送人
     * @throws Exception
     */
    public static void sendMessage(String subject, String content, List<String> recipient, List<String> CC) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties properties = new Properties();
        // 使用的协议（JavaMail规范要求）
        properties.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP 服务器地址
        properties.setProperty("mail.smtp.host", myEmailSMTPHost);
        // 需要请求认证
        properties.setProperty("mail.smtp.auth", "true");
        /**
         * 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
         * 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
         * 取消下面的注释代码, 开启 SSL 安全连接。
         */
        /*
           SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接, 需要改为对应邮箱的 SMTP 服务器的端口,
           具体可查看对应邮箱服务的帮助, QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
         */
//        String smtpPort = "135";
//        properties.setProperty("mail.smtp.port", smtpPort);
//        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);
//        properties.setProperty("mail.debug","true");

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(properties);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
//        MimeMessage message = createMimeMessage(session, myEmailAccount, recipient, subject, content, CC);
        MimeMessage message = createSimpleMessage(session, myEmailAccount, recipient, subject, content, CC);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        transport.connect(myEmailAccount, myEmailPassword);
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }


}
