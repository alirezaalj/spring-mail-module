# spring-mail-module

Starter Mail Module - send Mails With html templates using thymeleaf engin - simple text Mails : base on Objects You can
use this module as Dependency in your projects and create your own objects and html templates

## How to use

GitHub Repository: add to **`pom.xml`** File

```xml

<repositories>
   <repository>
      <id>alijani-gitlab-maven</id>
      <url>https://gitlab.com/api/v4/projects/39316022/packages/maven</url>
      <snapshots>
         <enabled>true</enabled>
         <updatePolicy>always</updatePolicy>
      </snapshots>
   </repository>
</repositories>
```

Module Dependency: add in **`pom.xml`** inside **`dependencies`** section

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.retry</groupId>
        <artifactId>spring-retry</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>ir.alirezaalijani</groupId>
        <artifactId>spring-mail-module</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>

```

## Usage:

1. Configuration: module configuration in your project `application.yml` file

```yaml
my-spring:
  mail:
    host: smtp.gmail.com # your smtp host
    port: 587 # your smtp host port
    username: gmail_account_name # your smtp account username
    password: gmail_account_password # your smtp account password
    properties:
      mail:
        smtp:
          auth: true
          connection-timeout: 5000
          timeout: 5000
          write-timeout: 5000
          starttls:
            enable: true # if your host has TLS is enabled else set false
    retry:
      initial-interval-ms: 1000
      max-interval-ms: 10000
      multiplier: 2.0
      maxAttempts: 3 # number of retry if sending mail is fail
      sleep-time-ms: 2000
```

or `application.properties` file

```properties
my-spring.mail.host=smtp.gmail.com
my-spring.mail.port=587
my-spring.mail.username=gmail_account_name
my-spring.mail.password=gmail_account_password
my-spring.mail.properties.mail.smtp.auth=true
my-spring.mail.properties.mail.smtp.connection-timeout=5000
my-spring.mail.properties.mail.smtp.timeout=5000
my-spring.mail.properties.mail.smtp.write-timeout=5000
my-spring.mail.properties.mail.smtp.starttls.enable=true
my-spring.mail.retry.initial-interval-ms=1000
my-spring.mail.retry.max-interval-ms=10000
my-spring.mail.retry.multiplier=2.0
my-spring.mail.retry.maxAttempts=3
my-spring.mail.retry.sleep-time-ms=2000
```

2. Config Class: Create new Config.java Class inside your Project

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({
        @ComponentScan(basePackages = "ir.alirezaalijani.spring.mail.module.*")
})
public class Config {

}
```

3. Use Existing `MailService` samples: - In the case of using Html mail sending read this
   before : https://github.com/alirezaalj/spring-mail-module/tree/master/src/main/resources/templates/mail

