package com.example.demo.services;

import com.example.demo.models.EmailBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Slf4j
@Service
public class MailServerManageImpl {

    @Value("${sender.mail.host}")
    private String mailHost;

    @Value("${sender.mail.port}")
    private String mailPort;

    public String sendMail(EmailBean emailBean) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailHost);
            mailSender.setPort(Integer.parseInt(mailPort));

            // Set Auth properties
            Properties mailProperties = new Properties();
//            mailProperties.setProperty("mail.smtp.auth", "true");
//            mailProperties.setProperty("mail.smtp.starttls.enable", "true");

            mailSender.setJavaMailProperties(mailProperties);

            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(emailBean.getEmailSubject());
            message.setText(emailBean.getEmailBody(), "utf-8", "html");
            message.setFrom(new InternetAddress(emailBean.getFrom()));

            List<String> to = new ArrayList<>();
            if (Objects.nonNull(emailBean.getTo())) {
                to = getEmailList(emailBean.getTo());
            }
            if (!to.isEmpty()) {
                for (String str : to) {
                    message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(str)));
                }
            }

            List<String> cc = new ArrayList<>();
            if (Objects.nonNull(emailBean.getCc())) {
                cc = getEmailList(emailBean.getCc());
            }
            if (!cc.isEmpty()) {
                for (String str : cc) {
                    message.addRecipients(Message.RecipientType.CC, String.valueOf(new InternetAddress(str)));
                }
            }

            mailSender.send(message);
            log.info("Email was sent successfully");
            return "success";
        } catch (MessagingException e) {
            log.error("Cannot send email", e);
            return "fail";
        }
    }

    private List<String> getEmailList(String str) {
        List<String> humans = new ArrayList<>();
        if (Objects.nonNull(str) && str.contains(",")) {
            String[] cced = str.split(",");
            humans = Arrays.asList(cced);
        } else {
            humans.add(str);
        }
        return humans;
    }

}
