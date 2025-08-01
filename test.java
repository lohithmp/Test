package com.sbi.epay.notification.thirdpartyservice;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
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
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    private final EmailTemplateService emailTemplateService;

    LoggerUtility logger = LoggerFactoryUtility.getLogger(EmailClientService.class);

    public Session createMailSession() {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "XXXXXXX");
        props.put("mail.smtp.port", "587");


        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("XXXX", "XXXXX");
            }
        });
    }

    public boolean sendEmail(EmailDto emailDto) {
        logger.info("ClassName - EmailClient,MethodName - sendEmail,Method-start");

        try {
            Message message = createMimeMessage(emailDto);
            Transport.send(message);
            return true;
        } catch (SendFailedException sfe) {
            logger.error("ClassName - EmailClient,MethodName - sendEmail, inside catch, SendFailedException"+ Arrays.toString(sfe.getInvalidAddresses()) + sfe);
            throw new NotificationException(NotificationConstant.FAILURE_CODE, MessageFormat.format(NotificationConstant.FAILURE_MSG, "Email"));
        } catch (MessagingException e) {
            logger.error("ClassName - EmailClient,MethodName - sendEmail, inside catch" + e);
            throw new NotificationException(NotificationConstant.FAILURE_CODE, MessageFormat.format(NotificationConstant.FAILURE_MSG, "Email"));
        }
    }

    private Message createMimeMessage(EmailDto emailDto) throws MessagingException {
        logger.info("ClassName - EmailClient,MethodName - createMimeMessage,Method-start");
        Session session = createMailSession();
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



plugins {
	id 'java'
	id 'maven-publish'
}

group = 'com.sbi.epay'
version = "${version}"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
	maven {
		url "https://gitlab.epay.sbi/api/v4/projects/16/packages/maven"
		credentials(PasswordCredentials) {
			username = project.findProperty("gitlab.username") ?: System.getenv("CI_USERNAME")
			password = project.findProperty("gitlab.token") ?: System.getenv("CI_JOB_TOKEN")
		}
		authentication {
			basic(BasicAuthentication)
		}
	}
}

publishing {
	publications {
		mavenJava(MavenPublication) {
			from components.java
		}
	}
	repositories {
		maven {
			url "https://gitlab.epay.sbi/api/v4/projects/16/packages/maven"
			credentials(PasswordCredentials) {
				username = project.findProperty("gitlab.username") ?: System.getenv("CI_USERNAME")
				password = project.findProperty("gitlab.token") ?: System.getenv("CI_JOB_TOKEN")
			}
			authentication {
				basic(BasicAuthentication)
			}
		}
	}
}

dependencies {
	implementation "org.springframework:spring-webflux:${webflux}"
	implementation "org.springframework:spring-webmvc:${webmvc}"
	implementation "org.springframework:spring-context-support:${spring_context}"

	implementation "org.thymeleaf:thymeleaf:${thymeleaf}"
	implementation "org.thymeleaf:thymeleaf-spring5:${thymeleaf}"
	implementation "org.hibernate.validator:hibernate-validator:${hibernate}"

	implementation "com.sun.mail:jakarta.mail:2.0.1"
	implementation "org.eclipse.angus:angus-mail:2.0.2"


//	implementation "com.google.code.gson:gson:${gson}"
	implementation "org.apache.commons:commons-lang3:${apache_common}"

	// Lombok
	compileOnly "org.projectlombok:lombok:${lombok}"
	annotationProcessor "org.projectlombok:lombok:${lombok}"

	implementation "com.sbi.epay:logging-service:${logg_version}"
//	implementation "javax.servlet:javax.servlet-api:${javax_servlet}"

	// Mockito
	testImplementation "org.mockito:mockito-core:${mockito}"
	// JUnit Platform Launcher
	testRuntimeOnly "org.junit.platform:junit-platform-launcher:${junit}"
	// JUnit Jupiter (for running JUnit 5 tests)
	testImplementation "org.junit.jupiter:junit-jupiter:${junit_jupiter}"
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
	useJUnitPlatform()
}