```java
import ir.alirezaalijani.spring.mail.module.mail.MailService;
import ir.alirezaalijani.spring.mail.module.mail.templates.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ir.alirezaalijani.spring.mail.module.mail.model.HtmlMailWithAttachments;
import ir.alirezaalijani.spring.mail.module.mail.model.TextMail;
import ir.alirezaalijani.spring.mail.module.mail.model.TextMailWithAttachments;
import ir.alirezaalijani.spring.mail.module.mail.templates.DefaultMailTemplate;
import ir.alirezaalijani.spring.mail.module.mail.templates.DefaultMailTemplateWithAttachments;

@Service
public class MyService {
    @Autowired
    private MailService mailService;

    // send simple text mail
    public void sendTextMail() {
        // create mail object of type TextMail
        TextMail textMail = new TextMail(
                "alirezaalijani.ir@gmail.com", // send to mail
                "yourmail@gmail.com", // sent from mail your own email
                "Text Mail Subject", // mail subject
                "My mail text is something about java", // mail text
                "https://alirezaalijani.ir"); // action link

        // fire spring event
        mailService.sendEmail(textMail);
    }

    // send simple text mail with Attachments
    public void sendTextMailWithAttachments() {
        // create mail object of type TextMail TextMailWithAttachments
        TextMailWithAttachments mailMessage = new TextMailWithAttachments(
                "alirezaalijani.ir@gmail.com", // send to mail
                "yourmail@gmail.com", // sent from mail
                "Text Mail Subject", // mail subject
                "My mail text is something about java", // mail text
                "https://alirezaalijani.ir"); // action link
        // create files to add in email - file must be existed - and buy default files are added inside of project resources
        File img1 = new File("src/test/resources/data/img1.jpg"); // attachment file
        File img2 = new File("src/test/resources/data/img2.jpg"); // attachment file

        // add file to mail object
        mailMessage.addAttachment("my image.jpg", img1);
        mailMessage.addAttachment("my image2.jpg", img2);

        // fire spring event
        mailService.sendEmail(mailMessage);
    }

    // send html mail with extra Attributes
    // read more : https://github.com/alirezaalj/spring-mail-module/tree/master/src/main/resources/templates/mail
    public void sendHtmlEmail() {
        // create mail object of type TextMail DefaultMailTemplate
        // you can implement your HtmlMail Class
        DefaultMailTemplate htmlMail = new DefaultMailTemplate(
                "alirezaalijani.ir@gmail.com", // send to mail
                "yourmail@gmail.com", // sent from mail
                "Html Mail Subject", // mail subject
                "My mail text is something about java", // mail message
                "https://alirezaalijani.ir", // action link

                TemplateType.Blue, // the default html file path inside resources/templates/mail/template_blue.html
                // sample files exist in https://github.com/alirezaalj/spring-mail-module/tree/master/src/main/resources/templates/mail
                // make sure if you are copying template_blue.html template_red.html from above link inside resources/templates/mail/ directory
                // do not change default attributes

                "Title Of My Mail", // html title text
                "https://alirezaalijani.ir", // html title link
                "View My Site", // action text
                "Alijani", // company name
                "https://alirezaalijani.ir" // company url
        );

        // add attributes like thymeleaf view model
        // <p th:text="${my-key}">...</p>
        htmlMail.addAttr("my-key", "any-value");

        // fire spring event
        mailService.sendEmail(htmlMail);
    }

    // send html mail with extra Attributes and Attachment
    // read more : https://github.com/alirezaalj/spring-mail-module/tree/master/src/main/resources/templates/mail
    public void sendHtmlMailWithAttachments() {
        // create mail object of type TextMail HtmlMailWithAttachments
        // you can implement your HtmlMail Class
        HtmlMailWithAttachments mailMessage = new DefaultMailTemplateWithAttachments(
                "alirezaalijani.ir@gmail.com",
                "yourmail@gmail.com",
                "Html Mail Subject",
                "My mail text is something about java",
                "https://alirezaalijani.ir",

                TemplateType.Red, // the default html file path inside resources/templates/mail/template_red.html of module is included in your project
                // sample files exist in https://github.com/alirezaalj/spring-mail-module/tree/master/src/main/resources/templates/mail
                // make sure if you are copying template_blue.html template_red.html from above link inside resources/templates/mail/ directory
                // do not change default attributes

                "Title Of My Mail",
                "https://alirezaalijani.ir",
                "View My Site", "Alijani",
                "https://alirezaalijani.ir");

        // create files to add in email - file must be existed - and buy default files are added inside of project resources
        File img1 = new File("src/test/resources/data/img1.jpg");
        File img2 = new File("src/test/resources/data/img2.jpg");

        // add file to mail object
        mailMessage.addAttachment("my image.jpg", img1);
        mailMessage.addAttachment("my image2.jpg", img2);

        // fire spring event
        mailService.sendEmail(mailMessage);
    }
}
```

4. After send Event: when we call `mailService.sendEmail(mailMessage);`
   new Mail is going to be sent and after sending default behavior of
   `MailService` service is :

```java
package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.BasicMailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This Service is implemented already inside the module
 * You can implement your own implementation
 */
@Slf4j
@Service
public class MailServiceImpl extends MailService {


    /**
     * Default behavior of implemented service
     * this method is call inside MailMessageVisitor class automatically
     * @param mailMessage basic mail object that we send
     * @param success stats of mail sending
     */
    @Override
    protected void mailSend(BasicMailMessage mailMessage, boolean success) {
        if (success) {
            log.info("Mail is Send Successfully to {}", mailMessage.getToMail());
        } else {
            log.error("Sending Mail to {} failed ", mailMessage.getToMail());
        }
    }

}
```

6. Create Your Own `MailService` implementation:

```java
import ir.alirezaalijani.spring.mail.module.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary // important to avoid SpringContext Bean errors
@Service // make Spring bean
public class MyMailService extends MailService {

    // My Jpa Repository
    @Autowired
    private MailRepository mailRepository;

    // My implementation
    @Override
    protected void mailSend(BasicMailMessage mailMessage, boolean success) {
        if (success) {
            log.info("Mail is Send Successfully to {}", mailMessage.getToMail());
            // save in  database or any other action
            mailRepository.save(
                    // data base entity
            );
        } else {
            log.error("Sending Mail to {} failed ", mailMessage.getToMail());
            // handling failure
        }
    }
}
```
