plugins {
	id 'java'
	id 'org.springframework.boot' version "${spring_boot}"
	id 'io.spring.dependency-management' version "${dependency_plugin}"
}

group = 'com.epay.merchant'
version = "${version}"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	flatDir {
		dirs "libs"
	}
	maven {
		url "https://gitlab.epay.sbi/api/v4/projects/16/packages/maven"
		credentials(PasswordCredentials) {
			username = project.findProperty("gitlab.username")?: System.getenv("CI_USERNAME")
			password = project.findProperty("gitlab.token")?: System.getenv("CI_JOB_TOKEN")
		}
		authentication {
			basic(BasicAuthentication)
		}
	}
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-webflux'
	implementation 'org.springframework.boot:spring-boot-starter-json'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-security'
//	implementation 'org.springframework.boot:spring-boot-starter-mail'
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-aop'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation "org.springframework.boot:spring-boot-devtools:${spring_boot_devtools}"
	implementation "org.springframework:spring-context-support:${spring_context}"
	implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${swagger}"

//	implementation "javax.persistence:javax.persistence-api:${javax_persistence}"
	implementation "org.hibernate.orm:hibernate-envers"
	implementation "com.oracle.database.jdbc:ojdbc11:${oracle_driver}"
    implementation 'org.liquibase:liquibase-core'

	implementation "net.javacrumbs.shedlock:shedlock-spring:${shedlock}"
	implementation "net.javacrumbs.shedlock:shedlock-provider-jdbc-template:${shedlock}"

	implementation "commons-io:commons-io:${commons_io}"

	implementation "com.itextpdf:itext-core:${itext}"
	implementation "com.itextpdf:bouncy-castle-adapter:${itext}"
	implementation "com.fasterxml.jackson.core:jackson-databind:${jackson_databind}"
    implementation "com.fasterxml.uuid:java-uuid-generator:${jackson_uuid_generator}"

    implementation 'org.springframework.kafka:spring-kafka'
	//keep lombok then mapstruct
	implementation 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'

	implementation "org.mapstruct:mapstruct:${mapstruct}"
	annotationProcessor "org.mapstruct:mapstruct-processor:${mapstruct}"
	implementation "org.projectlombok:lombok-mapstruct-binding:${lombok_mapstruct}"

	//Utility
	implementation "com.sbi.epay:logging-service:${sbi_logging}"
	implementation "com.sbi.epay:encryption-decryption-service:${sbi_crypto}"
	implementation "com.sbi.epay:authentication-service:${sbi_auth}"
//	implementation "com.sbi.epay:notification-service:${sbi_notification}"
	implementation "name:notification-service-0.0.1"
	implementation "com.sbi.epay:captcha-service:${sbi_captcha}"

	implementation "net.sf.sociaal:freetts:${freeTTS}"

//	implementation "javax.mail:javax.mail-api:${javax_mail}"
//	implementation "com.sun.mail:javax.mail:${javax_mail}"
	implementation "org.eclipse.angus:angus-mail:2.0.2"
	implementation "com.sun.activation:jakarta.activation:2.0.1"
	implementation "com.sun.mail:jakarta.mail:2.0.1"
	implementation "org.thymeleaf:thymeleaf:${thymeleaf}"
	implementation "org.thymeleaf:thymeleaf-spring5:${thymeleaf}"
	implementation "org.xhtmlrenderer:flying-saucer-pdf:${flying_saucer_pdf}"

	implementation "com.jhlabs:filters:${jhlabs}"
	implementation "org.apache.commons:commons-csv:${commons_csv}"
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
	testCompileOnly "org.mapstruct:mapstruct:${mapstruct}"

}

configurations {
	all*.exclude module : 'spring-boot-starter-logging'
	all*.exclude module : 'slf4j-simple'
}

configurations.all {
	exclude group: 'org.springframework.boot',
			module: 'spring-boot-starter-mail'
	exclude group: 'javax.mail',
			module: 'mail'
	exclude group: 'com.sun.mail',
			module: 'javax.mail'
	exclude group: 'javax.activation',
			module: 'activation'
	exclude group: 'javax.xml.bind',
			module: 'jaxb-api'
}

tasks.withType(JavaExec).configureEach {
	jvmArgs(['--add-opens=java.base/java.lang=ALL-UNNAMED'])
}

tasks.named('test') {
	useJUnitPlatform()
}
springBoot  {
	buildInfo()
}
bootJar {
	duplicatesStrategy(DuplicatesStrategy.EXCLUDE)
}
