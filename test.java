package com.sbi.epay.notification.thirdpartyservice;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.notification.exception.NotificationException;
import com.sbi.epay.notification.model.EmailDto;
import com.sbi.epay.notification.service.EmailTemplateService;
import com.sbi.epay.notification.util.NotificationConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;

/**
 * Class Name: EmailClient
 * *
 * Description:EmailClient this class mainly used for sending mail and createMessage and MimeMessageHelper
 * *
 * Author: V1017903 (bhushan wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class EmailClient {
    private final JavaMailSender javaMailSender;
    private final EmailTemplateService emailTemplateService;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailClient.class);

    /**
     * This method will be used for calling createMimeMessage and send method
     *
     * @param emailDTO object for calling createMimeMessage method
     * @return true if email send successfully otherwise return false
     * @throws NotificationException if any exception occur
     */
    public boolean sendEmail(EmailDto emailDTO) throws NotificationException {
        logger.info("ClassName - EmailClient,MethodName - sendEmail,Method-start");
        try {
            MimeMessage message = createMimeMessage(emailDTO);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            logger.info("ClassName - EmailClient,MethodName - sendEmail, inside catch" + e);
            throw new NotificationException(NotificationConstant.FAILURE_CODE, MessageFormat.format(NotificationConstant.FAILURE_MSG, "EMail"));
        }
    }

    /**
     * This method will be used for createMimeMessage
     *
     * @param emailDTO object for mimeMessage
     * @return notification message
     * @throws MessagingException if any exception occur
     */
    private MimeMessage createMimeMessage(EmailDto emailDTO) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-start");
        MimeMessage message = javaMailSender.createMimeMessage();
        setMimeMessageHelper(emailDTO, message);
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-end");
        return message;
    }

    /**
     * This method will be used for set email details into MimeMessageHelper
     *
     * @param emailDTO for set parameter into MimeMessageHelper
     * @param message MimeMessage
     */
    private void setMimeMessageHelper(EmailDto emailDTO, MimeMessage message) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-start");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailDTO.getFrom());
        helper.setTo(emailDTO.getRecipient());
        helper.setSubject(emailDTO.getSubject());
        String emailContent = emailTemplateService.generateEmailBody(emailDTO.getEmailTemplate(), emailDTO.getBody());
        helper.setText(emailContent, true); // true indicates HTML content
        if (StringUtils.isNotEmpty(emailDTO.getCc())) {
            helper.setCc(emailDTO.getCc());
        }
        if (StringUtils.isNotEmpty(emailDTO.getBcc())) {
            helper.setBcc(emailDTO.getBcc());
        }
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-end");
    }

}
