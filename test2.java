    server.port=9092
server.servlet.context-path=/transaction/v1

cors.allowedOrigins=*
security.whitelist.url=/webjars/, /actuator/, /swagger-resources/, /v3/api-docs, /swagger-ui/, /swagger-ui.html, /token,/downtime/api,/s1/fetch-data
whitelisted.endpoints=/webjars/, /actuator/, /swagger-resources/, /v3/api-docs, /swagger-ui/, /swagger-ui.html, /token,/downtime/api,/s1/fetch-data
jwt.secret.key=gdjfgskjfhsdjkhkflkdlksdlfkskfwperip3ke3le3lmldrnkfnhiewjfejfokepfkldkfoikfokork3dklwedlsvflvkfkvlkdfvodkvcdokro3

# Db connectivity
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.show_sql=true
spring.datasource.url=jdbc:oracle:thin:@10.177.134.124:1590:epaydbdev1
spring.datasource.username=PAYAGGTRANSCTION
spring.datasource.password=PAYAGGTRANSCTION
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
hibernate.dialect=org.hibernate.dialect.OracleDialect

# Optional settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.max-lifetime=2000000

#In Minutes
transaction.token.expiry.time=30
token.expiry.time.hr=5

# Liquibase Properties
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.liquibase.enabled=false
spring.liquibase.drop-first=false
logging.level.liquibase=DEBUG

#External Base Path
external.api.merchant.services.base.path=http://localhost:8080/api/merchant/v1
external.api.admin.services.base.path=http://localhost:8080/api/admin/v1
external.api.payment.services.base.path=https://dev.epay.sbi/payments/v1
external.web.transaction.service.base.path=https://dev.epay.sbi/2.0/#
scheduler.cron.expression=0 */5 * * * ?








distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
#distributionUrl=https\://services.gradle.org/distributions/gradle-8.10.1-bin.zip
distributionUrl=file\:/E:/gradle/gradle-8.9-bin.zip
#networkTimeout=10000
#validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
