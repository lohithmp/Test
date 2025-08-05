package com.sbi.epay.notification.thirdpartyservice;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import com.sbi.epay.notification.config.EmailConfig;
import com.sbi.epay.notification.exception.NotificationException;
import com.sbi.epay.notification.model.EmailDto;
import com.sbi.epay.notification.service.EmailTemplateService;
import com.sbi.epay.notification.util.NotificationConstant;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class EmailClientService {

    private final EmailTemplateService emailTemplateService;

    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailClientService.class);

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public static void main(String[] args) {
        EmailTemplateService emailTemplateService1 = new EmailTemplateService(new TemplateEngine());
        EmailClientService emailClientService = new EmailClientService(emailTemplateService1);
        EmailConfig emailConfig = new EmailConfig();
        emailClientService.createMailSession(emailConfig);

        Map<String, Object> emailBody = new HashMap<>();
        emailBody.put("firstName", "Bhoopendra");
        emailBody.put("generatedOTP", 052420);
        emailBody.put("loginId", "Bhoopen");
        emailBody.put("aggId", "SBIePay Merchant Portal");



        EmailDto emailDto = EmailDto.builder()
                .recipient("ebms_uat_receiver@ebmsgits.sbi.co.in")
                .subject("ResetPassword Otp")
                .from("ebms_uat_sender@ebmsgits.sbi.co.in")
                .cc("ebms_uat_receiver@ebmsgits.sbi.co.in")
                .body(emailBody)
                .emailTemplate("reset_password_otp")
                .build();
        emailClientService.sendEmail(emailDto, emailConfig);
    }

    public Session createMailSession(EmailConfig emailConfig) {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.port", emailConfig.getPort());


        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });
    }

    public boolean sendEmail(EmailDto emailDto, EmailConfig emailConfig) {
        logger.info("ClassName - EmailClient,MethodName - sendEmail,Method-start");

        try {
            Message message = createMimeMessage(emailDto, emailConfig);
            Transport.send(message);
            logger.info("Email sent successfully.");
            return true;
        } catch (SendFailedException sfe) {
            logger.error("ClassName - EmailClient,MethodName - sendEmail, inside catch, SendFailedException"+ Arrays.toString(sfe.getInvalidAddresses()) + sfe);
            logger.error("sfe Error meesage ==>"+ sfe.getMessage());
            throw new NotificationException(NotificationConstant.FAILURE_CODE, MessageFormat.format(NotificationConstant.FAILURE_MSG, "Email"));

        } catch (MessagingException e) {
            logger.error("ClassName - EmailClient,MethodName - sendEmail, inside catch" + e);
            logger.error("Error meesage ==>"+ e.getMessage());
            throw new NotificationException(NotificationConstant.FAILURE_CODE, MessageFormat.format(NotificationConstant.FAILURE_MSG, "Email"));
        }
    }

    private Message createMimeMessage(EmailDto emailDto, EmailConfig emailConfig) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-start");
        Session session = createMailSession(emailConfig);
        Message message = new MimeMessage(session);
        setMimeMessageHelper(emailDto, message);
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-end");
        return message;
    }

    private void setMimeMessageHelper(EmailDto emailDto, Message message) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-start");
        message.setFrom(new InternetAddress(emailDto.getFrom()));

        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(emailDto.getRecipient()));
        message.setSubject(emailDto.getSubject());

        String emailContent = emailTemplateService.generateEmailBody(emailDto.getEmailTemplate(), emailDto.getBody());
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(emailContent, "text/html");

        if (StringUtils.isNotEmpty(emailDto.getCc())) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailDto.getCc()));
        }
        if (StringUtils.isNotEmpty(emailDto.getBcc())) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emailDto.getBcc()));
        }
        logger.info("ClassName - EmailClient,MethodName - setMimeMessageHelper,Method-end");
    }
}


package com.sbi.epay.notification.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

}

