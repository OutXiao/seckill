package com.wenfan.seckill.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author:wenfan
 * @description:
 * @data: 2019/3/9 13:56
 */
//@Service
public class MailService {

    //@Autowired
    private JavaMailSender mailSender; //框架自带的

    @Value("${spring.mail.username}")  //发送人的邮箱  比如155156641XX@163.com
    private String from;

    @Async  //意思是异步调用这个方法
    public void sendMail(String title, String content, String email) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(content, true);
        mailSender.send(mimeMessage); //发送
    }

}