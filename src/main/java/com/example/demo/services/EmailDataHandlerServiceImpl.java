package com.example.demo.services;

import com.example.demo.enums.EmailTemplate;
import com.example.demo.models.EmailBean;
import com.example.demo.models.EmailBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
public class EmailDataHandlerServiceImpl {
    @Value("${sender.mail.from}")
    private String mailFrom;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private MailServerManageImpl mailServerManage;

    public String handleDataAndSendMail(EmailBuilder emailBuilder, EmailTemplate emailTemplate, List<String> toUsers, List<String> ccUsers) {
        try {
            log.info("Sending email...");

            // Generate dynamic data
            HashMap<String, Object> dynamicData = generateDynamicData(emailBuilder);

            // Send email
            return sendComposedEmail(emailTemplate, toUsers, ccUsers, dynamicData);
        } catch (RuntimeException e) {
            log.error("Send email exception: {}", e.getMessage(), e);
        }
        return "incomplete";
    }


    public String sendComposedEmail(EmailTemplate emailTemplate, List<String> toUsers, List<String > ccUsers, HashMap<String, Object> dynamicData) {
        log.info("Send Composed email");
        EmailBean emailBean = buildComposedEmail(emailTemplate, toUsers, ccUsers, dynamicData);

        return mailServerManage.sendMail(emailBean);
    }

    private HashMap<String, Object> generateDynamicData(EmailBuilder emailBuilder) {
        HashMap<String, Object> dynamicData = new HashMap<>();
        dynamicData.put("E_CA_LIST", Objects.nonNull(emailBuilder.getCertificateInformationList()) ? emailBuilder.getCertificateInformationList() : null);

        return dynamicData;
    }

    private EmailBean buildComposedEmail(EmailTemplate emailTemplate, List<String> toUsers, List<String> ccUsers, HashMap<String, Object> dynamicData) {
        log.info("To: {}", toUsers.toString());
        log.info("Cc: {}", ccUsers.toString());

        toUsers = toUsers.stream().map(String::toLowerCase).distinct().collect(Collectors.toList());
        ccUsers = ccUsers.stream().map(String::toLowerCase).distinct().collect(Collectors.toList());

        return EmailBean.builder()
                .from(mailFrom)
                .to(String.join(",", toUsers))
                .cc(CollectionUtils.isEmpty(ccUsers) ? null : String.join(",", ccUsers))
                .emailSubject(emailTemplate.baseSubject)
                .emailBody(buildHTMLEmailBodyByEmailTemplateType(emailTemplate, dynamicData))
                .build();
    }

    private String buildHTMLEmailBodyByEmailTemplateType(EmailTemplate emailTemplate, HashMap<String, Object> dynamicData) {
        Context context = new Context(null, dynamicData);

        // Build body
        return springTemplateEngine.process(emailTemplate.templateFileName, context);
    }
}
